package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;
import com.tkcx.api.business.wdData.model.AssetGrantRecordModel;
import com.tkcx.api.business.wdData.model.AssetIndividualModel;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.constant.EnumConstant;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 借款借据回单信息
 *
 * @author cuijh
 * @since 2019-08-15
 */
@Slf4j
public class LoanReceiptThread extends AcctBaseThread {


    public LoanReceiptThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run() {

        log.info("LoanReceiptThread start..." + new Date());

        // 查询前一天放款记录
        QueryWrapper queryWrapper = getQueryWrapper(AssetGrantRecordModel.class, "assetGrantRecordCreateAt");
        // 查询网贷 放款记录
        List<AssetGrantRecordModel> grantRecordList = assetGrantRecordService.list(queryWrapper);
        log.info("LoanReceipt of AssetGrantRecord record is {}.", grantRecordList.size());

        List<LoanReceiptModel> loanReceiptList = new ArrayList<>();
        for (AssetGrantRecordModel grantRecord:grantRecordList) {

            LoanReceiptModel loanReceipt = new LoanReceiptModel();

            loanReceipt.setLoanType(EnumConstant.LOAN_TYPE_SHORT);
            loanReceipt.setLoanDate(grantRecord.getAssetGrantRecordStartDate());
            loanReceipt.setDueDate(grantRecord.getAssetGrantRecordEndDate());
            loanReceipt.setLoanAmount(grantRecord.getAssetGrantRecordAmount().toString());

            String assetItemNo = grantRecord.getAssetGrantRecordAssetItemNo();
            // 查询网贷 资产信息
            AssetModel asset = busiCommonService.getAssetModel(assetItemNo);
            if (null != asset) {
                if (!"repay".equals(asset.getAssetStatus()) && !"payoff".equals(asset.getAssetStatus())) {
                    continue;
                }

                loanReceipt.setReceiverAccount(busiCommonService.selectAccCode(asset.getAssetBorrowerIdnum()));
                loanReceipt.setLoanAccount(asset.getAssetLoanAccount());
                loanReceipt.setContractNo(busiCommonService.getContractNoByAssetItemNo(assetItemNo));
                loanReceipt.setBorrowerIdnum(asset.getAssetBorrowerIdnum());
                loanReceipt.setLoanUsage(asset.getAssetLoanUsage());
                loanReceipt.setInterestRate(asset.getAssetInterestRate());
                loanReceipt.setOrgCode(asset.getOrgid());
                loanReceipt.setOrgName(busiCommonService.getOrgNameByCode(loanReceipt.getOrgCode()));

                String interestType = asset.getAssetInterestType();
                if(interestType!=null&&interestType.equals("eq_loan_pmt")){
                    loanReceipt.setPayoffType(EnumConstant.EQ_LOAN_PMT);
                }else if(interestType!=null&&interestType.equals("eq_principal_pmt")){
                    loanReceipt.setPayoffType(EnumConstant.EQ_PRINCIPAL_PMT);
                    //我改的
                }else if(interestType!=null&&interestType.equals("pay_int_principal_due")){
                    loanReceipt.setPayoffType(EnumConstant.pay_int_principal_due);
                }
            } else {
                log.info("{} assetItemNo of Asset is null", assetItemNo);
            }

            // 借款人信息
            AssetIndividualModel individaul = new AssetIndividualModel();
            individaul.setAssetIndividualAssetItemNo(assetItemNo);
            individaul.setAssetIndividualType("borrow");
            individaul = assetIndividualService.selectOne(individaul);
            if (null != individaul) {
                loanReceipt.setBorrowerName(individaul.getAssetIndividualName());
                loanReceipt.setBorrowerAddr(individaul.getAssetIndividualIdAddr());
            } else {
                log.info("{} assetItemNo of AssetIndividual is null", assetItemNo);
            }

            // 原来是loanReceipt.setAcctDate(grantRecord.getCoreSysDate()); DATE转换格式 yyyy-mm-dd hh:mi:ss转成yyyy-mm-dd
            loanReceipt.setAcctDate(DateUtil.parse(DateUtil.formatDate(grantRecord.getCoreSysDate()), "yyyy-MM-dd"));
            loanReceiptList.add(loanReceipt);
        }

        if (!loanReceiptList.isEmpty()) {
            log.info("LoanReceipt record is {}." ,loanReceiptList.size());
            loanReceiptService.saveBatch(loanReceiptList);
        }

        log.info("LoanReceiptThread end..." + new Date());
    }


}
