package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.AcctDataDao;
import com.tkcx.api.business.wdData.model.AcctDataModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-08 16:33
 */
@Service
public class AcctDataService extends CommonService<AcctDataDao,AcctDataModel> {

    @Resource
    private AcctDataDao acctDataDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctDataModel entity) {
        return acctDataDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctDataDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctDataModel entity) {
        return this.updateById(entity);
    }

    public AcctDataModel selectById(Integer id) {
        return acctDataDao.selectById(id);
    }

    public AcctDataModel selectOne(AcctDataModel entity) {
        QueryWrapper<AcctDataModel> queryWrapper = new QueryWrapper<>(entity);
        return acctDataDao.selectOne(queryWrapper);
    }

    public Integer selectCount(AcctDataModel model) {
        return acctDataDao.selectModelCount(model);
    }

    public List<AcctDataModel> selectList(AcctDataModel model) {
        return acctDataDao.selectModelList(model);
    }

}
