package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.LoanDetailBillModel;
import com.tkcx.api.business.wdData.model.*;
import com.tkcx.api.constant.EnumConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 贷款明细账
 *
 * @author cuijh
 * @since 2019-08-06
 */
@Slf4j
public class LoanDetailBillThread extends AcctBaseThread {

    public LoanDetailBillThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){
        Date startDate = new Date();
        log.info("LoanDetailBillThread start..." + startDate);

        // 查询前一天还款记录
        QueryWrapper queryWrapper = getQueryWrapper(RepayAssemblyRecordModel.class, "repayFinishTime");
        // 查询网贷还款记录
        // TODO 改成分页查询
        List<RepayAssemblyRecordModel> repayList = repayAssemblyRecordService.list(queryWrapper);
        log.info("LoanDetailBill of RepayAssemblyRecord record is {}.", repayList.size());
        AssetModel asset;
        CardiiModel cardii;
        // 贷款明细账
        List<LoanDetailBillModel> loanDetailBillList = new ArrayList<>();
        for (RepayAssemblyRecordModel repayRecord : repayList) {

            BigDecimal payoffPrincipal = new BigDecimal(0);// 还本金额
            BigDecimal payoffInterest = new BigDecimal(0);// 还息金额
            BigDecimal payoffAmount = new BigDecimal(0);// 已还金额
            BigDecimal principalBalance = new BigDecimal(0);// 本金余额
            BigDecimal repaidPrincipal = new BigDecimal(0);// 所有已还本金

            // 还款记录对应的期次记录
            queryWrapper = new QueryWrapper();
            queryWrapper.eq("ASSEMBLY_ID", repayRecord.getRepayAssemblyId());
            // 查询网贷还款记录
            // TODO 分页查询
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
                    .getRepaidPrincipalHistory(repayRecord.getAssetNo(), repayRecord.getRepayAssemblyId());

            LoanDetailBillModel loanDetailBill = new LoanDetailBillModel();
            loanDetailBill.setPayoffDate(repayRecord.getRepayFinishTime());
            loanDetailBill.setPayoffPrincipal(payoffPrincipal.toPlainString());
            loanDetailBill.setPayoffInterest(payoffInterest.toPlainString());
            loanDetailBill.setValidDate(repayRecord.getCoreSysDate());
            loanDetailBill.setAmount(payoffAmount.toPlainString());
            loanDetailBill.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);

            // 资产信息
            asset = busiCommonService.getAssetModel(repayRecord.getAssetNo());
            if (null != asset) {
                loanDetailBill.setOrgCode(asset.getOrgid());
                loanDetailBill.setOrgName(busiCommonService.getOrgNameByCode(loanDetailBill.getOrgCode()));
                loanDetailBill.setGrantDate(asset.getAssetActualGrantAt());
                loanDetailBill.setGtantAmount(asset.getAssetGrantedPrincipalAmount().toPlainString());
                loanDetailBill.setDebtNo(asset.getAssetDebtNo());
                loanDetailBill.setLoanAccount(asset.getAssetLoanAccount());
                loanDetailBill.setContractNo(busiCommonService.getContractNoByAssetItemNo(asset.getAssetItemNo()));
                loanDetailBill.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
                principalBalance = asset.getAssetGrantedPrincipalAmount().subtract(repaidPrincipal);
            }

            // 二类户信息
            if(StringUtils.isNotEmpty(loanDetailBill.getBorrowerIdnum())) {
                cardii = cardiiService.getCardiiByCardiiIdnum(loanDetailBill.getBorrowerIdnum());
                if (null != cardii) {
                    loanDetailBill.setLoanName(cardii.getCardiiName());
                }else{
                    log.info("AssetBorrowerIdnum{} of CardII is null", loanDetailBill.getBorrowerIdnum());
                }
            }

            loanDetailBill.setPrincipalBalance(principalBalance.toPlainString());
            loanDetailBill.setAcctDate(repayRecord.getCoreSysDate());
            loanDetailBillList.add(loanDetailBill);
        }

        // 查询前一天放款记录
        // TODO 分页查询
        queryWrapper = getQueryWrapper(AssetGrantRecordModel.class, "assetGrantRecordCreateAt");
        List<AssetGrantRecordModel> grantRecordList = assetGrantRecordService.list(queryWrapper);
        log.info("LoanDetailBill of AssetGrantRecord record is {}.", grantRecordList.size());
        for (AssetGrantRecordModel grantRecord : grantRecordList) {
            LoanDetailBillModel loanDetailBill = new LoanDetailBillModel();
            loanDetailBill.setPayoffPrincipal("0");
            loanDetailBill.setPayoffInterest("0");
            loanDetailBill.setAmount("0");
            loanDetailBill.setValidDate(grantRecord.getAssetGrantRecordCreateAt());
            loanDetailBill.setGtantAmount(grantRecord.getAssetGrantRecordAmount().toString());
            loanDetailBill.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);

            // 资产信息
            asset = busiCommonService.getAssetModel(grantRecord.getAssetGrantRecordAssetItemNo());
            if (null != asset) {
                if (!"repay".equals(asset.getAssetStatus()) && !"payoff".equals(asset.getAssetStatus())) {
                    continue;
                }
                loanDetailBill.setOrgCode(asset.getOrgid());
                loanDetailBill.setOrgName(busiCommonService.getOrgNameByCode(loanDetailBill.getOrgCode()));
                loanDetailBill.setGrantDate(asset.getAssetActualGrantAt());
                loanDetailBill.setDebtNo(asset.getAssetDebtNo());
                loanDetailBill.setLoanAccount(asset.getAssetLoanAccount());
                loanDetailBill.setContractNo(busiCommonService.getContractNoByAssetItemNo(asset.getAssetItemNo()));
                loanDetailBill.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
            }

            // 二类户信息
            if(StringUtils.isNotEmpty(loanDetailBill.getBorrowerIdnum())) {
                cardii = cardiiService.getCardiiByCardiiIdnum(loanDetailBill.getBorrowerIdnum());
                if (null != cardii) {
                    loanDetailBill.setLoanName(cardii.getCardiiName());
                }else{
                    log.info("AssetBorrowerIdnum{} of CardII is null", loanDetailBill.getBorrowerIdnum());
                }
            }
            loanDetailBill.setPrincipalBalance(loanDetailBill.getGtantAmount());
            loanDetailBill.setAcctDate(grantRecord.getCoreSysDate());
            loanDetailBillList.add(loanDetailBill);
        }

        // 保存贷款明细账
        if (!loanDetailBillList.isEmpty()) {
            log.info("LoanDetailBill record is {}." , loanDetailBillList.size());
            loanDetailBillService.saveBatch(loanDetailBillList);
        }
        Date endDate = new Date();
        log.info("LoanDetailBillThread end..." + endDate);
        log.info("LoanDetailBillThread end：{},定时任务耗时：{}", endDate, DateUtil.formatBetween( endDate,startDate));

    }
}
