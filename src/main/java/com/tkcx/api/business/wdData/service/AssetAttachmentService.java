package com.tkcx.api.business.wdData.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tkcx.api.common.CommonService;
import com.tkcx.api.business.wdData.dao.AssetAttachmentDao;
import com.tkcx.api.business.wdData.model.AssetAttachmentModel;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
/**
 * Service
 *
 * @author tianyi
 * @Date 2019-08-27 19:55
 */
@Service
public class AssetAttachmentService extends CommonService<AssetAttachmentDao,AssetAttachmentModel> {

    @Resource
    private AssetAttachmentDao assetAttachmentDao;

    @Transactional(rollbackFor = Exception.class)
    public Integer insert(AssetAttachmentModel entity) {
        return assetAttachmentDao.insert(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteById(Integer id) {
        return assetAttachmentDao.deleteById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean updateById(AssetAttachmentModel entity) {
        return this.updateById(entity);
    }

    @Transactional(rollbackFor = Exception.class)
    public AssetAttachmentModel selectById(Integer id) {
        return assetAttachmentDao.selectById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public AssetAttachmentModel selectOne(AssetAttachmentModel entity) {
        QueryWrapper<AssetAttachmentModel> queryWrapper = new QueryWrapper<>(entity);
        return assetAttachmentDao.selectOne(queryWrapper );
    }

    public Integer selectCount(AssetAttachmentModel model) {
        return assetAttachmentDao.selectModelCount(model);
    }

    public List<AssetAttachmentModel> selectList(AssetAttachmentModel model) {
        return assetAttachmentDao.selectModelList(model);
    }

}
