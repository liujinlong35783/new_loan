package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tkcx.api.business.wdData.model.RepayAssemblyRecordModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.List;

/**
 * RepayAssemblyRecordDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:26
 */
public interface RepayAssemblyRecordDao extends BaseMapper<RepayAssemblyRecordModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")RepayAssemblyRecordModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<RepayAssemblyRecordModel> selectModelList(@Param("e")RepayAssemblyRecordModel model);


    @Select("SELECT * FROM QN_DB_BIZ.REPAY_ASSEMBLY_RECORD  WHERE t.REPAY_FINISH_TIME >= #{startDate} AND t.REPAY_FINISH_TIME < #{endDate}")
    IPage<RepayAssemblyRecordModel> selectListByPage(Page<RepayAssemblyRecordModel> result, @Param("startDate")Date startDate, @Param("endDate")Date endDate);
}
