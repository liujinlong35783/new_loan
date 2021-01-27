package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.AcctSettleModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/1/27 11:25
 */
public interface AcctSettleDao extends BaseMapper<AcctSettleModel> {

    /**
     * 查询
     * @return
     */
    Date selectModelInfo();

    /**
     * 更新日期
     * @param sysDate
     * @param status
     * @return
     */
    @Update("UPDATE QN_DB_ACCT.ACCT_SETTLE t set t.SYS_DATE =#{sysDate} , t.STATUS =#{status} ")
    Boolean updateDateInfo(@Param("sysDate")Date sysDate, @Param("status")String status);
}
