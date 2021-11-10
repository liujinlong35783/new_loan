package com.tkcx.api.business.hjtemp.service;

import com.tkcx.api.business.hjtemp.dao.HjFileInfoDao;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.common.CommonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/2/7 16:07
 */
@Slf4j
@Service
public class HjFileInfoService extends CommonService<HjFileInfoDao, HjFileInfoModel> {

    @Resource
    private HjFileInfoDao hjFileInfoDao;

    public List<HjFileInfoModel> selectModelList(HjFileInfoModel queryInfo) {

        return hjFileInfoDao.selectModelList(queryInfo);
    }

    public HjFileInfoModel selectOneModel(HjFileInfoModel queryInfo){

        return hjFileInfoDao.selectOneModel(queryInfo);
    }

    public int insert(HjFileInfoModel entity) {
        return hjFileInfoDao.insert(entity);
    }

    public int updateDownloadFile(HjFileInfoModel entity){

        return hjFileInfoDao.updateDownloadFile(entity);
    }
}
