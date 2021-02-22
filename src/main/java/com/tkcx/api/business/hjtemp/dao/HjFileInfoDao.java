package com.tkcx.api.business.hjtemp.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/2/7 16:08
 */
public interface HjFileInfoDao extends BaseMapper<HjFileInfoModel> {

    List<HjFileInfoModel> selectModelList(@Param("e") HjFileInfoModel queryInfo);
}
