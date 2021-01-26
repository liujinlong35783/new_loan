package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.InterestBillModel;
import com.tkcx.api.vo.query.InterestBillQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * InterestBillDao
 *
 * @author tianyi
 * @Date 2019-08-06 21:00
 */
public interface InterestBillDao extends BaseMapper<InterestBillModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")InterestBillModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<InterestBillModel> selectModelList(@Param("e")InterestBillModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<InterestBillModel> selectListByQuery(@Param("e")InterestBillQuery query);

}
