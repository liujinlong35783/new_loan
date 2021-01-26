package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.AssetAttachmentModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AssetAttachmentDao
 *
 * @author tianyi
 * @Date 2019-08-27 19:54
 */
public interface AssetAttachmentDao extends BaseMapper<AssetAttachmentModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AssetAttachmentModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AssetAttachmentModel> selectModelList(@Param("e")AssetAttachmentModel model);

}
