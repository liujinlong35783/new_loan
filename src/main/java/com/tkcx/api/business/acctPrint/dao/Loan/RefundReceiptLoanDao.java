package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.RefundReceiptLoanModel;
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
public interface RefundReceiptLoanDao extends BaseMapper<RefundReceiptLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RefundReceiptLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RefundReceiptLoanModel> selectModelList(@Param("e")RefundReceiptLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<RefundReceiptLoanModel> selectListByQuery(@Param("e")RefundReceiptQuery query);

}
