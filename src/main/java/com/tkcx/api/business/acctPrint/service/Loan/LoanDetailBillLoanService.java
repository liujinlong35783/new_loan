package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.LoanDetailBillLoanDao;
import com.tkcx.api.business.acctPrint.dao.LoanDetailBillDao;
import com.tkcx.api.business.acctPrint.model.Loan.LoanDetailBillLoanModel;
import com.tkcx.api.business.acctPrint.model.LoanDetailBillModel;
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
public class LoanDetailBillLoanService extends CommonService<LoanDetailBillLoanDao, LoanDetailBillLoanModel> {

    @Resource
    private LoanDetailBillLoanDao loanDetailBillLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanDetailBillLoanModel entity) {
        return loanDetailBillLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanDetailBillLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanDetailBillLoanModel entity) {
        return this.updateById(entity);
    }

    
    public LoanDetailBillLoanModel selectById(Integer id) {
        return loanDetailBillLoanDao.selectById(id);
    }

    
    public LoanDetailBillLoanModel selectOne(LoanDetailBillLoanModel entity) {
        QueryWrapper<LoanDetailBillLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return loanDetailBillLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanDetailBillLoanModel model) {
        return loanDetailBillLoanDao.selectModelCount(model);
    }

    
    public List<LoanDetailBillLoanModel> selectList(LoanDetailBillLoanModel model) {
        return loanDetailBillLoanDao.selectModelList(model);
    }
}
