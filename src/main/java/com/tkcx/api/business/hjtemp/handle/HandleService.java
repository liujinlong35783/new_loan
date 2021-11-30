package com.tkcx.api.business.hjtemp.handle;

import com.tkcx.api.business.hjtemp.hjThread.AcctBrchReadThread;
import com.tkcx.api.business.hjtemp.hjThread.AcctDetailReadThread;
import com.tkcx.api.business.hjtemp.hjThread.AcctOrgReadThread;
import com.tkcx.api.business.hjtemp.hjThread.BusiCodeReadThread;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @project：
 * @author： zhaodan
 * @description： 互金数据文件解析类
 * @create： 2021/10/24 13:28
 */
@Slf4j
@Service
public class HandleService {

    @Value("${acct.isRemove}")
    private Boolean isRemove;

    /**
     * 互金文件处理
     *
     * @param fileType
     * @param fileDate
     */
    public void startHandle(String fileType, Date fileDate){

        //异步进行保存工作
        try {
            log.info("开始解析互金文件：日期【{}】，文件类型【{}】", fileDate, fileType);

            // 自定义线程池用来进行互金文件的解析
            ThreadPoolExecutor readThreadPool = new ThreadPoolExecutor(
                    4,5,60, TimeUnit.SECONDS, new LinkedBlockingDeque<>(3),
                    Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

            switch (fileType){
                case HjFileFlagConstant.ACCT_DETAIL_FILE:
                    // 2021年10月24解决CPU使用率高问题
                    // 会计科目文件解析
                    readThreadPool.execute(new AcctDetailReadThread(isRemove, fileDate));
                    break;
                case HjFileFlagConstant.ACT_BRCH_FILE:
                    readThreadPool.execute(new AcctBrchReadThread(isRemove, fileDate));
                    break;
                case HjFileFlagConstant.BUSI_CODE_FILE:
                    readThreadPool.execute(new BusiCodeReadThread(isRemove, fileDate));
                    break;
                case HjFileFlagConstant.ACT_PUB_ORG_FILE:
                    readThreadPool.execute(new AcctOrgReadThread(fileDate));
                    break;
                default:
                    log.error("文件类型：【{}】错误", fileType);
            }
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
