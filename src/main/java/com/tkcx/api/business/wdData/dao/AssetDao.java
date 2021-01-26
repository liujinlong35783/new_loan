package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.AssetModel;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * AssetDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:35
 */
public interface AssetDao extends BaseMapper<AssetModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AssetModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AssetModel> selectModelList(@Param("e")AssetModel model);

    /**
     * 根据资产编号查询实体
     * @param assetItemNo
     * @return AssetModel
     */
    @Select("select * from QN_DB_BIZ.ASSET t where t.ASSET_ITEM_NO = #{assetItemNo}")
    AssetModel getAssetByItemNo(String assetItemNo);

}
