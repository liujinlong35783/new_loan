package com.tkcx.api.business.acctPrint.service.Loan;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.business.acctPrint.dao.BusiOrgSeqDao;
import com.tkcx.api.business.acctPrint.dao.Loan.BusiOrgSeqLoanDao;
import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgSeqLoanModel;
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
public class BusiOrgSeqLoanService extends CommonService<BusiOrgSeqLoanDao, BusiOrgSeqLoanModel> {

    @Resource
    private BusiOrgSeqLoanDao busiOrgSeqLoanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(BusiOrgSeqLoanModel entity) {
        return busiOrgSeqLoanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return busiOrgSeqLoanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(BusiOrgSeqLoanModel entity) {
        return this.updateById(entity);
    }

    
    public BusiOrgSeqLoanModel selectById(Integer id) {
        return busiOrgSeqLoanDao.selectById(id);
    }

    
    public BusiOrgSeqLoanModel selectOne(BusiOrgSeqLoanModel entity) {
        QueryWrapper<BusiOrgSeqLoanModel> queryWrapper = new QueryWrapper<>(entity);
        return busiOrgSeqLoanDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(BusiOrgSeqLoanModel model) {
        return busiOrgSeqLoanDao.selectModelCount(model);
    }

    
    public List<BusiOrgSeqLoanModel> selectList(BusiOrgSeqLoanModel model) {
        return busiOrgSeqLoanDao.selectModelList(model);
    }

}
