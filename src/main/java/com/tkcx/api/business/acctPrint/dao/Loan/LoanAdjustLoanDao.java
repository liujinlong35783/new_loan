package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAdjustLoanModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LoanAdjustDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanAdjustLoanDao extends BaseMapper<LoanAdjustLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanAdjustLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanAdjustLoanModel> selectModelList(@Param("e")LoanAdjustLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanAdjustLoanModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

}
