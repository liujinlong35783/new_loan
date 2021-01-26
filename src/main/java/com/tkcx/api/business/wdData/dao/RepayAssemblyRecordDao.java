package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.RepayAssemblyRecordModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RepayAssemblyRecordDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:26
 */
public interface RepayAssemblyRecordDao extends BaseMapper<RepayAssemblyRecordModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RepayAssemblyRecordModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RepayAssemblyRecordModel> selectModelList(@Param("e")RepayAssemblyRecordModel model);

}
