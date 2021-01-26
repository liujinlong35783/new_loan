package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.InterestAccrualModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InterestAccrualDao
 *
 * @author tianyi
 * @Date 2019-08-30 16:53
 */
public interface InterestAccrualDao extends BaseMapper<InterestAccrualModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")InterestAccrualModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<InterestAccrualModel> selectModelList(@Param("e")InterestAccrualModel model);

}
