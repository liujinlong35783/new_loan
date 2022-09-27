package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.LoanAccBillLoanDao;
import com.tkcx.api.business.acctPrint.dao.LoanAccBillDao;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanModel;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;
import com.tkcx.api.common.CommonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
@Service
public class LoanAccBillLoanService extends CommonService<LoanAccBillLoanDao, LoanAccBillLoanModel> {

    @Resource
    private LoanAccBillLoanDao loanAccBillLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanAccBillLoanModel entity) {
        return loanAccBillLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanAccBillLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanAccBillLoanModel entity) {
        return this.updateById(entity);
    }

    
    public LoanAccBillLoanModel selectById(Integer id) {
        return loanAccBillLoanDao.selectById(id);
    }

    
    public LoanAccBillLoanModel selectOne(LoanAccBillLoanModel entity) {
        QueryWrapper<LoanAccBillLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return loanAccBillLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanAccBillLoanModel model) {
        return loanAccBillLoanDao.selectModelCount(model);
    }

    
    public List<LoanAccBillLoanModel> selectList(LoanAccBillLoanModel model) {
        return loanAccBillLoanDao.selectModelList(model);
    }

    public LoanAccBillLoanModel getModelByLoanAccount(String loanAccount, Integer principalStatus){
        if(StringUtils.isEmpty(loanAccount))return null;
        return loanAccBillLoanDao.getModelByLoanAccount(loanAccount, principalStatus);
    }
    public LoanAccBillLoanModel selectByDebtNoItem(String debtNo,String item) {
        return loanAccBillLoanDao.selectByDebtNoItem(debtNo,item);
    }
}
