package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
import com.tkcx.api.business.acctPrint.model.Loan.AccountVoucherLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanReceiptLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.RefundReceiptLoanModel;
import com.tkcx.api.business.acctPrint.service.Loan.LoanReceiptLoanService;
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

    /**
     * 当前执行日期
     */
    private Date date;
    public AcctVoucherThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){

        log.info("AcctVoucherThread start..." + new Date());
        AccountVoucherLoanModel model = new AccountVoucherLoanModel();
        model.setAcctDate(super.getCurDate());
        List<AccountVoucherLoanModel> accountVoucherLoanList = accountVoucherLoanService.selectList(model);
        ArrayList<AccountVoucherLoanModel> modelArrayList = new ArrayList<>();
        for (AccountVoucherLoanModel accountVoucherLoanModel : accountVoucherLoanList) {
            AcctDetailTempModel acctDetail = queryDetailByTransSeqNo(accountVoucherLoanModel.getTransSeqNo());
            accountVoucherLoanModel.setItemCtrl(acctDetail.getItemCtrl());
            if ("D".equals(acctDetail.getDebtFlag())) {
                accountVoucherLoanModel.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);
            } else if ("C".equals(acctDetail.getDebtFlag())) {
                accountVoucherLoanModel.setDebtFlag(EnumConstant.DEBT_FLAG_CREDIT);
            }
            if ("2".equals(acctDetail.getAcctType())) {// 红字显示负值
                accountVoucherLoanModel.setAmount("-" + ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
            } else {
                accountVoucherLoanModel.setAmount(ToolUtil.yuanToFen(acctDetail.getTransAmount().toString()));
            }
            modelArrayList.add(accountVoucherLoanModel);
        }
        if (!modelArrayList.isEmpty()) {
            log.info("BusiOrgBill record is {}.", modelArrayList.size());
            accountVoucherLoanService.updateBatchById(modelArrayList);
        }
        log.info("AcctVoucherThread end..." + new Date());

    }

    /**
     * 查询会计科目数据

     * @param transSeqNo
     * @return
     */
    private AcctDetailTempModel queryDetailByTransSeqNo(String transSeqNo){
        AcctDetailTempModel detailBySeqNo = acctDetailTempService.getDetailBySeqNo(transSeqNo);
        if (detailBySeqNo==null){
            log.info("queryDetailByTransSeqNo  detailBySeqNo==null");
        }
        return detailBySeqNo;
    }
}
