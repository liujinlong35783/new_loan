package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * LoanAccBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanAccBillLoanDao extends BaseMapper<LoanAccBillLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanAccBillLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanAccBillLoanModel> selectModelList(@Param("e")LoanAccBillLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanAccBillLoanModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

    /**
     * 根据贷款账号查询实体
     * @param loanAccount
     * @return
     */
    @Select("select * from QN_DB_ACCT.LOAN_ACC_BILL t where t.LOAN_ACCOUNT = #{loanAccount} and t.PRINCIPAL_STATUS = #{principalStatus}")
    LoanAccBillLoanModel getModelByLoanAccount(@Param("loanAccount")String loanAccount, @Param("principalStatus")Integer principalStatus);

    /**
     * 根据流水号查询实体
     * @param debtNo
     * @return
     */
    @Select("select * from QN_DB_LOAN.LOAN_ACC_BILL t where t.DEBT_NO = #{debtNo} AND t.ITEM = #{item} ")
    LoanAccBillLoanModel selectByDebtNoItem(@Param("debtNo")String debtNo ,@Param("item")String item);

}
