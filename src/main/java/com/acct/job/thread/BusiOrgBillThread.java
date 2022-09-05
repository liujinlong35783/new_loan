package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.utils.DateUtils;
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
    public void run() {
//        log.info("BusiOrgBillThread start..." + new Date());
//        //轧帐单改版
//        List<BusiOrgBillModel> busiOrgBillList = acctDetailTempService.statAllBusiOrgBillVo(super.getCurDate());
//        /*QueryWrapper<AcctBrchTempModel> queryWrapper = getQueryWrapper(AcctBrchTempModel.class, "acctDate");
//        queryWrapper.and(i -> i.ne("TODAY_LOAN_QUANTITIES",0).or().ne("TODAY_DEBIT_QUANTITIES",0));
//        queryWrapper.notIn("ITEM_CTRL","304101","304102","304112","200501");
//
//        // 查询机构总账表临时数据
//        List<AcctBrchTempModel> acctBrchTempList = acctBrchTempService.list(queryWrapper);
//        log.info("AcctBrchTempModel record is {}." ,busiOrgBillList.size());
//
//        List<BusiOrgBillModel> busiOrgBillList = new ArrayList<>();
//        for (AcctBrchTempModel acctBrchTemp: acctBrchTempList) {
//            BusiOrgBillModel busiOrgBill = new BusiOrgBillModel();
//            busiOrgBill.setOrgCode(acctBrchTemp.getAcctOrg());
//            busiOrgBill.setOrgName(busiCommonService.getOrgNameByCode(busiOrgBill.getOrgCode()));
//            busiOrgBill.setBusiDate(acctBrchTemp.getAcctDate());
//            busiOrgBill.setItemCode(acctBrchTemp.getItemCtrl());
//            busiOrgBill.setFlag(acctBrchTemp.getOffBalanceFlag());
//            busiOrgBill.setDebtNum(acctBrchTemp.getTodayDebitQuantities());
//            busiOrgBill.setDebtAmount(ToolUtil.yuanToFen(acctBrchTemp.getTodayDebitAmount().toString()));
//            busiOrgBill.setCreditNum(acctBrchTemp.getTodayLoanQuantities());
//            busiOrgBill.setCreditAmount(ToolUtil.yuanToFen(acctBrchTemp.getTodayLoanAmount().toString()));
//            busiOrgBill.setAcctDate(acctBrchTemp.getAcctDate());
//            busiOrgBillList.add(busiOrgBill);
//        }
//*/
//        // 保存临时数据到业务机构轧账表
//        if (!busiOrgBillList.isEmpty()) {
//            log.info("BusiOrgBill record is {}.", busiOrgBillList.size());
//            busiOrgBillService.saveBatch(busiOrgBillList);
//        }
//        log.info("BusiOrgBillThread end..." + new Date());
//    }
        Date startDate = new Date();
        log.info("BusiOrgBillThread start..." + startDate);
        //取前一天数据
        Date startDate1 = DateUtils.getBeforDate(startDate);
        List<AcctBrchTempModel> acctBrchTempModelList = acctBrchTempService.queryBrchByAcctDate(startDate1);
        ArrayList<BusiOrgBillModel> busiOrgBillLoanList = new ArrayList<>();
        for (AcctBrchTempModel brchTempModel : acctBrchTempModelList) {
            BusiOrgBillModel busiOrgBillModel = new BusiOrgBillModel();
            //查询互金机构表获取机构名
            busiOrgBillModel.setOrgName(busiCommonService.getOrgNameByCode(brchTempModel.getAcctOrg()));
            busiOrgBillModel.setOrgCode(brchTempModel.getAcctOrg());
            busiOrgBillModel.setItemCode(brchTempModel.getItemCode());
            busiOrgBillModel.setFlag(brchTempModel.getOffBalanceFlag());
            busiOrgBillModel.setDebtNum(brchTempModel.getTodayDebitQuantities());
            busiOrgBillModel.setDebtAmount(brchTempModel.getTodayDebitAmount() + "");
            busiOrgBillModel.setCreditNum(brchTempModel.getTodayLoanQuantities());
            busiOrgBillModel.setCreditAmount(brchTempModel.getTodayLoanAmount() + "");
            busiOrgBillModel.setCreateDate(brchTempModel.getCreateDate());
            busiOrgBillModel.setAcctDate(super.getCurDate());
            busiOrgBillLoanList.add(busiOrgBillModel);
        }
        if (!busiOrgBillLoanList.isEmpty()) {
            log.info("BusiOrgBill record is {}.", busiOrgBillLoanList.size());
            busiOrgBillService.saveBatch(busiOrgBillLoanList);
        }
        Date endDate = new Date();
        log.info("BusiOrgBillThread end..." + endDate);
        log.info("BusiOrgBillThread end：{},定时任务耗时：{}", endDate, DateUtil.formatBetween( endDate,startDate));

    }
}
