package com.acct.job.thread;

import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import lombok.extern.slf4j.Slf4j;

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

    @Override
    public void run(){
        log.info("BusiOrgBillThread start..." + new Date());
        //轧帐单改版
        List<BusiOrgBillModel> busiOrgBillList = acctDetailTempService.statAllBusiOrgBillVo(super.getCurDate());
        /*QueryWrapper<AcctBrchTempModel> queryWrapper = getQueryWrapper(AcctBrchTempModel.class, "acctDate");
        queryWrapper.and(i -> i.ne("TODAY_LOAN_QUANTITIES",0).or().ne("TODAY_DEBIT_QUANTITIES",0));
        queryWrapper.notIn("ITEM_CTRL","304101","304102","304112","200501");

        // 查询机构总账表临时数据
        List<AcctBrchTempModel> acctBrchTempList = acctBrchTempService.list(queryWrapper);
        log.info("AcctBrchTempModel record is {}." ,busiOrgBillList.size());

        List<BusiOrgBillModel> busiOrgBillList = new ArrayList<>();
        for (AcctBrchTempModel acctBrchTemp: acctBrchTempList) {
            BusiOrgBillModel busiOrgBill = new BusiOrgBillModel();
            busiOrgBill.setOrgCode(acctBrchTemp.getAcctOrg());
            busiOrgBill.setOrgName(busiCommonService.getOrgNameByCode(busiOrgBill.getOrgCode()));
            busiOrgBill.setBusiDate(acctBrchTemp.getAcctDate());
            busiOrgBill.setItemCode(acctBrchTemp.getItemCtrl());
            busiOrgBill.setFlag(acctBrchTemp.getOffBalanceFlag());
            busiOrgBill.setDebtNum(acctBrchTemp.getTodayDebitQuantities());
            busiOrgBill.setDebtAmount(ToolUtil.yuanToFen(acctBrchTemp.getTodayDebitAmount().toString()));
            busiOrgBill.setCreditNum(acctBrchTemp.getTodayLoanQuantities());
            busiOrgBill.setCreditAmount(ToolUtil.yuanToFen(acctBrchTemp.getTodayLoanAmount().toString()));
            busiOrgBill.setAcctDate(acctBrchTemp.getAcctDate());
            busiOrgBillList.add(busiOrgBill);
        }
*/
        // 保存临时数据到业务机构轧账表
        if (!busiOrgBillList.isEmpty()) {
            log.info("BusiOrgBill record is {}.", busiOrgBillList.size());
            busiOrgBillService.saveBatch(busiOrgBillList);
        }
        log.info("BusiOrgBillThread end..." + new Date());
    }
}
