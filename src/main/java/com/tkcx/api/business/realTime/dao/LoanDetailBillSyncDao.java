package com.tkcx.api.business.realTime.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.realTime.model.LoanDetailBillSyncModel;
import com.tkcx.api.vo.query.LoanBillAndAdjustQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author cuijh
 * @since 2019/10/24 17:28
 */
public interface LoanDetailBillSyncDao extends BaseMapper<LoanDetailBillSyncModel> {

    /**
     * 根据条件实时查询
     * @param query
     * @return
     */
    List<LoanDetailBillSyncModel> selectListByRealTimeQuery(@Param("e")LoanBillAndAdjustQuery query);
}
