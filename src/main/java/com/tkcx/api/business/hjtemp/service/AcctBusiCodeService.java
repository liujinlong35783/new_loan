package com.tkcx.api.business.hjtemp.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.hjtemp.dao.AcctBusiCodeDao;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;
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
 * @Date 2019-08-06 20:18
 */
@Service
public class AcctBusiCodeService extends CommonService<AcctBusiCodeDao,AcctBusiCodeModel> {

    @Resource
    private AcctBusiCodeDao acctBusiCodeDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctBusiCodeModel entity) {
        return acctBusiCodeDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctBusiCodeDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctBusiCodeModel entity) {
        return this.updateById(entity);
    }

    
    public AcctBusiCodeModel selectById(Integer id) {
        return acctBusiCodeDao.selectById(id);
    }

    
    public AcctBusiCodeModel selectOne(AcctBusiCodeModel entity) {
        QueryWrapper<AcctBusiCodeModel> queryWrapper = new QueryWrapper<>(entity);
        return acctBusiCodeDao.selectOne(queryWrapper);
    }

    
    public Integer selectCount(AcctBusiCodeModel model) {
        return acctBusiCodeDao.selectModelCount(model);
    }

    
    public List<AcctBusiCodeModel> selectList(AcctBusiCodeModel model) {
        return acctBusiCodeDao.selectModelList(model);
    }

    public AcctBusiCodeModel getModelByBusiCode(String busiCode, String balanceIdentifier){
        return acctBusiCodeDao.getModelByBusiCode(busiCode, balanceIdentifier);
    }
}
