package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.wdData.model.*;
import com.tkcx.api.constant.EnumConstant;
import com.tkcx.api.utils.ToolUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 会计凭证(记账凭证/交易凭证)
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class AcctVoucherThread extends AcctBaseThread {

    public AcctVoucherThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){
        Date startDate = new Date();
        log.info("AcctVoucherThread start..." + startDate);

        // 查询前一天放款记录
        QueryWrapper queryWrapper = getQueryWrapper(AssetGrantRecordModel.class, "assetGrantRecordCreateAt");
        // 查询网贷系统放款记录
        List<AssetGrantRecordModel> grantRecordList = assetGrantRecordService.list(queryWrapper);
        log.info("AcctVoucher of AssetGrantRecord record is {}." ,grantRecordList.size());
        // 资产信息
        AssetModel asset;
        List<AccountVoucherModel> acctVoucherList = new ArrayList<>();
        for (AssetGrantRecordModel grantRecord : grantRecordList) {

            String voucherNo = "";

            // 查询网贷系统资产信息
            asset = busiCommonService.getAssetModel(grantRecord.getAssetGrantRecordAssetItemNo());
            if (null != asset) {
                if (!"repay".equals(asset.getAssetStatus()) && !"payoff".equals(asset.getAssetStatus())) {
                    continue;
                }
                voucherNo = busiCommonService.getVoucherNo(asset.getOrgid(),
                        DateUtil.format(grantRecord.getAssetGrantRecordCreateAt(), "yyyyMMdd"));
            }

            // 查询业务流水对应的分录账
            String transSeqNo = busiCommonService.selectChannelSeq(grantRecord.getAssetGrantRecordReqTx());
            if (StringUtils.isEmpty(transSeqNo)) {
                log.info("No assetGrantRecordReqTx {} corresponding to transSeqNo", grantRecord.getAssetGrantRecordReqTx());
                continue;
            }

            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("CHANNEL_SEQ",transSeqNo);
            queryWrapper.notIn("ITEM_CTRL","304101","304102","304112","304113");
            // 查询会计凭证ACCT_DETAIL_TEMP表
            List<AcctDetailTempModel> detailList = acctDetailTempService.list(queryWrapper);
            for (AcctDetailTempModel acctDetail : detailList) {
                AccountVoucherModel accVoucher = new AccountVoucherModel();

                accVoucher.setAccountName(acctDetail.getAccountName());
                accVoucher.setAccountCode(acctDetail.getAccountCode());
                accVoucher.setBusiType(acctDetail.getServiceName());
                accVoucher.setCurrency("CNY");
                accVoucher.setSerialNo(grantRecord.getAssetGrantRecordReqTx());
                accVoucher.setAcctDate(grantRecord.getCoreSysDate());
                accVoucher.setAbstracts("放款");
                accVoucher.setOperator("QNWD");
                accVoucher.setItemCtrl(acctDetail.getItemCtrl());
                accVoucher.setTransferFlag(EnumConstant.TRANSFER_ELEC);
                if ("D".equals(acctDetail.getDebtFlag())) {
                    accVoucher.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
                } else if ("C".equals(acctDetail.getDebtFlag())) {
                    accVoucher.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);
                }

                if (null != asset) {
                    accVoucher.setLoanAccount(asset.getAssetLoanAccount());
                    accVoucher.setDebtNo(asset.getAssetDebtNo());
                    // 查询网贷ASSET_ATTACHMENT表
                    accVoucher.setContractNo(busiCommonService.getContractNoByAssetItemNo(asset.getAssetItemNo()));
                    accVoucher.setOrgCode(asset.getOrgid());
                    // 查询会计凭证ACCT_ORG_TEMP表
                    accVoucher.setOrgName(busiCommonService.getOrgNameByCode(asset.getOrgid()));
                    accVoucher.setBusiDate(grantRecord.getAssetGrantRecordCreateAt());
                    accVoucher.setVoucherNo(voucherNo);
                    if ("2".equals(acctDetail.getAcctType())) {
                        accVoucher.setAmount("-" + ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
                    } else {
                        accVoucher.setAmount(ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
                    }
                } else {
                    log.info("{} assetItemNo of Asset is null", grantRecord.getAssetGrantRecordAssetItemNo());
                }

                acctVoucherList.add(accVoucher);
            }

        }

        // 查询前一天还款期次记录
        queryWrapper = getQueryWrapper(RepayPeriodRecordModel.class, "coreSysDate");
        // 查询网贷系统放款记录
        List<RepayPeriodRecordModel> repayList = repayPeriodRecordService.list(queryWrapper);
        log.info("AcctVoucher of RepayPeriodRecord record is {}.", repayList.size());

        for (RepayPeriodRecordModel repayPeriod : repayList) {
            if (null == repayPeriod.getAcctRecordNo()) {
                log.info("acctRecordNo of RepayPeriodRecordModel is null {}.", repayPeriod.getRepayRecordId());
                continue;
            }

            String voucherNo = "";

            // 资产信息
            asset = busiCommonService.getAssetModel(repayPeriod.getAssetNo());
            if (null != asset) {
                voucherNo = busiCommonService.getVoucherNo(asset.getOrgid(),
                        DateUtil.format(repayPeriod.getRepayCreateDate(), "yyyyMMdd"));
            }

            // 会计记账分录
            queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("CHANNEL_SEQ",repayPeriod.getAcctRecordNo());
            queryWrapper.notIn("ITEM_CTRL","304101","304102","304112","304113");
            // 查询会计凭证ACCT_DETAIL_TEMP表
            List<AcctDetailTempModel> detailList = acctDetailTempService.list(queryWrapper);
            for (AcctDetailTempModel acctDetail : detailList) {

                AccountVoucherModel accVoucher = new AccountVoucherModel();
                accVoucher.setAccountName(acctDetail.getAccountName());
                accVoucher.setAccountCode(acctDetail.getAccountCode());
                accVoucher.setBusiType(acctDetail.getServiceName());
                accVoucher.setCurrency("CNY");
                accVoucher.setSerialNo(repayPeriod.getAcctRecordNo());
                accVoucher.setOperator("QNWD");
                accVoucher.setItemCtrl(acctDetail.getItemCtrl());//互金数据
                accVoucher.setAcctDate(repayPeriod.getCoreSysDate());
                accVoucher.setTransferFlag(EnumConstant.TRANSFER_ELEC);
                if ("D".equals(acctDetail.getDebtFlag())) {
                    accVoucher.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
                } else if ("C".equals(acctDetail.getDebtFlag())) {
                    accVoucher.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);
                }

                if ("PARTIAL".equals(repayPeriod.getRepayResult())) {
                    accVoucher.setAbstracts("部分还款");
                } else if("FULL".equals(repayPeriod.getRepayResult())) {
                    accVoucher.setAbstracts("当期全部还款");
                }

                if (null != asset) {
                    accVoucher.setLoanAccount(asset.getAssetLoanAccount());
                    accVoucher.setDebtNo(asset.getAssetDebtNo());
                    // 查询网贷表ASSET_ATTACHMENT
                    accVoucher.setContractNo(busiCommonService.getContractNoByAssetItemNo(asset.getAssetItemNo()));
                    accVoucher.setOrgCode(asset.getOrgid());
                    // 查询会计凭证ACCT_ORG_TEMP表
                    accVoucher.setOrgName(busiCommonService.getOrgNameByCode(asset.getOrgid()));
                    accVoucher.setBusiDate(repayPeriod.getRepayCreateDate());
                    accVoucher.setVoucherNo(voucherNo);
                    if ("2".equals(acctDetail.getAcctType())) {// 红字显示负值
                        accVoucher.setAmount("-" + ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
                    } else {
                        accVoucher.setAmount(ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
                    }
                } else {
                    log.info("{} assetItemNo of Asset is null", repayPeriod.getAssetNo());
                }

                acctVoucherList.add(accVoucher);
            }
        }


        if (!acctVoucherList.isEmpty()) {
            log.info("AcctVoucher record is {}.", acctVoucherList.size());
            accountVoucherService.saveBatch(acctVoucherList);
        }

        Date endDate = new Date();
        log.info("AcctVoucherThread end..." + endDate);
        log.info("AcctVoucherThread end：{},定时任务耗时：{}", endDate, DateUtil.formatBetween( endDate,startDate));

    }
}
