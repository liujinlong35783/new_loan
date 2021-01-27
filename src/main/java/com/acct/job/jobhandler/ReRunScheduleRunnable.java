package com.acct.job.jobhandler;

import cn.hutool.core.date.DateUtil;
import com.acct.job.thread.*;
import com.dcfs.esb.ftp.utils.ThreadSleepUtil;
import com.tkcx.api.business.acctPrint.service.AcctSettleService;
import com.tkcx.api.common.BeanContext;
import com.tkcx.api.common.BusiCommonService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 会计凭证重跑数据获取执行类
 *
 * @author cuijh
 * @date 2019/10/12 10:51
 */
@Slf4j
public class ReRunScheduleRunnable implements Runnable {

    private int count = 0;
    private BusiCommonService busiCommonService = BeanContext.getBean(BusiCommonService.class);
    private AcctSettleService acctSettleService = BeanContext.getBean(AcctSettleService.class);

    @Override
    public void run() {

        Date startDate = new Date();
        log.info("ScheduleRunnable start ..." + startDate);

        // 获取会计日期
        Date selectDate = acctSettleService.queryDate();
        if(selectDate!=null){
            selectDate = DateUtil.parse(DateUtil.formatDate(selectDate),"yyyy-MM-dd");
        }
        // 判断互金数据是否已接入
        if(count < 10){
            if(selectDate == null || !busiCommonService.existHjData(selectDate)){
                count ++;
                ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
                scheduleThreadPool.schedule(this, 10, TimeUnit.MINUTES);
                scheduleThreadPool.shutdown();
            }
        } else if (!busiCommonService.existHjData(selectDate) && count >= 10){
            log.info("当日无互金数据，请检查互金数据文件是否正常！");
            return;
        } else if (selectDate == null && count >= 10) {
            log.info("当日获取网贷数据时间超时，请确认网贷系统是否正常！若系统正常，请手动再次执行该任务");
            return;
        }



        log.info("当前会计日期为：" + selectDate);
        if (selectDate != null && busiCommonService.existHjData(selectDate)) {
            // 创建线程池
            ExecutorService exe = Executors.newFixedThreadPool(10);

            // 1. 借款借据回单，数据来自于网贷
            LoanReceiptThread loanThead = new LoanReceiptThread();
            loanThead.setCurDate(selectDate);
            exe.execute(new Thread(loanThead));

            // 2. 还款回单，数据来自于网贷
            RefundReceiptThread refundThead = new RefundReceiptThread();
            refundThead.setCurDate(selectDate);
            exe.execute(new Thread(refundThead));

            // 3. 网贷业务机构轧账单，数据来自于会计凭证 ACCT_DETAIL_TEMP 表，ACCT_DETAIL_TEMP数据来自于互金
            BusiOrgBillThread busiOrgBillThread = new BusiOrgBillThread();
            busiOrgBillThread.setCurDate(selectDate);
            exe.execute(new Thread(busiOrgBillThread));

            // 4. 网贷业务机构业务流水，数据来自于网贷+会计凭证 ACCT_DETAIL_TEMP 表
            BusiOrgSeqThread busiOrgSeqThread = new BusiOrgSeqThread();
            busiOrgSeqThread.setCurDate(selectDate);
            exe.execute(new Thread(busiOrgSeqThread));

            // 5. 贷款分户账 网贷数据+会计凭证ACCT_ORG_TEMP表，ACCT_ORG_TEMP数据来自于互金
            LoanAccBillThread loanAccBillThread = new LoanAccBillThread();
            loanAccBillThread.setCurDate(selectDate);
            exe.execute(new Thread(loanAccBillThread));

            // 6. 贷款明细账，数据来自于网贷+会计凭证ACCT_ORG_TEMP表、ACCT_BUSI_CODE表
            LoanDetailBillThread loanDetailBillThread = new LoanDetailBillThread();
            loanDetailBillThread.setCurDate(selectDate);
            exe.execute(new Thread(loanDetailBillThread));

            // 7. 贷款利息登记簿，数据来自于网贷系统
            InterestBillThread interestBillThread = new InterestBillThread();
            interestBillThread.setCurDate(selectDate);
            exe.execute(new Thread(interestBillThread));

            // 8. 会计凭证(记账凭证/交易凭证)，数据来自网贷+会计凭证ACCT_DETAIL_TEMP表、ACCT_ORG_TEMP表
            AcctVoucherThread acctVoucherThread = new AcctVoucherThread();
            acctVoucherThread.setCurDate(selectDate);
            exe.execute(new Thread(acctVoucherThread));

            // 9. 贷款形态调整明细清单、贷款调整登记簿,数据来自网贷+会计凭证ACCT_ORG_TEMP表
            LoanAdjustThread loanAdjustThread = new LoanAdjustThread();
            loanAdjustThread.setCurDate(selectDate);
            exe.execute(new Thread(loanAdjustThread));

            // 关闭线程池
            exe.shutdown();

            // 判断线程是否执行完成
            while (true) {
                if (exe.isTerminated()) {
                    Date endDate = new Date();
                    log.info("ScheduleRunnable end ......" + endDate);
                    log.info("定时任务耗时：" + DateUtil.formatBetween(startDate, endDate));
                    // TODO
                    Date selectDateBiz = busiCommonService.getCoreSysDate();
                    if(selectDate.equals(selectDateBiz)){
                        acctSettleService.updateDateInfo(selectDateBiz, "FINSH");
                    }
                    if(!selectDate.equals(selectDateBiz)){
                        acctSettleService.updateDateInfo(DateUtil.offsetDay(selectDate, 1), "FINSH");
                    }
                    break;
                }
                ThreadSleepUtil.sleepSecondIngoreEx(60);
            }
        }
    }
}
