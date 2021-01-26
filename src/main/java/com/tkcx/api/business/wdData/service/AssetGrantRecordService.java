package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.AssetGrantRecordDao;
import com.tkcx.api.business.wdData.model.AssetGrantRecordModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-08 16:35
 */
@Service
public class AssetGrantRecordService extends CommonService<AssetGrantRecordDao,AssetGrantRecordModel> {

    @Resource
    private AssetGrantRecordDao assetGrantRecordDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AssetGrantRecordModel entity) {
        return assetGrantRecordDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return assetGrantRecordDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AssetGrantRecordModel entity) {
        return this.updateById(entity);
    }

    public AssetGrantRecordModel selectById(Integer id) {
        return assetGrantRecordDao.selectById(id);
    }

    public AssetGrantRecordModel selectOne(AssetGrantRecordModel entity) {
        QueryWrapper<AssetGrantRecordModel> queryWrapper = new QueryWrapper<>(entity);
        return assetGrantRecordDao.selectOne(queryWrapper);
    }

    public Integer selectCount(AssetGrantRecordModel model) {
        return assetGrantRecordDao.selectModelCount(model);
    }

    public List<AssetGrantRecordModel> selectList(AssetGrantRecordModel model) {
        return assetGrantRecordDao.selectModelList(model);
    }

}
