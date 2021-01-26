package com.tkcx.api.business.wdData.dao;

import org.apache.ibatis.annotations.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * AcctDataDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:33
 */
public interface CommonMapper{

    /**
     * 根据sql查询map类型集合
     * @param sqlstr
     * @return
     */
    @ResultType(value = Map.class)
    @Select("${sqlstr}")
    List<Map<String, Object>> selectListBySql(@Param("sqlstr") String sqlstr);

    /**
     * 根据sql单独字段信息
     * @param sqlstr
     * @return
     */
    @Select("${sqlstr}")
    Object getBySql(@Param("sqlstr") String sqlstr);

    /**
     * 根据sql map类型
     * @param sqlstr
     * @return map
     */
    @ResultType(value = Map.class)
    @Select("${sqlstr}")
    Map<String, Object> getMapBySql(@Param("sqlstr") String sqlstr);
}
