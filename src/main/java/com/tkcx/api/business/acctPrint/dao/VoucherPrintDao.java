package com.tkcx.api.business.acctPrint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.acctPrint.model.VoucherPrintModel;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * VoucherPrintDao
 *
 * @author tianyi
 * @Date 2019-10-31 11:50
 */
public interface VoucherPrintDao extends BaseMapper<VoucherPrintModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")VoucherPrintModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<VoucherPrintModel> selectModelList(@Param("e")VoucherPrintModel model);
    /**
     * 根据流水号和业务类型查询
     * @param busiType
     * @param serialNo
     * @return
     */
    @Select("select * from QN_DB_ACCT.VOUCHER_PRINT t where t.BUSI_TYPE = #{busiType} and t.SERIAL_NO = #{serialNo}")
    VoucherPrintModel getVoucherPrintByBusiTypeAndSerialNo(@Param("busiType")Integer busiType, @Param("serialNo")String serialNo);

    /**
     * 更新打印次数
     * @param queryNo
     * @return
     */
    @Update("UPDATE QN_DB_ACCT.VOUCHER_PRINT t set t.PRINT_COUNT = t.PRINT_COUNT+1, t.UPDATE_DATE = SYSDATE  WHERE t.QUERY_NO = #{queryNo}")
    Integer updatePrintCountByQueryNo(@Param("queryNo")String queryNo);
}
