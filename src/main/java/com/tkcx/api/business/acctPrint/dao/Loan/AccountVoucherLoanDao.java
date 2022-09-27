package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
import com.tkcx.api.business.acctPrint.model.Loan.AccountVoucherLoanModel;
import com.tkcx.api.vo.query.AcctVoucherQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AccountVoucherLoanDao
 *
 * @author tianyi
 * @Date 2019-08-06 20:51
 */
public interface AccountVoucherLoanDao extends BaseMapper<AccountVoucherLoanModel> {

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AccountVoucherLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AccountVoucherLoanModel> selectModelList(@Param("e")AccountVoucherLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<AccountVoucherLoanModel> selectListByQuery(@Param("e")AcctVoucherQuery query);

}
