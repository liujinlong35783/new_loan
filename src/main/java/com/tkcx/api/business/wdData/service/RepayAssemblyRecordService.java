package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkcx.api.business.wdData.dao.RepayAssemblyRecordDao;
import com.tkcx.api.business.wdData.model.RepayAssemblyRecordModel;
import com.tkcx.api.common.CommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-08 16:26
 */
@Service
public class RepayAssemblyRecordService extends CommonService<RepayAssemblyRecordDao,RepayAssemblyRecordModel> {

    @Resource
    private RepayAssemblyRecordDao repayAssemblyRecordDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(RepayAssemblyRecordModel entity) {
        return repayAssemblyRecordDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return repayAssemblyRecordDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(RepayAssemblyRecordModel entity) {
        return this.updateById(entity);
    }

    public RepayAssemblyRecordModel selectById(Integer id) {
        return repayAssemblyRecordDao.selectById(id);
    }

    public RepayAssemblyRecordModel selectOne(RepayAssemblyRecordModel entity) {
        QueryWrapper<RepayAssemblyRecordModel> queryWrapper = new QueryWrapper<>(entity);
        return repayAssemblyRecordDao.selectOne(queryWrapper);
    }

    public Integer selectCount(RepayAssemblyRecordModel model) {
        return repayAssemblyRecordDao.selectModelCount(model);
    }

    public List<RepayAssemblyRecordModel> selectList(RepayAssemblyRecordModel model) {
        return repayAssemblyRecordDao.selectModelList(model);
    }


    public List<RepayAssemblyRecordModel> queryRepayAssRecByPage(Page<RepayAssemblyRecordModel> pageReq,
                                                                 Date startDate, Date endDate){

        Page<RepayAssemblyRecordModel> result = new Page<>(pageReq.getCurrent(), pageReq.getSize());
        IPage<RepayAssemblyRecordModel> acctDataModelIPage = repayAssemblyRecordDao.selectListByPage(result, startDate, endDate);
        return acctDataModelIPage.getRecords();
    }
}
