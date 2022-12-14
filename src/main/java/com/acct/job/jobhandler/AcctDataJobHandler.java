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
@JobHandler(value = "acctDataJobHandler")
@Component
@Slf4j
public class AcctDataJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param){

        log.info("AcctDataJobHandler -->>>>>>>>start....");

        new ScheduleRunnable().run();

        log.info("AcctDataJobHandler -->>>>>>>>end....");

//        log.info("NewLoanScheduleRunnable -->>>>>>>>start....");
//
//        new NewLoanScheduleRunnable().run();
//
//        log.info("NewLoanScheduleRunnable -->>>>>>>>end....");

        return SUCCESS;
    }
}
