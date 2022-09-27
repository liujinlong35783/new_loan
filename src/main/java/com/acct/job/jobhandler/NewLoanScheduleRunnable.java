package com.acct.job.jobhandler;


import cn.hutool.core.date.DateUtil;
import com.acct.job.newLoadTemp.newLoanThread.*;
import com.acct.job.util.MapUtil;
import com.acct.job.util.ResourceBundleUtil;
import com.tkcx.api.business.hjtemp.service.NewLoanCommonService;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.common.BeanContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 会计凭证数据获取执行类 新网贷解析
 *
 * @author cuijh
 * @date 2019/10/12 10:51
 */
@Slf4j
public class NewLoanScheduleRunnable implements Runnable {

    private NewLoanCommonService newLoanCommonService = BeanContext.getBean(NewLoanCommonService.class);


    @Override
    public void run() {
        startHandle();
    }
    /**
     * 新网贷文件处理
     *
     */
    public void startHandle(){

        //异步进行保存工作
        try {
            //测试使用
            Calendar calendar = Calendar.getInstance();
            calendar.set(2040, 11, 22);
            Date acctDate = calendar.getTime();
            if (acctDate != null) {
                acctDate = DateUtil.parse(DateUtil.formatDate(acctDate), "yyyy-MM-dd");
            }
            log.info("NewLoanScheduleRunnable startHandle start 开始解析新网贷文件：日期【{"+new Date()+"}】");
            //正式使用
//            Date acctDate = new Date();
            Map<String, String> typeNameMap = newLoanCommonService.excuteFiles(acctDate);
            ResourceBundle bundle = ResourceBundle.getBundle("bank");
            Map<String, Object> map = ResourceBundleUtil.getBundleMap(bundle);
            Boolean isRemove = MapUtil.objToBoolean("acct.isRemove", map);
            // 自定义线程池用来进行新网贷文件的解析入库
            ThreadPoolExecutor readThreadPool = new ThreadPoolExecutor(
                    5,10,60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

//          1. 借款借据回单，数据来自于新网贷文件(已本地测试（小量数据）)
            readThreadPool.execute(new NewLoanLoanReceiptThread(isRemove, typeNameMap.get("JJHD")));

            // 2. 还款回单，数据来自于新网贷文件
            readThreadPool.execute(new NewLoanRefundReceiptThread(isRemove, typeNameMap.get("HKHD")));

            // 3. 网贷业务机构轧账单，数据来自于新网贷文件(已本地测试（小量数据）)
//            readThreadPool.execute(new NewLoanBusiOrgBillThread(isRemove, typeNameMap.get("JGZZD")));

            // 4. 网贷业务机构业务流水，数据来自于新网贷文件(已本地测试（小量数据）)
            readThreadPool.execute(new NewLoanBusiOrgSeqThread(isRemove, typeNameMap.get("YWLS")));

            // 5. 贷款分户账，数据来自于新网贷文件(已本地测试（小量数据(缺少贷款账号字段)）)
            readThreadPool.execute(new NewLoanLoanAccBillThread(isRemove, typeNameMap.get("FHZ")));

            // 6. 贷款明细账，数据来自于新网贷文件(已本地测试（小量数据）)
            readThreadPool.execute(new NewLoanLoanDetailBillThread(isRemove, typeNameMap.get("MXZ")));

            // 7. 贷款利息登记簿，数据来自于新网贷文件(已本地测试（小量数据(创建时间缺少会自己补充)）)
            readThreadPool.execute(new NewLoanInterestBillThread(isRemove, typeNameMap.get("LXDJB")));

            // 8. 会计凭证(记账凭证/交易凭证)，数据来自于新网贷文件(已本地测试（小量数据）)
            readThreadPool.execute(new NewLoanAcctVoucherThread(isRemove, typeNameMap.get("KJPZ")));

            // 9. 贷款形态调整明细清单，数据来自于新网贷文件
            readThreadPool.execute(new NewLoanLoanAdjustThread(isRemove, typeNameMap.get("XTTZ")));

            log.info("NewLoanScheduleRunnable startHandle end 结束解析新网贷文件：日期【{"+new Date()+"}】");

            // 关闭线程池
            readThreadPool.shutdown();
            boolean flag;
            do {
                flag = ! readThreadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            } while (flag);
        }catch (Exception e){
            log.error("解析互金文件异常{}" ,e);
        }
    }

    /**
     * 通用处理
     * @param path
     * @return
     */
    public  <T> List<T> makeListData(String path, Class<T> clazz){

        List<T> modelList = new ArrayList<>();
        try {
            List<String> list = FileUtil.readLine(path);
            /*以下调用带参的、私有构造函数*/
            Constructor constructor = clazz.getDeclaredConstructor(new Class[]{String.class});
            constructor.setAccessible(true);

            //遍历行数据
            T model = null;
            for (String lineStr : list) {
                if(StringUtils.isEmpty(lineStr)){continue;}
                //行数据生成对象
                model = (T) constructor.newInstance(new Object[]{lineStr});
                modelList.add(model);
            }
        }catch (Exception e){
            e.printStackTrace();
            log.error(clazz.getName()+"数据保存异常");
        }
        return modelList;
    }



}
