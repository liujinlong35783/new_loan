package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.RefundReceiptModel;
import com.tkcx.api.vo.query.RefundReceiptQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * RefundReceiptDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface RefundReceiptDao extends BaseMapper<RefundReceiptModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RefundReceiptModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RefundReceiptModel> selectModelList(@Param("e")RefundReceiptModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<RefundReceiptModel> selectListByQuery(@Param("e")RefundReceiptQuery query);

}
