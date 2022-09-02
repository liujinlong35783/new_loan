package com.tkcx.api.common;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.business.acctPrint.model.LoanDetailBillModel;
import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;
import com.tkcx.api.business.acctPrint.model.RefundReceiptModel;
import com.tkcx.api.business.realTime.dao.InterestBillSyncDao;
import com.tkcx.api.business.realTime.dao.LoanDetailBillSyncDao;
import com.tkcx.api.business.realTime.dao.LoanReceiptSyncDao;
import com.tkcx.api.business.realTime.dao.RefundReceiptSyncDao;
import com.tkcx.api.business.realTime.model.InterestBillSyncModel;
import com.tkcx.api.business.realTime.model.LoanDetailBillSyncModel;
import com.tkcx.api.business.realTime.model.LoanReceiptSyncModel;
import com.tkcx.api.business.realTime.model.RefundReceiptSyncModel;
import com.tkcx.api.business.wdData.model.RepayPeriodRecordModel;
import com.tkcx.api.business.wdData.service.RepayPeriodRecordService;
import com.tkcx.api.vo.query.InterestBillQuery;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import com.tkcx.api.vo.query.LoanReceiptQuery;
import com.tkcx.api.vo.query.RefundReceiptQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 实时查询业务类
 *
 * @author cuijh
 * @since 2019/10/23 9:46
 */
@Slf4j
@Service
public class RealTimeQueryService {

    @Resource
    private BusiCommonService busiCommonService;

    @Resource
    private RepayPeriodRecordService repayPeriodRecordService;

    @Resource
    private RefundReceiptSyncDao refundReceiptSyncDao;

    @Resource
    private LoanReceiptSyncDao loanReceiptSyncDao;

    @Resource
    private LoanDetailBillSyncDao loanDetailBillSyncDao;

    @Resource
    private InterestBillSyncDao interestBillSyncDao;

