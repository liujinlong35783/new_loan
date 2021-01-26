package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.RepayPlanProgressDao;
import com.tkcx.api.business.wdData.model.RepayPlanProgressModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-10 15:22
 */
@Service
public class RepayPlanProgressService extends CommonService<RepayPlanProgressDao,RepayPlanProgressModel> {

    @Resource
    private RepayPlanProgressDao repayPlanProgressDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RepayPlanProgressModel entity) {
        return repayPlanProgressDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return repayPlanProgressDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RepayPlanProgressModel entity) {
        return this.updateById(entity);
    }

    public RepayPlanProgressModel selectById(Integer id) {
        return repayPlanProgressDao.selectById(id);
    }

    public RepayPlanProgressModel selectOne(RepayPlanProgressModel entity) {
        QueryWrapper<RepayPlanProgressModel> queryWrapper = new QueryWrapper<>(entity);
        return repayPlanProgressDao.selectOne(queryWrapper);
    }

    public Integer selectCount(RepayPlanProgressModel model) {
        return repayPlanProgressDao.selectModelCount(model);
    }

    public List<RepayPlanProgressModel> selectList(RepayPlanProgressModel model) {
        return repayPlanProgressDao.selectModelList(model);
    }

}
