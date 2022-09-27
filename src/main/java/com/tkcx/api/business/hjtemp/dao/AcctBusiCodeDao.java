package com.tkcx.api.business.hjtemp.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.hjtemp.model.AcctBusiCodeModel;

import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AcctBusiCodeDao
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Repository
public interface AcctBusiCodeDao extends BaseMapper<AcctBusiCodeModel> {

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AcctBusiCodeModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AcctBusiCodeModel> selectModelList(@Param("e")AcctBusiCodeModel model);

    /**
     * 查询
     * @param busiCode
     * @param balanceIdentifier
     * @return
     */
    @Select("select * from QN_DB_LOAN.ACCT_BUSI_CODE b where b.BUSI_CODE = '${busiCode}' and b.BALANCE_IDENTIFIER = '${balanceIdentifier}'")
    AcctBusiCodeModel getModelByBusiCode(@Param("busiCode")String busiCode, @Param("balanceIdentifier")String balanceIdentifier);
 /**
     * 查询
     * @param busiCode
     * @param balanceIdentifier
     * @return
     */
    @Select("select * from QN_DB_LOAN.ACCT_BUSI_CODE b where b.ITEM_CTRL = '${itemCtrl}'")
    AcctBusiCodeModel getModelByItemCtrl(@Param("busiCode")String itemCtrl);
}
