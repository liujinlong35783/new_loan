package com.acct.job.jobhandler;

import cn.hutool.core.date.DateUtil;
import com.acct.job.thread.*;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.common.BeanContext;
import com.tkcx.api.common.BusiCommonService;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 会计凭证数据获取执行类
 *
 * @author cuijh
 * @date 2019/10/12 10:51
 */
@Slf4j
public class ScheduleRunnable implements Runnable {

    private int count = 0;
    private BusiCommonService busiCommonService = BeanContext.getBean(BusiCommonService.class);

    //@Autowired
    private HjFileInfoService hjFileInfoService= BeanContext.getBean(HjFileInfoService.class);;

    @Override
    public void run() {

        Date startDate = new Date();
        log.info("ScheduleRunnable start {} ..." + startDate);
        // 获取会计日期
        Date selectDate = busiCommonService.getCoreSysDate();
        //测试使用
/*        Calendar calendar = Calendar.getInstance();
        calendar.set(2024,5,21);
        Date selectDate = calendar.getTime();*/
        if(selectDate!=null){
            selectDate = DateUtil.parse(DateUtil.formatDate(selectDate),"yyyy-MM-dd");
        }
        // 判断互金数据是否已接入
        HjFileInfoModel queryInfo = new HjFileInfoModel();
        queryInfo.setFileDate(selectDate);
        queryInfo.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        List<HjFileInfoModel> hjData = hjFileInfoService.selectModelList(queryInfo);
        log.info("互金数据查询日期：{}，查询结果{}", queryInfo.getFileDate(), hjData.size());

        if(count < 10){
            if(selectDate == null || hjData.size() == 0){
                count ++;
                ThreadPoolExecutor threadPool = new ThreadPoolExecutor(1,2,50,
                        TimeUnit.SECONDS, new LinkedBlockingDeque<>(1), Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());
                threadPool.submit(this);
                threadPool.shutdown();
            }
        } else if (hjData.size() == 0 && count >= 10){
            log.info("当日无互金数据，请检查互金数据文件是否正常！");
            return;
        } else if (selectDate == null && count >= 10) {
            log.info("当日获取网贷数据时间超时，请确认网贷系统是否正常！若系统正常，请手动再次执行该任务");
            return;
        }

        if(hjFileInfoService.selectModelList(queryInfo).size() <= 0){
            log.error("会计日期{}无互金数据", selectDate);
            return;
        }
        log.info("当前会计日期为：{}", selectDate);
        // 自定义一个线程池
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                5,10,60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(5),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // 1. 借款借据回单，数据来自于网贷
        threadPool.execute(new LoanReceiptThread(selectDate));

        // 2. 还款回单，数据来自于网贷
        threadPool.execute(new RefundReceiptThread(selectDate));

        // 3. 网贷业务机构轧账单，数据来自于会计凭证 ACCT_DETAIL_TEMP 表，ACCT_DETAIL_TEMP数据来自于互金
        threadPool.execute(new BusiOrgBillThread(selectDate));

        // 4. 网贷业务机构业务流水，数据来自于网贷+会计凭证 ACCT_DETAIL_TEMP 表
        threadPool.execute(new BusiOrgSeqThread(selectDate));

        // 5. 贷款分户账 网贷数据+会计凭证ACCT_ORG_TEMP表，ACCT_ORG_TEMP数据来自于互金
        threadPool.execute(new LoanAccBillThread(selectDate));

        // 6. 贷款明细账，数据来自于网贷+会计凭证ACCT_ORG_TEMP表、ACCT_BUSI_CODE表
        threadPool.execute(new LoanDetailBillThread(selectDate));

        // 7. 贷款利息登记簿，数据来自于网贷系统
        threadPool.execute(new InterestBillThread(selectDate));

        // 8. 会计凭证(记账凭证/交易凭证)，数据来自网贷+会计凭证ACCT_DETAIL_TEMP表、ACCT_ORG_TEMP表
        threadPool.execute(new AcctVoucherThread(selectDate));

        // 9. 贷款形态调整明细清单、贷款调整登记簿,数据来自网贷+会计凭证ACCT_ORG_TEMP表
        threadPool.execute(new LoanAdjustThread(selectDate));
        // 关闭线程池
        threadPool.shutdown();
        // 优雅关闭线程池
        try {
            boolean flag;
            do {
                flag = ! threadPool.awaitTermination(500, TimeUnit.MILLISECONDS);
            } while (flag);
        } catch (InterruptedException e) {
            log.error("会计凭证定时任务执行异常：{}" ,e);
        } finally {
            Date endDate = new Date();
            log.info("ScheduleRunnable end：{},定时任务耗时：{}", endDate, DateUtil.formatBetween(startDate, endDate));
        }
    }
}
