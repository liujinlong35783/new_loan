package com.tkcx.api.business.wdData.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.wdData.model.CardiiModel;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * CardiiDao
 *
 * @author tianyi
 * @Date 2019-08-08 16:36
 */
public interface CardiiDao extends BaseMapper<CardiiModel>{

    /**
     * 统计数量
     * @param model
     * @return
     */
    Integer selectModelCount(@Param("e")CardiiModel model);

    /**
     * 查询列表
     * @param model
     * @return
     */
    List<CardiiModel> selectModelList(@Param("e")CardiiModel model);

    /**
     * 根据身份证号查询二类卡信息
     * @param cardiiIdnum 身份证号
     * @return AssetModel
     */
    @Select("select * from QN_DB_BIZ.CARDII t where t.CARDII_IDNUM = #{cardiiIdnum}")
    CardiiModel getCardiiByCardiiIdnum(String cardiiIdnum);
}
