package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.LoanDetailBillLoanModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * LoanDetailBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanDetailBillLoanDao extends BaseMapper<LoanDetailBillLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanDetailBillLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanDetailBillLoanModel> selectModelList(@Param("e")LoanDetailBillLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanDetailBillLoanModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

}
