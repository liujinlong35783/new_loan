package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.AssetIndividualModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AssetIndividualDao
 *
 * @author tianyi
 * @Date 2019-08-15 17:50
 */
public interface AssetIndividualDao extends BaseMapper<AssetIndividualModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AssetIndividualModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AssetIndividualModel> selectModelList(@Param("e")AssetIndividualModel model);

}
