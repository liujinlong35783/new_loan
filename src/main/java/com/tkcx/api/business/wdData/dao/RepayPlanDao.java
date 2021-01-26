package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.RepayPlanModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RepayPlanDao
 *
 * @author tianyi
 * @Date 2019-08-10 15:23
 */
public interface RepayPlanDao extends BaseMapper<RepayPlanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RepayPlanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RepayPlanModel> selectModelList(@Param("e")RepayPlanModel model);

}
