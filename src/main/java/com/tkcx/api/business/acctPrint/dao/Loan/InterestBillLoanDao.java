package com.tkcx.api.business.acctPrint.dao.Loan;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.Loan.InterestBillLoanModel;
import com.tkcx.api.vo.query.InterestBillQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InterestBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface InterestBillLoanDao extends BaseMapper<InterestBillLoanModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")InterestBillLoanModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<InterestBillLoanModel> selectModelList(@Param("e")InterestBillLoanModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<InterestBillLoanModel> selectListByQuery(@Param("e")InterestBillQuery query);

}
