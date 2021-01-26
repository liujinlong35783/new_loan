package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.BusiOrgSeqModel;
import com.tkcx.api.vo.query.BusiOrgQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BusiOrgSeqDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface BusiOrgSeqDao extends BaseMapper<BusiOrgSeqModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")BusiOrgSeqModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<BusiOrgSeqModel> selectModelList(@Param("e")BusiOrgSeqModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<BusiOrgSeqModel> selectListByQuery(@Param("e")BusiOrgQuery query);
}
