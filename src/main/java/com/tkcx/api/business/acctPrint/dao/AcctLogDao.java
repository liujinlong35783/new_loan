package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.AcctLogModel;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AcctLogDao
 *
 * @author cuijh
 * @Date 2019-10-12 15:20
 */
public interface AcctLogDao extends BaseMapper<AcctLogModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AcctLogModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AcctLogModel> selectModelList(@Param("e")AcctLogModel model);

}