    public List realTime(Object query){
        String start = "", end = "", acctDate = "";
        Date coreSysDate = busiCommonService.getCoreSysDate();
        //测试使用
/*        Calendar calendar = Calendar.getInstance();
        calendar.set(2023,11,21);
        Date coreSysDate = calendar.getTime();*/
        if(coreSysDate!=null){
            acctDate = DateUtil.format(coreSysDate, "yyyy-MM-dd");
        } else return null;
        RefundReceiptQuery refundReceiptQuery;
        LoanReceiptQuery loanReceiptQuery;
        LoanBillAndAdjustQuery loanBillAndAdjustQuery;
        InterestBillQuery interestBillQuery;
        if(query instanceof RefundReceiptQuery){
            refundReceiptQuery = (RefundReceiptQuery) query;
            start = DateUtil.format(refundReceiptQuery.getTimeSegment().getStartDate(), "yyyy-MM-dd");
            end = DateUtil.format(refundReceiptQuery.getTimeSegment().getEndDate(), "yyyy-MM-dd");
            if (start.equals(start) && end.equals(acctDate)) {
                log.info("开始实时查询还款回单");
                return queryRefundReceiptByRealTime(refundReceiptQuery);
            }
        }else if(query instanceof LoanReceiptQuery){
            loanReceiptQuery = (LoanReceiptQuery) query;
            start = DateUtil.format(loanReceiptQuery.getTimeSegment().getStartDate(), "yyyy-MM-dd");
            end = DateUtil.format(loanReceiptQuery.getTimeSegment().getEndDate(), "yyyy-MM-dd");
            if (start.equals(start) && end.equals(acctDate)) {
                log.info("开始实时查询借款回单");
                return queryLoanReceiptByRealTime(loanReceiptQuery);
            }
        }else if(query instanceof LoanBillAndAdjustQuery){
            loanBillAndAdjustQuery = (LoanBillAndAdjustQuery) query;
            start = DateUtil.format(loanBillAndAdjustQuery.getTimeSegment().getStartDate(), "yyyy-MM-dd");
            end = DateUtil.format(loanBillAndAdjustQuery.getTimeSegment().getEndDate(), "yyyy-MM-dd");
            if (start.equals(start) && end.equals(acctDate)) {
                log.info("开始实时查询明细账");
                return queryLoanDetailBillByRealTime(loanBillAndAdjustQuery);
            }
        }else if(query instanceof InterestBillQuery){
            interestBillQuery = (InterestBillQuery) query;
            start = DateUtil.format(interestBillQuery.getTimeSegment().getStartDate(), "yyyy-MM-dd");
            end = DateUtil.format(interestBillQuery.getTimeSegment().getEndDate(), "yyyy-MM-dd");
            if (start.equals(start) && end.equals(acctDate)) {
                log.info("开始实时查询利息登记薄");
                return queryInterestBillByRealTime(interestBillQuery);
            }
        }
        return null;
    }
    /**
     * 还款回单实时查询
     * @param query
     * @return
     */
    public List<RefundReceiptModel> queryRefundReceiptByRealTime(RefundReceiptQuery query) {
        List<RefundReceiptModel> receiptModels = new ArrayList<>();

        List<RefundReceiptSyncModel> receiptSyncModels = refundReceiptSyncDao.selectListByRealTimeQuery(query);
        for (RefundReceiptSyncModel syncModel:receiptSyncModels) {
            RefundReceiptModel receipt = new RefundReceiptModel();

            // 复制属性
            BeanUtils.copyProperties(syncModel, receipt);

            // 实际利息
            BigDecimal actualInterest = busiCommonService.getInterestAmountByAsset(syncModel
                    .getAssetNo(), syncModel.getAssemblyId());

            BigDecimal repaidPrincipal = new BigDecimal(0);// 偿还本金金额
            BigDecimal unsettlePrincipal = new BigDecimal(0);// 结欠本金金额
            BigDecimal repaidInterest = new BigDecimal(0);// 偿还利息金额
            BigDecimal unsettleInterest = new BigDecimal(0);// 结欠利息金额

            // 还款期次信息
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("ASSEMBLY_ID", syncModel.getAssemblyId());
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
            String coreDate = DateUtil.formatDate(syncModel.getAcctDate());
            BigDecimal overdueInterest = busiCommonService
                    .getOverdueInterest(syncModel.getAssetNo(), coreDate);

            // 历史已还金额
            BigDecimal repaidPrincipalHis = busiCommonService
                    .getRepaidPrincipalHistory(syncModel.getAssetNo(), syncModel.getAssemblyId());
            BigDecimal repaidInterestHis = busiCommonService
                    .getRepaidInterestHistory(syncModel.getAssetNo(), syncModel.getAssemblyId());

            // 非结清时计算结欠本金和结欠利息
            if (!"clean".equals(syncModel.getRepayType())) {
                unsettlePrincipal = new BigDecimal(syncModel.getLoanAmount()).subtract(repaidPrincipalHis);

                // 结欠利息 = 所有本金利息 + 逾期产生的利息 - 已还利息
                unsettleInterest = actualInterest.add(overdueInterest).subtract(repaidInterestHis);
            }

            receipt.setRepaidPrincipalAmount(repaidPrincipal.toPlainString());
            receipt.setUnsettlePrincipalAmount(unsettlePrincipal.toPlainString());
            receipt.setRepaidInterestAmount(repaidInterest.toPlainString());
            receipt.setUnsettleInterestAmount(unsettleInterest.toPlainString());
            receipt.setRepaidSumAmount(repaidPrincipal.add(repaidInterest).toPlainString());
            receipt.setUnsettleSumAmount(unsettlePrincipal.add(unsettleInterest).toPlainString());
            receipt.setOrgName(busiCommonService.getOrgNameByCode(syncModel.getOrgCode()));
            receipt.setContractNo(busiCommonService.getContractNoByAssetItemNo(syncModel.getAssetNo()));

            receiptModels.add(receipt);
        }

        return receiptModels;
    }


    /**
     * 借款回单实时查询
     *
     * @param query
     * @return
     */
    public List<LoanReceiptModel> queryLoanReceiptByRealTime(LoanReceiptQuery query) {
        List<LoanReceiptModel> receiptModels = new ArrayList<>();

        List<LoanReceiptSyncModel> receiptSyncModels = loanReceiptSyncDao.selectListByRealTimeQuery(query);
        for (LoanReceiptSyncModel syncModel : receiptSyncModels) {
            LoanReceiptModel receipt = new LoanReceiptModel();

            // 复制属性
            BeanUtils.copyProperties(syncModel, receipt);

            receipt.setReceiverAccount(busiCommonService.selectAccCode(syncModel.getBorrowerIdnum()));
            //receipt.setContractNo(busiCommonService.getContractNoByAssetItemNo(syncModel.getAssetNo()));
            receipt.setOrgName(busiCommonService.getOrgNameByCode(syncModel.getOrgCode()));
            receiptModels.add(receipt);
        }

        return receiptModels;
    }

