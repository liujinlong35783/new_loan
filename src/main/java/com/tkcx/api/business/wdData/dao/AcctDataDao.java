package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.AcctDataModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AcctDataDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:33
 */
public interface AcctDataDao extends BaseMapper<AcctDataModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AcctDataModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AcctDataModel> selectModelList(@Param("e")AcctDataModel model);

}
