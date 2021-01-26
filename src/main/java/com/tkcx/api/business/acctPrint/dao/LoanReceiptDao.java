package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.LoanReceiptModel;
import com.tkcx.api.vo.query.LoanReceiptQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LoanReceiptDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanReceiptDao extends BaseMapper<LoanReceiptModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanReceiptModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanReceiptModel> selectModelList(@Param("e")LoanReceiptModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanReceiptModel> selectListByQuery(@Param("e")LoanReceiptQuery query);

}
