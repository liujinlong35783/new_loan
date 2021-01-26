package com.tkcx.api.business.realTime.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.realTime.model.LoanReceiptSyncModel;
import com.tkcx.api.vo.query.LoanReceiptQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuijh
 * @since 2019/10/24 15:42
 */
public interface LoanReceiptSyncDao extends BaseMapper<LoanReceiptSyncModel> {

    /**
     * 根据条件实时查询
     * @param query
     * @return
     */
    List<LoanReceiptSyncModel> selectListByRealTimeQuery(@Param("e")LoanReceiptQuery query);
}
