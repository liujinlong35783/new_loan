package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.LoanReceiptLoanModel;
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
public interface LoanReceiptLoanDao extends BaseMapper<LoanReceiptLoanModel>{

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanReceiptLoanModel> selectListByDate(@Param("e")LoanReceiptLoanModel model);

}
