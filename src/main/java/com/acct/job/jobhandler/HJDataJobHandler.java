package com.acct.job.jobhandler;

import com.tkcx.api.service.imp.QnBankApiServiceImpl;
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
@JobHandler(value = "hjDataJobHandler")
@Component
@Slf4j
public class HJDataJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String param){

        log.info("AcctDataJobHandler -->>>>>>>>start....");

        boolean result = new QnBankApiServiceImpl().makeDownloadFile();
        log.info("AcctDataJobHandler -->>>>>>>>end....:"+result);

        return SUCCESS;
    }
}
