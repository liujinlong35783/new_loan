package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.AssetGrantRecordModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AssetGrantRecordDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:34
 */
public interface AssetGrantRecordDao extends BaseMapper<AssetGrantRecordModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AssetGrantRecordModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AssetGrantRecordModel> selectModelList(@Param("e")AssetGrantRecordModel model);

}
