package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.BusiOrgBillModel;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgBillLoanModel;
import com.tkcx.api.vo.query.BusiOrgQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BusiOrgBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface BusiOrgBillLoanDao extends BaseMapper<BusiOrgBillLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")BusiOrgBillLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<BusiOrgBillLoanModel> selectModelList(@Param("e")BusiOrgBillLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<BusiOrgBillLoanModel> selectListByQuery(@Param("e")BusiOrgQuery query);

}
