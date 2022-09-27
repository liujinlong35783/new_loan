package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgSeqLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAdjustLoanModel;
import com.tkcx.api.business.acctPrint.model.LoanAdjustModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import com.tkcx.api.business.wdData.model.AssetModel;
import com.tkcx.api.business.wdData.model.CardiiModel;
import com.tkcx.api.constant.AcctRecordScene;
import com.tkcx.api.constant.BusiCodeConstant;
import com.tkcx.api.utils.BusinessUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 贷款形态调整明细清单、贷款调整登记簿
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class LoanAdjustThread extends AcctBaseThread {

    public LoanAdjustThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run(){
        log.info("LoanAdjustThread start...");
        LoanAdjustLoanModel loanAdjustLoanModel = new LoanAdjustLoanModel();
        loanAdjustLoanModel.setAcctDate(super.getCurDate());
        List<LoanAdjustLoanModel> loanAdjustLoanList = loanAdjustLoanService.selectList(loanAdjustLoanModel);
        List<LoanAdjustLoanModel> loanAdjustList = new ArrayList<>();
        for (LoanAdjustLoanModel adjustModel : loanAdjustLoanList) {
            //借方发生科目控制字
            String dCtrl = busiCommonService.selectItemCtrl(adjustModel.getTransSeqNo(), "D");
            adjustModel.setOriginalItemCode(dCtrl);//原科目代号
            String dItemName = busiCommonService.selectItemName(dCtrl);
            adjustModel.setOriginalItemName(dItemName);//原科目名称
            //贷方发生科目控制字
            String cCtrl = busiCommonService.selectItemCtrl(adjustModel.getTransSeqNo(), "C");
            adjustModel.setNewItemCode(cCtrl);//转化后科目代号
            String cItemName = busiCommonService.selectItemName(dCtrl);
            adjustModel.setNewItemName(cItemName);//转化后科目名称

            loanAdjustList.add(adjustModel);
        }
        if (loanAdjustList.size() > 0) {
            log.info("生成贷款形态调整数据：" + loanAdjustList.size() + "条");
            loanAdjustLoanService.saveBatch(loanAdjustList);
            log.info("保存成功：" + loanAdjustList.size() + "条");
        }
    }
}
