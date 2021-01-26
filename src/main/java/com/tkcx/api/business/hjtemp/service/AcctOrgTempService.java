package com.tkcx.api.business.hjtemp.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.hjtemp.dao.AcctOrgTempDao;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-23 14:49
 */
@Service
public class AcctOrgTempService extends CommonService<AcctOrgTempDao,AcctOrgTempModel> {

    @Resource
    private AcctOrgTempDao acctOrgTempDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AcctOrgTempModel entity) {
        return acctOrgTempDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return acctOrgTempDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AcctOrgTempModel entity) {
        return this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public AcctOrgTempModel selectById(Integer id) {
        return acctOrgTempDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public AcctOrgTempModel selectOne(AcctOrgTempModel entity) {
        QueryWrapper<AcctOrgTempModel> queryWrapper = new QueryWrapper<>(entity);
        return acctOrgTempDao.selectOne(queryWrapper );
    }

    public Integer selectCount(AcctOrgTempModel model) {
        return acctOrgTempDao.selectModelCount(model);
    }

    public List<AcctOrgTempModel> selectList(AcctOrgTempModel model) {
        return acctOrgTempDao.selectModelList(model);
    }

}
