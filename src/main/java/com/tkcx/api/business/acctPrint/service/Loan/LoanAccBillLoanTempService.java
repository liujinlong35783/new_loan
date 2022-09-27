package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.LoanAccBillLoanDao;
import com.tkcx.api.business.acctPrint.dao.Loan.LoanAccBillLoanTempDao;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanTempModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanTempModel;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Service
public class LoanAccBillLoanTempService extends CommonService<LoanAccBillLoanTempDao, LoanAccBillLoanTempModel> {

    @Resource
    private LoanAccBillLoanTempDao loanAccBillLoanTempDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanAccBillLoanTempModel entity) {
        return loanAccBillLoanTempDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanAccBillLoanTempDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanAccBillLoanTempModel entity) {
        return this.updateById(entity);
    }

    
    public LoanAccBillLoanTempModel selectById(Integer id) {
        return loanAccBillLoanTempDao.selectById(id);
    }

    
    public LoanAccBillLoanTempModel selectOne(LoanAccBillLoanTempModel entity) {
        QueryWrapper<LoanAccBillLoanTempModel> queryWrapper = new QueryWrapper<>(entity);
        return loanAccBillLoanTempDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanAccBillLoanTempModel model) {
        return loanAccBillLoanTempDao.selectModelCount(model);
    }

    
    public List<LoanAccBillLoanTempModel> selectList(LoanAccBillLoanTempModel model) {
        return loanAccBillLoanTempDao.selectModelList(model);
    }

    public LoanAccBillLoanTempModel getModelByLoanAccount(String loanAccount, Integer principalStatus){
        if(StringUtils.isEmpty(loanAccount))return null;
        return loanAccBillLoanTempDao.getModelByLoanAccount(loanAccount, principalStatus);
    }

    /**
     * 通过会计日期获取会计科目数据
     *
     * @param curDate
     * @return
     */
    public List<LoanAccBillLoanTempModel> queryAccBillByAcctDate(Date curDate){
        List<LoanAccBillLoanTempModel> accBillLoanTempModels = loanAccBillLoanTempDao.queryAccBillByAcctDate(curDate);
        return accBillLoanTempModels;
    }
}
