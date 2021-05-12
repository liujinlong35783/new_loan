package com.acct.job.jobhandler;

import cn.hutool.core.date.DateUtil;
import com.acct.job.thread.*;
import com.dcfs.esb.ftp.utils.ThreadSleepUtil;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagEnum;
import com.tkcx.api.common.BeanContext;
import com.tkcx.api.common.BusiCommonService;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
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
        log.info("ScheduleRunnable start ..." + startDate);
        // 获取会计日期
        Date selectDate = busiCommonService.getCoreSysDate();
        if(selectDate!=null){
            selectDate = DateUtil.parse(DateUtil.formatDate(selectDate),"yyyy-MM-dd");
        }
        // 判断互金数据是否已接入
        HjFileInfoModel queryInfo = new HjFileInfoModel();
        queryInfo.setFileDate(selectDate);
        queryInfo.setDeleteFlag(HjFileFlagEnum.NOT_DELETED);
        List<HjFileInfoModel> hjData = hjFileInfoService.selectModelList(queryInfo);
        log.info("互金数据查询日期：{}，查询结果{}", queryInfo.getFileDate(), hjData.size());

        if(count < 10){
            // !busiCommonService.existHjData(selectDate)
            if(selectDate == null || hjData.size() == 0){
                count ++;
                ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(1);
                scheduleThreadPool.schedule(this, 10, TimeUnit.MINUTES);
                scheduleThreadPool.shutdown();
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
        log.info("当前会计日期为：" + selectDate);
        // 创建线程池
        ExecutorService exe = Executors.newFixedThreadPool(10);

        // 1. 借款借据回单，数据来自于网贷
        exe.execute(new LoanReceiptThread(selectDate));

        // 2. 还款回单，数据来自于网贷
        exe.execute(new RefundReceiptThread(selectDate));

        // 3. 网贷业务机构轧账单，数据来自于会计凭证 ACCT_DETAIL_TEMP 表，ACCT_DETAIL_TEMP数据来自于互金
        exe.execute(new BusiOrgBillThread(selectDate));

        // 4. 网贷业务机构业务流水，数据来自于网贷+会计凭证 ACCT_DETAIL_TEMP 表
        exe.execute(new BusiOrgSeqThread(selectDate));

        // 5. 贷款分户账 网贷数据+会计凭证ACCT_ORG_TEMP表，ACCT_ORG_TEMP数据来自于互金
        exe.execute(new BusiOrgSeqThread(selectDate));

        // 6. 贷款明细账，数据来自于网贷+会计凭证ACCT_ORG_TEMP表、ACCT_BUSI_CODE表
        exe.execute(new LoanDetailBillThread(selectDate));

        // 7. 贷款利息登记簿，数据来自于网贷系统
        exe.execute(new InterestBillThread(selectDate));

        // 8. 会计凭证(记账凭证/交易凭证)，数据来自网贷+会计凭证ACCT_DETAIL_TEMP表、ACCT_ORG_TEMP表
        exe.execute(new AcctVoucherThread(selectDate));

        // 9. 贷款形态调整明细清单、贷款调整登记簿,数据来自网贷+会计凭证ACCT_ORG_TEMP表
        exe.execute(new LoanAdjustThread(selectDate));

        // 关闭线程池
        exe.shutdown();
        // 判断线程是否执行完成
        while (true) {
            if (exe.isTerminated()) {
                Date endDate = new Date();
                log.info("ScheduleRunnable end ......" + endDate);
                log.info("定时任务耗时：" + DateUtil.formatBetween(startDate, endDate));
                break;
            }
            ThreadSleepUtil.sleepSecondIngoreEx(60);
        }
        //if (selectDate != null && busiCommonService.existHjData(selectDate)) {

//        }
    }
}
