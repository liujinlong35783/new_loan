package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.AssetDao;
import com.tkcx.api.business.wdData.model.AssetModel;

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
public class AssetService extends CommonService<AssetDao,AssetModel> {

    @Resource
    private AssetDao assetDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AssetModel entity) {
        return assetDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return assetDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AssetModel entity) {
        return this.updateById(entity);
    }

    public AssetModel selectById(Integer id) {
        return assetDao.selectById(id);
    }

    public AssetModel selectOne(AssetModel entity) {
        QueryWrapper<AssetModel> queryWrapper = new QueryWrapper<>(entity);
        return assetDao.selectOne(queryWrapper);
    }

    public Integer selectCount(AssetModel model) {
        return assetDao.selectModelCount(model);
    }

    public List<AssetModel> selectList(AssetModel model) {
        return assetDao.selectModelList(model);
    }

    public AssetModel getAssetByItemNo(String assetItemNo) {
        return assetDao.getAssetByItemNo(assetItemNo);
    }
}
