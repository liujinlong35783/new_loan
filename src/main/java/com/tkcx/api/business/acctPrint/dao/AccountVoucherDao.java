package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.AccountVoucherModel;
import com.tkcx.api.vo.query.AcctVoucherQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * AccountVoucherDao
 *
 * @author tianyi
 * @Date 2019-08-06 20:51
 */
public interface AccountVoucherDao extends BaseMapper<AccountVoucherModel> {

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")AccountVoucherModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<AccountVoucherModel> selectModelList(@Param("e")AccountVoucherModel model);

    /**
     * 根据条件查询
     * @param query
     * @return
     */
    List<AccountVoucherModel> selectListByQuery(@Param("e")AcctVoucherQuery query);

}
