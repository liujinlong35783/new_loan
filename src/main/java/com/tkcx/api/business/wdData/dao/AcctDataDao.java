package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkcx.api.business.wdData.model.AcctDataModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * AcctDataDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:33
 */
public interface AcctDataDao extends BaseMapper<AcctDataModel>{

    /**
     * 统计数量
     * @param preDate
     * @param curDate
     * @return
     */
    @Select("select count(1) from QN_DB_BIZ.ACCT_DATA t WHERE t.CREATE_AT >= #{preDate} AND t.CREATE_AT < #{curDate} ")
    Integer selectModelCount(@Param("preDate") Date preDate, @Param("curDate") Date curDate);

    /**
     * 查询列表
     * @param
     * @return
     */
    @Select("select * from QN_DB_BIZ.ACCT_DATA t WHERE t.CREATE_AT >= #{startDate} AND t.CREATE_AT < #{endDate}")
    List<AcctDataModel> selectModelList(@Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Select("select MESSAGE from QN_DB_BIZ.ACCT_DATA_DETAIL where ACCT_DATA_ID =#{id} and ROWNUM = 1")
    String selectAcctDataDetailMessage(@Param("id")Integer id);


    @Select("SELECT * FROM QN_DB_BIZ.ACCT_DATA t WHERE t.CREATE_AT >= #{startDate} AND t.CREATE_AT < #{endDate}")
    IPage<AcctDataModel> selectListByPage(Page<AcctDataModel> page, @Param("startDate")Date startDate, @Param("endDate")Date endDate);

    @Select("select * from QN_DB_BIZ.ACCT_DATA  where TRANS_SEQ_NO =#{transSeqNo} and ACG_DT = #{acgDt}")
    AcctDataModel selectDateByDate(@Param("transSeqNo")String transSeqNo, @Param("acgDt")String acgDt);

}
