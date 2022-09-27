package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.LoanReceiptLoanDao;
import com.tkcx.api.business.acctPrint.dao.LoanReceiptDao;
import com.tkcx.api.business.acctPrint.model.Loan.LoanReceiptLoanModel;
import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;
import com.tkcx.api.common.CommonService;
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
public class LoanReceiptLoanService extends CommonService<LoanReceiptLoanDao, LoanReceiptLoanModel> {

    @Resource
    private LoanReceiptLoanDao loanReceiptLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanReceiptLoanModel entity) {
        return loanReceiptLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanReceiptLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanReceiptLoanModel entity) {
        return this.updateById(entity);
    }

    
    public LoanReceiptLoanModel selectById(Integer id) {
        return loanReceiptLoanDao.selectById(id);
    }

    
    public LoanReceiptLoanModel selectOne(LoanReceiptLoanModel entity) {
        QueryWrapper<LoanReceiptLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return loanReceiptLoanDao.selectOne(queryWrapper);
    }


    public List<LoanReceiptLoanModel> selectListByDate(LoanReceiptLoanModel model) {
        return loanReceiptLoanDao.selectListByDate(model);
    }

}
