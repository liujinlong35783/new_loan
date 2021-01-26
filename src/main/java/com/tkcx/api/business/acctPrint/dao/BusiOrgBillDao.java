package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.vo.query.BusiOrgQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BusiOrgBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface BusiOrgBillDao extends BaseMapper<BusiOrgBillModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")BusiOrgBillModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<BusiOrgBillModel> selectModelList(@Param("e")BusiOrgBillModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<BusiOrgBillModel> selectListByQuery(@Param("e")BusiOrgQuery query);

}
