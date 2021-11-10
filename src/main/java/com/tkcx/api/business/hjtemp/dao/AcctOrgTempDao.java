package com.tkcx.api.business.hjtemp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.hjtemp.model.AcctOrgTempModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AcctOrgTempDao
 *
 * @author tianyi
 * @Date 2019-08-23 14:49
 */
@Repository
public interface AcctOrgTempDao extends BaseMapper<AcctOrgTempModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AcctOrgTempModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AcctOrgTempModel> selectModelList(@Param("e")AcctOrgTempModel model);

}
