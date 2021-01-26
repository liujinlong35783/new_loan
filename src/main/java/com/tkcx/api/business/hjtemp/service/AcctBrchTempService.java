package com.tkcx.api.business.hjtemp.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.hjtemp.dao.AcctBrchTempDao;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
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
public class AcctBrchTempService extends CommonService<AcctBrchTempDao,AcctBrchTempModel> {

    @Resource
    private AcctBrchTempDao acctBrchTempDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctBrchTempModel entity) {
        return acctBrchTempDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctBrchTempDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctBrchTempModel entity) {
        return this.updateById(entity);
    }

    public AcctBrchTempModel selectById(Integer id) {
        return acctBrchTempDao.selectById(id);
    }

    public AcctBrchTempModel selectOne(AcctBrchTempModel entity) {
        QueryWrapper<AcctBrchTempModel> queryWrapper = new QueryWrapper<>(entity);
        return acctBrchTempDao.selectOne(queryWrapper);
    }

    public Integer selectCount(AcctBrchTempModel model) {
        return acctBrchTempDao.selectModelCount(model);
    }

    public List<AcctBrchTempModel> selectList(AcctBrchTempModel model) {
        return acctBrchTempDao.selectModelList(model);
    }

}
