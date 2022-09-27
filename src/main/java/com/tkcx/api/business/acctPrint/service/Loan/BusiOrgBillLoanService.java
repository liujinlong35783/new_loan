package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.BusiOrgBillDao;
import com.tkcx.api.business.acctPrint.dao.Loan.BusiOrgBillLoanDao;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgBillLoanModel;
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
public class BusiOrgBillLoanService extends CommonService<BusiOrgBillLoanDao, BusiOrgBillLoanModel> {

    @Resource
    private BusiOrgBillLoanDao busiOrgBillLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(BusiOrgBillLoanModel entity) {
        return busiOrgBillLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return busiOrgBillLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(BusiOrgBillLoanModel entity) {
        return this.updateById(entity);
    }

    
    public BusiOrgBillLoanModel selectById(Integer id) {
        return busiOrgBillLoanDao.selectById(id);
    }

    
    public BusiOrgBillLoanModel selectOne(BusiOrgBillLoanModel entity) {
        QueryWrapper<BusiOrgBillLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return busiOrgBillLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(BusiOrgBillLoanModel model) {
        return busiOrgBillLoanDao.selectModelCount(model);
    }

    
    public List<BusiOrgBillLoanModel> selectList(BusiOrgBillLoanModel model) {
        return busiOrgBillLoanDao.selectModelList(model);
    }

}
