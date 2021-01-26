package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.LoanDetailBillModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LoanDetailBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanDetailBillDao extends BaseMapper<LoanDetailBillModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanDetailBillModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanDetailBillModel> selectModelList(@Param("e")LoanDetailBillModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanDetailBillModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

}