    /**
     * 贷款明细账实时查询
     *
     * @param query
     * @return
     */
    public List<LoanDetailBillModel> queryLoanDetailBillByRealTime(LoanBillAndAdjustQuery query) {
        List<LoanDetailBillModel> billModels = new ArrayList<>();

        List<LoanDetailBillSyncModel> detailSyncModels = loanDetailBillSyncDao.selectListByRealTimeQuery(query);
        for (LoanDetailBillSyncModel syncModel : detailSyncModels) {
            LoanDetailBillModel detailBill = new LoanDetailBillModel();

            // 复制属性
            BeanUtils.copyProperties(syncModel, detailBill);

            if(syncModel.getAssemblyId() > 0) {

                BigDecimal payoffPrincipal = new BigDecimal(0);// 还本金额
                BigDecimal payoffInterest = new BigDecimal(0);// 还息金额
                BigDecimal payoffAmount = new BigDecimal(0);// 已还金额
                BigDecimal principalBalance = new BigDecimal(0);// 本金余额
                BigDecimal repaidPrincipal = new BigDecimal(0);// 所有已还本金

                // 还款记录对应的期次记录
                QueryWrapper queryWrapper = new QueryWrapper();
                queryWrapper.eq("ASSEMBLY_ID", syncModel.getAssemblyId());
                List<RepayPeriodRecordModel> repayPeriodList = repayPeriodRecordService.list(queryWrapper);
                for (RepayPeriodRecordModel repayPeriod : repayPeriodList) {
                    payoffPrincipal = payoffPrincipal.add(repayPeriod.getPrincipalRepayAmount());
                    payoffInterest = payoffInterest.add(repayPeriod.getInterestRepayAmount())
                            .add(repayPeriod.getOffblInterestRepayAmount())
                            .add(repayPeriod.getExtendInterestRepayAmount())
                            .add(repayPeriod.getFinedRepayAmount()).add(repayPeriod.getOffblFinedRepayAmount());
                    payoffAmount = payoffAmount.add(repayPeriod.getCurrRepayAmount());
                }

                repaidPrincipal = busiCommonService
                        .getRepaidPrincipalHistory(syncModel.getAssetNo(), syncModel.getAssemblyId());
                principalBalance = new BigDecimal(syncModel.getGtantAmount()).subtract(repaidPrincipal);

                detailBill.setPayoffPrincipal(payoffPrincipal.toPlainString());
                detailBill.setPayoffInterest(payoffInterest.toPlainString());
                detailBill.setAmount(payoffAmount.toPlainString());
                detailBill.setPrincipalBalance(principalBalance.toPlainString());
            }

            detailBill.setContractNo(busiCommonService.getContractNoByAssetItemNo(syncModel.getAssetNo()));
            detailBill.setOrgName(busiCommonService.getOrgNameByCode(syncModel.getOrgCode()));
            billModels.add(detailBill);
        }

        return billModels;
    }

