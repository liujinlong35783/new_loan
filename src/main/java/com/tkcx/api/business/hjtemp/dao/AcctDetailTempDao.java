package com.tkcx.api.business.hjtemp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import com.tkcx.api.business.hjtemp.model.vo.BusiOrgBillVo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * AcctDetailTempDao
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Repository
public interface AcctDetailTempDao extends BaseMapper<AcctDetailTempModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AcctDetailTempModel model);

    Integer selectCountNotInEleAccount(@Param("e")AcctDetailTempModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AcctDetailTempModel> selectModelList(@Param("e")AcctDetailTempModel model);
    /**
     * 根据交易流水查科目
     * @param channelSeq
     * @param debtFlag
     * @return
     */
    @Select("select * from QN_DB_ACCT.ACCT_DETAIL_TEMP t where t.CHANNEL_SEQ = #{channelSeq} and t.DEBT_FLAG = #{debtFlag}")
    AcctDetailTempModel getDetailBySeq(@Param("channelSeq")String channelSeq, @Param("debtFlag")String debtFlag);

    /**
     * 查询统计轧帐单
     * @param acctDate
     * @return
     */
    @Select("SELECT (SELECT aot.ORG_NAME FROM QN_DB_LOAN.ACCT_ORG_TEMP aot WHERE aot.ORG_CODE = adt.ACCT_ORG) orgName," +
            "adt.ACCT_ORG acctOrg,adt.ITEM_CTRL itemCtrl,adt.CURRENCY currency,adt.DEBT_FLAG debtFlag," +
            "adt.OFF_BALANCE_FLAG offBalanceFlag,adt.ACCT_TYPE acctType," +
            "SUM(CASE WHEN adt.ACCT_TYPE = '2' THEN (adt.TRANS_AMOUNT * -1) ELSE adt.TRANS_AMOUNT END) transAmount," +
            "COUNT(adt.DETAIL_ID) transNum FROM QN_DB_LOAN.ACCT_DETAIL_TEMP adt " +
            "where adt.ACCT_DATE = #{acctDate}  AND adt.STATUS IN('02','05') " +
            "GROUP BY adt.ACCT_ORG,adt.ITEM_CTRL,adt.CURRENCY,adt.DEBT_FLAG,adt.OFF_BALANCE_FLAG,adt.ACCT_TYPE " +
            "ORDER BY adt.ACCT_ORG,adt.ITEM_CTRL,adt.DEBT_FLAG,adt.OFF_BALANCE_FLAG")
    List<BusiOrgBillVo> findStatAllBusiOrgBillVo(@Param("acctDate")Date acctDate);


    /**
     * 根据交易流水查科目
     * @param channelSeq
     * @param debtFlag
     * @return
     */
    @Select("select * from QN_DB_LOAN.ACCT_DETAIL_TEMP t where t.CHANNEL_SEQ = #{channelSeq}")
    AcctDetailTempModel getDetailBySeqNo(@Param("channelSeq")String channelSeq);

    /**
     * 根据会计日期查科目
     * @return
     */
    @Delete("DELETE from QN_DB_LOAN.ACCT_DETAIL_TEMP t where  t.ACCT_DATE <= #{acctDate}")
    Boolean delAcctDetailTempData(@Param("acctDate")Date acctDate);
}
