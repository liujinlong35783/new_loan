package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.BusiOrgSeqLoanModel;
import com.tkcx.api.vo.query.BusiOrgQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * BusiOrgSeqLoanDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface BusiOrgSeqLoanDao extends BaseMapper<BusiOrgSeqLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")BusiOrgSeqLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<BusiOrgSeqLoanModel> selectModelList(@Param("e")BusiOrgSeqLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<BusiOrgSeqLoanModel> selectListByQuery(@Param("e")BusiOrgQuery query);
}
