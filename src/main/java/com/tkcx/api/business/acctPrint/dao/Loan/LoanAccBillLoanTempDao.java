package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanModel;
import com.tkcx.api.business.acctPrint.model.Loan.LoanAccBillLoanTempModel;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * LoanAccBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface LoanAccBillLoanTempDao extends BaseMapper<LoanAccBillLoanTempModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanAccBillLoanTempModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanAccBillLoanTempModel> selectModelList(@Param("e")LoanAccBillLoanTempModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanAccBillLoanTempModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

    /**
     * 根据贷款账号查询实体
     * @param loanAccount
     * @return
     */
    @Select("select * from QN_DB_ACCT.LOAN_ACC_BILL t where t.LOAN_ACCOUNT = #{loanAccount} and t.PRINCIPAL_STATUS = #{principalStatus}")
    LoanAccBillLoanTempModel getModelByLoanAccount(@Param("loanAccount")String loanAccount, @Param("principalStatus")Integer principalStatus);



    /**
     * 根据会计日期查科目
     * @return
     */
    @Select("select * from QN_DB_LOAN.LOAN_ACC_BILL_TEMP t where t.ACCT_DATE = #{acctDate}")
    List<LoanAccBillLoanTempModel> queryAccBillByAcctDate(@Param("acctDate") Date acctDate);

}
