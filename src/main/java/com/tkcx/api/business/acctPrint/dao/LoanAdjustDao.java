package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.LoanAdjustModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LoanAdjustDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanAdjustDao extends BaseMapper<LoanAdjustModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanAdjustModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanAdjustModel> selectModelList(@Param("e")LoanAdjustModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanAdjustModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

}
