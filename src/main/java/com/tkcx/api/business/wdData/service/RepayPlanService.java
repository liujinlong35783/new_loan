package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.RepayPlanDao;
import com.tkcx.api.business.wdData.model.RepayPlanModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-10 15:23
 */
@Service
public class RepayPlanService extends CommonService<RepayPlanDao,RepayPlanModel> {

    @Resource
    private RepayPlanDao repayPlanDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RepayPlanModel entity) {
        return repayPlanDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return repayPlanDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RepayPlanModel entity) {
        return this.updateById(entity);
    }

    public RepayPlanModel selectById(Integer id) {
        return repayPlanDao.selectById(id);
    }

    public RepayPlanModel selectOne(RepayPlanModel entity) {
        QueryWrapper<RepayPlanModel> queryWrapper = new QueryWrapper<>(entity);
        return repayPlanDao.selectOne(queryWrapper);
    }

    public Integer selectCount(RepayPlanModel model) {
        return repayPlanDao.selectModelCount(model);
    }

    public List<RepayPlanModel> selectList(RepayPlanModel model) {
        return repayPlanDao.selectModelList(model);
    }

}
