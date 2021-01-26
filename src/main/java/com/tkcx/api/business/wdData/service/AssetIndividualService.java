package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.AssetIndividualDao;
import com.tkcx.api.business.wdData.model.AssetIndividualModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-15 17:50
 */
@Service
public class AssetIndividualService extends CommonService<AssetIndividualDao,AssetIndividualModel> {

    @Resource
    private AssetIndividualDao assetIndividualDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AssetIndividualModel entity) {
        return assetIndividualDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return assetIndividualDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AssetIndividualModel entity) {
        return this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public AssetIndividualModel selectById(Integer id) {
        return assetIndividualDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public AssetIndividualModel selectOne(AssetIndividualModel entity) {
        QueryWrapper<AssetIndividualModel> queryWrapper = new QueryWrapper<>(entity);
        return assetIndividualDao.selectOne(queryWrapper );
    }

    public Integer selectCount(AssetIndividualModel model) {
        return assetIndividualDao.selectModelCount(model);
    }

    public List<AssetIndividualModel> selectList(AssetIndividualModel model) {
        return assetIndividualDao.selectModelList(model);
    }

}
