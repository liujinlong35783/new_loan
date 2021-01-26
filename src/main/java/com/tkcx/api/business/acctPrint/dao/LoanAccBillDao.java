package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.LoanAccBillModel;
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
public interface LoanAccBillDao extends BaseMapper<LoanAccBillModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")LoanAccBillModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<LoanAccBillModel> selectModelList(@Param("e")LoanAccBillModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<LoanAccBillModel> selectListByQuery(@Param("e")LoanBillAndAdjustQuery query);

    /**
     * 根据贷款账号查询实体
     * @param loanAccount
     * @return
     */
    @Select("select * from QN_DB_ACCT.LOAN_ACC_BILL t where t.LOAN_ACCOUNT = #{loanAccount} and t.PRINCIPAL_STATUS = #{principalStatus}")
    LoanAccBillModel getModelByLoanAccount(@Param("loanAccount")String loanAccount, @Param("principalStatus")Integer principalStatus);
}
