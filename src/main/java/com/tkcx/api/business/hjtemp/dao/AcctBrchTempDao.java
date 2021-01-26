package com.tkcx.api.business.hjtemp.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.hjtemp.model.AcctBrchTempModel;

import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AcctBrchTempDao
 *
 * @author tianyi
 * @Date 2019-08-06 20:18
 */
@Repository
public interface AcctBrchTempDao extends BaseMapper<AcctBrchTempModel> {

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AcctBrchTempModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AcctBrchTempModel> selectModelList(@Param("e")AcctBrchTempModel model);

}
