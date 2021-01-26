package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.RepayPeriodRecordDao;
import com.tkcx.api.business.wdData.model.RepayPeriodRecordModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-08 16:37
 */
@Service
public class RepayPeriodRecordService extends CommonService<RepayPeriodRecordDao,RepayPeriodRecordModel> {

    @Resource
    private RepayPeriodRecordDao repayPeriodRecordDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RepayPeriodRecordModel entity) {
        return repayPeriodRecordDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return repayPeriodRecordDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RepayPeriodRecordModel entity) {
        return this.updateById(entity);
    }

    public RepayPeriodRecordModel selectById(Integer id) {
        return repayPeriodRecordDao.selectById(id);
    }

    public RepayPeriodRecordModel selectOne(RepayPeriodRecordModel entity) {
        QueryWrapper<RepayPeriodRecordModel> queryWrapper = new QueryWrapper<>(entity);
        return repayPeriodRecordDao.selectOne(queryWrapper);
    }

    public Integer selectCount(RepayPeriodRecordModel model) {
        return repayPeriodRecordDao.selectModelCount(model);
    }

    public List<RepayPeriodRecordModel> selectList(RepayPeriodRecordModel model) {
        return repayPeriodRecordDao.selectModelList(model);
    }

}
