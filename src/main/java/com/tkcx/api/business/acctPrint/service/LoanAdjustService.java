package com.tkcx.api.business.acctPrint.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.acctPrint.dao.LoanAdjustDao;
import com.tkcx.api.business.acctPrint.model.LoanAdjustModel;

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
public class LoanAdjustService extends CommonService<LoanAdjustDao,LoanAdjustModel> {

    @Resource
    private LoanAdjustDao loanAdjustDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(LoanAdjustModel entity) {
        return loanAdjustDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return loanAdjustDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(LoanAdjustModel entity) {
        return this.updateById(entity);
    }

    
    public LoanAdjustModel selectById(Integer id) {
        return loanAdjustDao.selectById(id);
    }

    
    public LoanAdjustModel selectOne(LoanAdjustModel entity) {
        QueryWrapper<LoanAdjustModel> queryWrapper = new QueryWrapper<>(entity);
        return loanAdjustDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(LoanAdjustModel model) {
        return loanAdjustDao.selectModelCount(model);
    }

    
    public List<LoanAdjustModel> selectList(LoanAdjustModel model) {
        return loanAdjustDao.selectModelList(model);
    }

}
