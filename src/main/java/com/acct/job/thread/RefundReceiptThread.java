package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.RefundReceiptModel;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.business.wdData.model.RepayAssemblyRecordModel;
import com.tkcx.api.business.wdData.model.RepayPeriodRecordModel;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 查询还款回单信息
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class RefundReceiptThread extends AcctBaseThread {

    @Override
    public void run(){

        log.info("RefundReceiptThread start..." + new Date());

        // 查询还款总成表前一天记录
        QueryWrapper queryWrapper = getQueryWrapper(RepayAssemblyRecordModel.class, "repayFinishTime");
        // 查询网贷，还款记录
        List<RepayAssemblyRecordModel> repayList = repayAssemblyRecordService.list(queryWrapper);
        log.info("RefundReceipt of RepayAssemblyRecord record is {}.", repayList.size());
        List<RefundReceiptModel> refundReceiptList = new ArrayList<>();
        for (RepayAssemblyRecordModel repayRecord : repayList) {

            RefundReceiptModel refundRec = new RefundReceiptModel();
            refundRec.setRefundName(busiCommonService.selectAccName(repayRecord.getIndividualIdnum()));
            refundRec.setBorrowerIdnum(repayRecord.getIndividualIdnum());
            refundRec.setPayAccount(repayRecord.getRepayCard());
            refundRec.setPayoffDate(repayRecord.getRepayFinishTime());

            // 查询网贷，资产信息
            AssetModel asset = busiCommonService.getAssetModel(repayRecord.getAssetNo());
            if (null != asset) {
                refundRec.setContractNo(busiCommonService.getContractNoByAssetItemNo(asset.getAssetItemNo()));
                refundRec.setDebtNo(asset.getAssetDebtNo());
                refundRec.setLoanAccount(asset.getAssetLoanAccount());

                refundRec.setLoanAmount(asset.getAssetGrantedPrincipalAmount().toString());
                refundRec.setGrantDate(asset.getAssetActualGrantAt());
                refundRec.setDueDate(asset.getAssetDueAt());
                refundRec.setInterestRate(asset.getAssetInterestRate());
                refundRec.setOverdueInterestRate(asset.getAssetPenaltyInterestRate());
                refundRec.setOrgCode(asset.getOrgid());
                refundRec.setOrgName(busiCommonService.getOrgNameByCode(refundRec.getOrgCode()));
            } else {
                log.info("{} assetItemNo of Asset is null", repayRecord.getAssetNo());
            }

            // 实际利息
            BigDecimal actualInterest = busiCommonService.getInterestAmountByAsset(repayRecord
                    .getAssetNo(), repayRecord.getRepayAssemblyId());

            BigDecimal repaidPrincipal = new BigDecimal(0);// 偿还本金金额
            BigDecimal unsettlePrincipal = new BigDecimal(0);// 结欠本金金额
            BigDecimal repaidInterest = new BigDecimal(0);// 偿还利息金额
            BigDecimal unsettleInterest = new BigDecimal(0);// 结欠利息金额

            // 还款期次信息
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("ASSEMBLY_ID", repayRecord.getRepayAssemblyId());
            queryWrapper.orderByDesc("REPAY_PERIOD");
            List<RepayPeriodRecordModel> periodRecordList = repayPeriodRecordService.list(queryWrapper);
            for (RepayPeriodRecordModel periodRecord : periodRecordList) {
                repaidPrincipal = repaidPrincipal.add(periodRecord.getPrincipalRepayAmount());
                repaidInterest = repaidInterest.add(periodRecord.getInterestRepayAmount())
                        .add(periodRecord.getOffblInterestRepayAmount())
                        .add(periodRecord.getExtendInterestRepayAmount())
                        .add(periodRecord.getFinedRepayAmount())
                        .add(periodRecord.getOffblFinedRepayAmount());
            }

            // 整笔贷款产生的逾期利息
            String coreDate = DateUtil.formatDate(repayRecord.getCoreSysDate());
            BigDecimal overdueInterest = busiCommonService
                    .getOverdueInterest(repayRecord.getAssetNo(), coreDate);

            // 历史已还金额
            BigDecimal repaidPrincipalHis = busiCommonService
                    .getRepaidPrincipalHistory(repayRecord.getAssetNo(), repayRecord.getRepayAssemblyId());
            BigDecimal repaidInterestHis = busiCommonService
                    .getRepaidInterestHistory(repayRecord.getAssetNo(), repayRecord.getRepayAssemblyId());

            // 非结清时计算结欠本金和结欠利息
            if (!"clean".equals(repayRecord.getRepayType())) {
                unsettlePrincipal = asset.getAssetGrantedPrincipalAmount().subtract(repaidPrincipalHis);

                // 结欠利息 = 所有本金利息 + 逾期产生的利息 - 已还利息
                unsettleInterest = actualInterest.add(overdueInterest).subtract(repaidInterestHis);
            }

            refundRec.setRepaidPrincipalAmount(repaidPrincipal.toPlainString());
            refundRec.setUnsettlePrincipalAmount(unsettlePrincipal.toPlainString());
            refundRec.setRepaidInterestAmount(repaidInterest.toPlainString());
            refundRec.setUnsettleInterestAmount(unsettleInterest.toPlainString());
            refundRec.setRepaidSumAmount(repaidPrincipal.add(repaidInterest).toPlainString());
            refundRec.setUnsettleSumAmount(unsettlePrincipal.add(unsettleInterest).toPlainString());
            refundRec.setAcctDate(repayRecord.getCoreSysDate());
            refundReceiptList.add(refundRec);
        }

        // 保存还款回单记录
        if (!refundReceiptList.isEmpty()) {
            log.info("RefundReceipt record is {}.", refundReceiptList.size());
            refundReceiptService.saveBatch(refundReceiptList);
        }

        log.info("RefundReceiptThread end..." + new Date());
    }

}