    /**
     * 贷款利息登记簿实时查询
     *
     * @param query
     * @return
     */
    public List<InterestBillModel> queryInterestBillByRealTime(InterestBillQuery query) {

        List<InterestBillModel> interestBills = new ArrayList<>();

        List<InterestBillSyncModel> billSyncModels = interestBillSyncDao.selectListByRealTimeQuery(query);
        for (InterestBillSyncModel syncModel : billSyncModels) {
            InterestBillModel interestBill = new InterestBillModel();

            // 复制属性
            BeanUtils.copyProperties(syncModel, interestBill);
            interestBill.setShouldOtherSum("0");
            interestBill.setPayoffOtherSum("0");

            if (syncModel.getAssemblyId() > 0) {
                //应还费用
                BigDecimal[] shouldAmount =  busiCommonService.getPrincipalHistory(syncModel.getAssetNo(),
                        syncModel.getAssemblyId(), syncModel.getAcctDate());
                //已还费用
                BigDecimal[] payoffAmount =  busiCommonService.getPayoffPrincipalHistory(syncModel.getAssemblyId());

                BigDecimal principalAmount = shouldAmount[0];// 还款计划本金
                BigDecimal repayPrincipal = payoffAmount[0];// 已还本金
                BigDecimal interestAmount = shouldAmount[1];// 还款计划正常利息
                BigDecimal repayInterest = payoffAmount[1];// 已还正常利息
                BigDecimal overdueAmount = shouldAmount[2];// 还款计划逾期利息
                BigDecimal repayOverdue = payoffAmount[2];// 已还逾期利息

                BigDecimal shouldSum = principalAmount.add(interestAmount).add(overdueAmount);
                BigDecimal payoffSum = repayPrincipal.add(repayInterest).add(repayOverdue);

                interestBill.setShouldPrincipal(principalAmount.toPlainString());
                interestBill.setShouldNormalInterest(interestAmount.toPlainString());
                interestBill.setShouldOverdueInterest(overdueAmount.toPlainString());
                interestBill.setShouldSum(shouldSum.toPlainString());

                interestBill.setPayoffPrincipal(repayPrincipal.toPlainString());
                interestBill.setPayoffNormalInterest(repayInterest.toPlainString());
                interestBill.setPayfoffOverdueInterest(repayOverdue.toPlainString());
                interestBill.setPayoffSum(payoffSum.toPlainString());
            } else {
                Map<String, Object> repayPlanSum = busiCommonService
                        .selectPrincipalInterestHis(syncModel.getAssetNo(), syncModel.getRepayPlanNo());
                Map<String, Object> repaidSum = busiCommonService
                        .selectPrincipalInterestRepaid(syncModel.getAssetNo(),
                                syncModel.getRepayPlanNo(), DateUtil.offsetDay(syncModel.getAcctDate(), 1));

                BigDecimal repayAmount = (BigDecimal)repaidSum.get("CURR_REPAY_AMOUNT");
                BigDecimal principal = (BigDecimal)repayPlanSum.get("PRINCIPAL");// 本金
                BigDecimal repaidPrincipal = (BigDecimal)repaidSum.get("PRINCIPAL_REPAY_AMOUNT");// 已还本金
                BigDecimal interest = (BigDecimal)repayPlanSum.get("INTEREST");// 实际的足期的本金利息
                BigDecimal repaidInterest = (BigDecimal)repaidSum.get("INTEREST_REPAY_AMOUNT");// 已还本金利息
                BigDecimal overdueInterest = busiCommonService
                        .selectOverInterestHis(syncModel.getAssetNo(),
                                syncModel.getRepayPlanNo(), DateUtil.offsetDay(syncModel.getAcctDate(), 1)); // 逾期利息

                BigDecimal shouldPrincipal = principal.subtract(repaidPrincipal);
                BigDecimal shouldNormalInterest = interest.subtract(repaidInterest);
                BigDecimal shouldOverdueInterest = overdueInterest.subtract(repayAmount)
                        .add(repaidPrincipal).add(repaidInterest);

                BigDecimal shouldSum = shouldPrincipal.add(shouldNormalInterest).add(shouldOverdueInterest);

                if ("interest_settle".equals(syncModel.getSettleType())) {
                    // 正常利息结息
                    interestBill.setShouldPrincipal(shouldPrincipal.toPlainString());
                    interestBill.setShouldNormalInterest(shouldNormalInterest.toPlainString());
                    interestBill.setShouldOverdueInterest("0");
                    interestBill.setShouldSum(principal.add(interest).toPlainString());
                } else {
                    // 逾期利息结息时存在还款记录，则以还款记录为准
                    interestBill.setShouldPrincipal(shouldPrincipal.toString());
                    interestBill.setShouldNormalInterest(shouldNormalInterest.toString());
                    interestBill.setShouldOverdueInterest(shouldOverdueInterest.toString());
                    interestBill.setShouldSum(shouldSum.toString());
                }

                interestBill.setPayoffPrincipal("0");
                interestBill.setPayoffNormalInterest("0");
                interestBill.setPayfoffOverdueInterest("0");
                interestBill.setPayoffSum("0");
            }

            interestBill.setContractNo(busiCommonService.getContractNoByAssetItemNo(syncModel.getAssetNo()));
            interestBill.setOrgName(busiCommonService.getOrgNameByCode(syncModel.getOrgCode()));

            interestBills.add(interestBill);
        }

        return interestBills;
    }
}
