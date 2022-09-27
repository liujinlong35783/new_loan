package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.InterestBillDao;
import com.tkcx.api.business.acctPrint.dao.Loan.InterestBillLoanDao;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.business.acctPrint.model.Loan.InterestBillLoanModel;
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
public class InterestBillLoanService extends CommonService<InterestBillLoanDao, InterestBillLoanModel> {

    @Resource
    private InterestBillLoanDao interestBillLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(InterestBillLoanModel entity) {
        return interestBillLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return interestBillLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(InterestBillLoanModel entity) {
        return this.updateById(entity);
    }

    
    public InterestBillLoanModel selectById(Integer id) {
        return interestBillLoanDao.selectById(id);
    }

    
    public InterestBillLoanModel selectOne(InterestBillLoanModel entity) {
        QueryWrapper<InterestBillLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return interestBillLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(InterestBillLoanModel model) {
        return interestBillLoanDao.selectModelCount(model);
    }

    
    public List<InterestBillLoanModel> selectList(InterestBillLoanModel model) {
        return interestBillLoanDao.selectModelList(model);
    }

}
