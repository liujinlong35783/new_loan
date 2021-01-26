package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.business.wdData.model.*;
import com.tkcx.api.constant.EnumConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 贷款利息登记簿
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class InterestBillThread extends AcctBaseThread {

    @Override
    public void run(){

        log.info("InterestBillThread start..." + new Date());

        // 贷款利息登记簿
        List<InterestBillModel> interestBillList = new ArrayList<>();

        // 查询前一天利息计提记录
        Date selectDate = DateUtil.offsetDay(this.getCurDate(), -1);
        // 查询网贷系统利息计提记录
        List<Map<String, Object>> interestAccrualList = busiCommonService.selectInterestAccrual(selectDate);
        log.info("InterestBill of InterestAccrual record is {}.", interestAccrualList.size());

        // 查询前一天期次还款记录
        //QueryWrapper<RepayAssemblyRecordModel> queryWrapper = new QueryWrapper<>();
        //queryWrapper.ge("REPAY_FINISH_TIME", DateUtil.offsetDay(this.getCurDate(),-1)).lt("REPAY_FINISH_TIME", this.getCurDate());
        QueryWrapper queryWrapper = getQueryWrapper(RepayAssemblyRecordModel.class, "repayFinishTime");
        // 查询网贷系统还款记录
        List<RepayAssemblyRecordModel> repayAssemblyList = repayAssemblyRecordService.list(queryWrapper);
        log.info("InterestBill of RepayAssemblyRecordModel record is {}.", repayAssemblyList.size());

        // 所有利息登记簿记录
        List interestList = new ArrayList();
        interestList.addAll(interestAccrualList);
        interestList.addAll(repayAssemblyList);

        for (Object obj : interestList) {
            String assetItemNo = "";
            InterestBillModel interestBill = null;

            if (obj instanceof Map) {
                // 利息计提
                Map<String, Object> map = (Map<String, Object>) obj;
                assetItemNo = (String)map.get("ASSET_ITEM_NO") ;
                interestBill = this.getInterestBillModelByInterestAccrual(map);
            } else if (obj instanceof RepayAssemblyRecordModel) {
                // 还款记录
                RepayAssemblyRecordModel repayAssembly = (RepayAssemblyRecordModel)obj;
                assetItemNo = repayAssembly.getAssetNo();
                interestBill = this.getInterestBillModelByRepayAssemblyRecord(repayAssembly);
            }

            // 填充利息登记簿资产信息
            this.setInterestBillByAsset(assetItemNo, interestBill);

            interestBillList.add(interestBill);
        }

        if (!interestBillList.isEmpty()) {
            log.info("InterestBill record is {}.", interestBillList.size());
            interestBillService.saveBatch(interestBillList);
        }

        log.info("InterestBillThread end..." + new Date());
    }

    /**
     * 填充利息登记簿资产信息
     *
     * @param assetItemNo
     * @param interestBill
     * @return
     */
    private InterestBillModel setInterestBillByAsset(String assetItemNo, InterestBillModel interestBill) {

        // 资产信息
        AssetModel asset = new AssetModel();
        asset.setAssetItemNo(assetItemNo);
        asset = assetService.selectOne(asset);
        if (null != asset) {
            interestBill.setOrgCode(asset.getOrgid());
            interestBill.setOrgName(busiCommonService.getOrgNameByCode(interestBill.getOrgCode()));
            interestBill.setLoanAccount(asset.getAssetLoanAccount());
            interestBill.setDebtNo(asset.getAssetDebtNo());
            interestBill.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
            interestBill.setContractNo(busiCommonService.getContractNoByAssetItemNo(asset.getAssetItemNo()));

            // 结清标识
            if (interestBill.getPayoffDate() == null) {
                interestBill.setPayoffFlag(EnumConstant.PAYOFF_FLAG_NULL);
            } else {
                if (null == interestBill.getPayoffFlag() &&
                        interestBill.getPayoffSum().equals(interestBill.getShouldSum())) {
                    interestBill.setPayoffFlag(EnumConstant.PAYOFF_FLAG_PART);
                } else if (!interestBill.getPayoffSum().equals(interestBill.getShouldSum())){
                    interestBill.setPayoffFlag(EnumConstant.PAYOFF_FLAG_NULL);
                }
            }
        } else {
            log.info("{} assetItemNo of Asset is null", assetItemNo);
        }

        // 二类户信息
        if(StringUtils.isNotEmpty(interestBill.getBorrowerIdnum())) {
            CardiiModel cardii = cardiiService.getCardiiByCardiiIdnum(interestBill.getBorrowerIdnum());
            if (null != cardii) {
                interestBill.setLoanName(cardii.getCardiiName());
            }else{
                log.info("AssetBorrowerIdnum{} of CardII is null", interestBill.getBorrowerIdnum());
            }
        }

        return interestBill;
    }

    /**
     * 填充利息登记簿利息计提信息
     *
     * @param interestAccrual
     * @return
     */
    private InterestBillModel getInterestBillModelByInterestAccrual(Map<String, Object> interestAccrual) {
        String accrualType = (String)interestAccrual.get("SETTLE_TYPE");

        String assetNo = (String)interestAccrual.get("ASSET_ITEM_NO");
        String repayPlanNo = (String)interestAccrual.get("REPAY_PLAN_NO");

        Map<String, Object> repayPlanSum = busiCommonService.selectPrincipalInterestHis(assetNo, repayPlanNo);
        Map<String, Object> repaidSum = busiCommonService.selectPrincipalInterestRepaid(assetNo, repayPlanNo, this.getCurDate());

        BigDecimal repayAmount = (BigDecimal)repaidSum.get("CURR_REPAY_AMOUNT");
        BigDecimal principal = (BigDecimal)repayPlanSum.get("PRINCIPAL");// 本金
        BigDecimal repaidPrincipal = (BigDecimal)repaidSum.get("PRINCIPAL_REPAY_AMOUNT");// 已还本金
        BigDecimal interest = (BigDecimal)repayPlanSum.get("INTEREST");// 实际的足期的本金利息
        BigDecimal repaidInterest = (BigDecimal)repaidSum.get("INTEREST_REPAY_AMOUNT");// 已还本金利息
        BigDecimal overdueInterest = busiCommonService.selectOverInterestHis(assetNo, repayPlanNo, this.getCurDate()); // 逾期利息

        BigDecimal shouldPrincipal = principal.subtract(repaidPrincipal);
        BigDecimal shouldNormalInterest = interest.subtract(repaidInterest);
        BigDecimal shouldOverdueInterest = overdueInterest.subtract(repayAmount).add(repaidPrincipal).add(repaidInterest);

        BigDecimal shouldSum = shouldPrincipal.add(shouldNormalInterest).add(shouldOverdueInterest);

        InterestBillModel interestBill = new InterestBillModel();
        interestBill.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
        interestBill.setShouldPrincipal(shouldPrincipal.toPlainString());
        interestBill.setShouldNormalInterest(shouldNormalInterest.toPlainString());
        interestBill.setShouldOtherSum("0");

        if (accrualType.equals("interest_settle")) {
            // 正常利息结息
            interestBill.setShouldOverdueInterest("0");
        } else {
            // 逾期利息结息时存在还款记录，则以还款记录为准
            interestBill.setShouldOverdueInterest(shouldOverdueInterest.toString());
        }
        interestBill.setShouldSum(shouldSum.toString());
        interestBill.setPayoffPrincipal("0");
        interestBill.setPayoffNormalInterest("0");
        interestBill.setPayfoffOverdueInterest("0");
        interestBill.setPayoffOtherSum("0");
        interestBill.setPayoffSum("0");
        //interestBill.setAcctDate((Date) interestAccrual.get("CREATE_CORE_SYSTE_DATE"));
        interestBill.setAcctDate(DateUtil.offsetDay(this.getCurDate(),-1));
        return interestBill;
    }

    /**
     * 填充利息登记簿还款信息
     *
     * @param repayAssembly
     * @return
     */
    private InterestBillModel getInterestBillModelByRepayAssemblyRecord(RepayAssemblyRecordModel repayAssembly) {
        //应还费用
        BigDecimal[] shouldAmount =  busiCommonService.getPrincipalHistory(repayAssembly.getAssetNo(), repayAssembly.getRepayAssemblyId(), repayAssembly.getCoreSysDate());
        //已还费用
        BigDecimal[] payoffAmount =  busiCommonService.getPayoffPrincipalHistory(repayAssembly.getRepayAssemblyId());

        BigDecimal principalAmount = shouldAmount[0];// 还款计划本金
        BigDecimal repayPrincipal = payoffAmount[0];// 已还本金
        BigDecimal interestAmount = shouldAmount[1];// 还款计划正常利息
        BigDecimal repayInterest = payoffAmount[1];// 已还正常利息
        BigDecimal overdueAmount = shouldAmount[2];// 还款计划逾期利息
        BigDecimal repayOverdue = payoffAmount[2];// 已还逾期利息

        BigDecimal shouldSum = principalAmount.add(interestAmount).add(overdueAmount);
        BigDecimal payoffSum = repayPrincipal.add(repayInterest).add(repayOverdue);

        InterestBillModel interestBill = new InterestBillModel();
        interestBill.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);
        interestBill.setShouldPrincipal(principalAmount.toPlainString());
        interestBill.setShouldNormalInterest(interestAmount.toPlainString());
        interestBill.setShouldOverdueInterest(overdueAmount.toPlainString());
        interestBill.setShouldOtherSum("0");
        interestBill.setShouldSum(shouldSum.toPlainString());

        interestBill.setPayoffPrincipal(repayPrincipal.toPlainString());
        interestBill.setPayoffNormalInterest(repayInterest.toPlainString());
        interestBill.setPayfoffOverdueInterest(repayOverdue.toPlainString());
        interestBill.setPayoffOtherSum("0");
        interestBill.setPayoffSum(payoffSum.toPlainString());
        interestBill.setPayoffDate(repayAssembly.getRepayFinishTime());
        interestBill.setAcctDate(repayAssembly.getCoreSysDate());
        if (busiCommonService.getFinanceStatusByRepay(repayAssembly.getRepayAssemblyId())) {
            interestBill.setPayoffFlag(EnumConstant.PAYOFF_FLAG_FULL);
        }

        return interestBill;
    }

    public static void main(String[] args) {
        System.out.println(DateUtil.parseDate("2034-06-08 15:35:04").after(DateUtil.parseDate("2034-06-08 15:35:04")));
    }
}
