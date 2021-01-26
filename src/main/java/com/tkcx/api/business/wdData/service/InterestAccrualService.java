package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.InterestAccrualDao;
import com.tkcx.api.business.wdData.model.InterestAccrualModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-30 16:53
 */
@Service
public class InterestAccrualService extends CommonService<InterestAccrualDao,InterestAccrualModel> {

    @Resource
    private InterestAccrualDao interestAccrualDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(InterestAccrualModel entity) {
        return interestAccrualDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return interestAccrualDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(InterestAccrualModel entity) {
        return this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public InterestAccrualModel selectById(Integer id) {
        return interestAccrualDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public InterestAccrualModel selectOne(InterestAccrualModel entity) {
        QueryWrapper<InterestAccrualModel> queryWrapper = new QueryWrapper<>(entity);
        return interestAccrualDao.selectOne(queryWrapper );
    }

    public Integer selectCount(InterestAccrualModel model) {
        return interestAccrualDao.selectModelCount(model);
    }

    public List<InterestAccrualModel> selectList(InterestAccrualModel model) {
        return interestAccrualDao.selectModelList(model);
    }

}
