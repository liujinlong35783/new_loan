package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.Loan.LoanAdjustLoanDao;
import com.tkcx.api.business.acctPrint.dao.LoanAdjustDao;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAdjustLoanModel;
import com.tkcx.api.business.acctPrint.model.LoanAdjustModel;
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
public class LoanAdjustLoanService extends CommonService<LoanAdjustLoanDao, LoanAdjustLoanModel> {

    @Resource
    private LoanAdjustLoanDao loanAdjustLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanAdjustLoanModel entity) {
        return loanAdjustLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanAdjustLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanAdjustLoanModel entity) {
        return this.updateById(entity);
    }

    
    public LoanAdjustLoanModel selectById(Integer id) {
        return loanAdjustLoanDao.selectById(id);
    }

    
    public LoanAdjustLoanModel selectOne(LoanAdjustLoanModel entity) {
        QueryWrapper<LoanAdjustLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return loanAdjustLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanAdjustLoanModel model) {
        return loanAdjustLoanDao.selectModelCount(model);
    }

    
    public List<LoanAdjustLoanModel> selectList(LoanAdjustLoanModel model) {
        return loanAdjustLoanDao.selectModelList(model);
    }

}
