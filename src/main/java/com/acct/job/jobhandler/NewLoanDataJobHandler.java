package com.acct.job.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 会计凭证数据自动任务
 *
 * @author
 */
@JobHandler(value = "newLoanDataJobHandler")
@Component
@Slf4j
public class NewLoanDataJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param){
        //定时任务每日20:00执行，解析新希望文件入库
        log.info("NewLoanScheduleRunnable -->>>>>>>>start....");

        new NewLoanScheduleRunnable().run();

        log.info("NewLoanScheduleRunnable -->>>>>>>>end....");

        return SUCCESS;
    }
}
