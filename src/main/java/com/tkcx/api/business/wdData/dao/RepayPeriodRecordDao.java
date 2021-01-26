package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.RepayPeriodRecordModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RepayPeriodRecordDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:37
 */
public interface RepayPeriodRecordDao extends BaseMapper<RepayPeriodRecordModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RepayPeriodRecordModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RepayPeriodRecordModel> selectModelList(@Param("e")RepayPeriodRecordModel model);

}
