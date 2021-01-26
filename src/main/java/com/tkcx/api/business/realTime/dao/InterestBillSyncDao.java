package com.tkcx.api.business.realTime.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.realTime.model.InterestBillSyncModel;
import com.tkcx.api.vo.query.InterestBillQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuijh
 * @since 2019/10/25 15:15
 */
public interface InterestBillSyncDao extends BaseMapper<InterestBillSyncModel> {

    /**
     * 根据条件实时查询
     * @param query
     * @return
     */
    List<InterestBillSyncModel> selectListByRealTimeQuery(@Param("e")InterestBillQuery query);
}
