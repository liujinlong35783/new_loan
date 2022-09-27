package com.acct.job.thread;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgSeqLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanTempModel;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.wdData.model.*;
import com.tkcx.api.constant.AcctRecordScene;
import com.tkcx.api.constant.EnumConstant;
import com.tkcx.api.utils.BusinessUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * 贷款分户账
 *
 * @author
 * @since 2019-08-06
 */
@Slf4j
public class LoanAccBillThread extends AcctBaseThread {

    private Map<String, LoanAccBillModel> modelMap = new HashMap<>();

    public LoanAccBillThread(Date curDate) {
        super(curDate);
    }

    @Override
    public void run() {
        log.info("LoanAccBillThread start...");
        //获取会计日期相对应的新希望贷款分户账临时表数据
        Date selectDate1 = DateUtil.offsetDay(super.getCurDate(), -1);
        log.info("BusiOrgBill selectDate1 is {}.", selectDate1);
        List<LoanAccBillLoanTempModel> accBillLoanTempList = loanAccBillLoanTempService.queryAccBillByAcctDate(selectDate1);
        List<LoanAccBillLoanModel> insertAccBillModelList = new ArrayList<>();
        List<LoanAccBillLoanModel> updateAccBillModelList = new ArrayList<>();
        for (LoanAccBillLoanTempModel billLoanTempModel : accBillLoanTempList) {
            //通过临时表借据号获取对象，不存在则新增，存在则更新
            LoanAccBillLoanModel accBillLoanModel = loanAccBillLoanService.selectByDebtNoItem(billLoanTempModel.getDebtNo(),billLoanTempModel.getItem());
            LoanAccBillLoanModel loanModel = new LoanAccBillLoanModel();
            if (accBillLoanModel==null){
                //临时对象转入库信息
                BeanUtils.copyProperties(billLoanTempModel,loanModel);
                loanModel.setDebtFlag(EnumConstant.DEBT_FLAG_DEBT);//借款
                insertAccBillModelList.add(loanModel);
            }else {
                BeanUtils.copyProperties(accBillLoanModel,loanModel);
                loanModel.setBalanceAmount(billLoanTempModel.getBalanceAmount());
                loanModel.setPayoffAccount(billLoanTempModel.getPayoffAccount());
                updateAccBillModelList.add(loanModel);
            }
        }
        if (insertAccBillModelList.size()>0){
            String size = insertAccBill(insertAccBillModelList);
            log.info("LoanAccBillThread insert record:" + size + "条");
        }
        if (updateAccBillModelList.size()>0){
            String size = updateAccBill(updateAccBillModelList);
            log.info("LoanAccBillThread update record:" + size + "条");
        }
    }
    private String updateAccBill(List<LoanAccBillLoanModel> updateAccBillModelList){
        boolean result = loanAccBillLoanService.updateBatchById(updateAccBillModelList);
        if (!result){
            log.info("LoanAccBillThread updateAccBill update error");
        }
        return updateAccBillModelList.size()+"";
    }
    private String insertAccBill(List<LoanAccBillLoanModel> insertAccBillModelList){
        boolean result = loanAccBillLoanService.saveBatch(insertAccBillModelList);
        if (!result){
            log.info("LoanAccBillThread updateAccBill insert error");
        }
        return insertAccBillModelList.size()+"";
    }
}
