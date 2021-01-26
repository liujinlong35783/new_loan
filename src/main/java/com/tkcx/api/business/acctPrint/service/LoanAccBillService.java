package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.LoanAccBillDao;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;

import javafx.scene.control.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class LoanAccBillService extends CommonService<LoanAccBillDao,LoanAccBillModel> {

    @Resource
    private LoanAccBillDao loanAccBillDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanAccBillModel entity) {
        return loanAccBillDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanAccBillDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanAccBillModel entity) {
        return this.updateById(entity);
    }

    
    public LoanAccBillModel selectById(Integer id) {
        return loanAccBillDao.selectById(id);
    }

    
    public LoanAccBillModel selectOne(LoanAccBillModel entity) {
        QueryWrapper<LoanAccBillModel> queryWrapper = new QueryWrapper<>(entity);
        return loanAccBillDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanAccBillModel model) {
        return loanAccBillDao.selectModelCount(model);
    }

    
    public List<LoanAccBillModel> selectList(LoanAccBillModel model) {
        return loanAccBillDao.selectModelList(model);
    }

    public LoanAccBillModel getModelByLoanAccount(String loanAccount, Integer principalStatus){
        if(StringUtils.isEmpty(loanAccount))return null;
        return loanAccBillDao.getModelByLoanAccount(loanAccount, principalStatus);
    }
}
