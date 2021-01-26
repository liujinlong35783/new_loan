package com.tkcx.api.business.realTime.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.realTime.model.RefundReceiptSyncModel;
import com.tkcx.api.vo.query.RefundReceiptQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuijh
 * @since 2019/10/24 14:47
 */
public interface RefundReceiptSyncDao extends BaseMapper<RefundReceiptSyncModel> {

    /**
     * 根据条件实时查询
     * @param query
     * @return
     */
    List<RefundReceiptSyncModel> selectListByRealTimeQuery(@Param("e")RefundReceiptQuery query);
}
