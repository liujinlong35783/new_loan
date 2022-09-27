package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgBillLoanModel;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 网贷业务机构轧账单
 *
 * @author cuijh
 * @since 2019-08-06
 */
@Slf4j
public class BusiOrgBillThread extends AcctBaseThread {

    public BusiOrgBillThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){
        Date startDate = new Date();
        log.info("BusiOrgBillThread start..." + startDate);
        //取前一天数据
        Date selectDate1 = DateUtil.offsetDay(super.getCurDate(), -1);
        log.info("BusiOrgBill selectDate1 is {}.", selectDate1);
        List<AcctBrchTempModel> acctBrchTempModelList = acctBrchTempService.queryBrchByAcctDate(selectDate1);
        log.info("BusiOrgBill acctBrchTempModelList is {}条记录", acctBrchTempModelList.size());
        ArrayList<BusiOrgBillLoanModel> busiOrgBillLoanList = new ArrayList<>();
        for (AcctBrchTempModel brchTempModel : acctBrchTempModelList) {
            BusiOrgBillLoanModel busiOrgBillLoanModel = new BusiOrgBillLoanModel();
            //查询互金机构表获取机构名
            busiOrgBillLoanModel.setOrgName(busiCommonService.getOrgNameByCode(brchTempModel.getAcctOrg()));
            busiOrgBillLoanModel.setOrgCode(brchTempModel.getAcctOrg());
            busiOrgBillLoanModel.setItemCode(brchTempModel.getItemCode());
            busiOrgBillLoanModel.setFlag(brchTempModel.getOffBalanceFlag());
            busiOrgBillLoanModel.setDebtNum(brchTempModel.getTodayDebitQuantities());
            busiOrgBillLoanModel.setDebtAmount(brchTempModel.getTodayDebitAmount()+"");
            busiOrgBillLoanModel.setCreditNum(brchTempModel.getTodayLoanQuantities());
            busiOrgBillLoanModel.setCreditAmount(brchTempModel.getTodayLoanAmount()+"");
            busiOrgBillLoanModel.setCreateDate(brchTempModel.getCreateDate());
            busiOrgBillLoanModel.setAcctDate(super.getCurDate());
            busiOrgBillLoanModel.setBusiDate(super.getCurDate());
            busiOrgBillLoanModel.setChannel("1");
            busiOrgBillLoanList.add(busiOrgBillLoanModel);
        }
        if (!busiOrgBillLoanList.isEmpty()) {
            log.info("BusiOrgBill record is {}.", busiOrgBillLoanList.size());
            busiOrgBillLoanService.saveBatch(busiOrgBillLoanList);
            log.info("BusiOrgBill {}条入库成功", busiOrgBillLoanList.size());
        }
        log.info("BusiOrgBillThread end..." + new Date());
        Date endDate = new Date();
        log.info("BusiOrgBillThread end：{},定时任务耗时：{}", endDate, DateUtil.formatBetween(startDate, endDate));
    }
}
