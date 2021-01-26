package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.LoanReceiptDao;
import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;

import javafx.scene.control.Pagination;
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
public class LoanReceiptService extends CommonService<LoanReceiptDao,LoanReceiptModel> {

    @Resource
    private LoanReceiptDao loanReceiptDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanReceiptModel entity) {
        return loanReceiptDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanReceiptDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanReceiptModel entity) {
        return this.updateById(entity);
    }

    
    public LoanReceiptModel selectById(Integer id) {
        return loanReceiptDao.selectById(id);
    }

    
    public LoanReceiptModel selectOne(LoanReceiptModel entity) {
        QueryWrapper<LoanReceiptModel> queryWrapper = new QueryWrapper<>(entity);
        return loanReceiptDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanReceiptModel model) {
        return loanReceiptDao.selectModelCount(model);
    }

    
    public List<LoanReceiptModel> selectList(LoanReceiptModel model) {
        return loanReceiptDao.selectModelList(model);
    }

}
