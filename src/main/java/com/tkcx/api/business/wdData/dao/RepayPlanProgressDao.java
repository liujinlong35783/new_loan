package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.RepayPlanProgressModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RepayPlanProgressDao
 *
 * @author tianyi
 * @Date 2019-08-10 15:22
 */
public interface RepayPlanProgressDao extends BaseMapper<RepayPlanProgressModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RepayPlanProgressModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RepayPlanProgressModel> selectModelList(@Param("e")RepayPlanProgressModel model);

}
