package com.tkcx.api.business.hjtemp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.dao.HjFileInfoDao;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/1 9:18
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HjFileInfoDaoTest {

    @Resource
    private HjFileInfoDao hjFileInfoDao;

    @Test
    public void testQueryDao() {

        // t_act_one_detail
        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setFileType("t_act_one_detail");
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        queryCon.setReadFlag(HjFileFlagConstant.NOT_FINISH);
        queryCon.setFileDate(DateUtil.parseDate("2078-01-01"));
        List<HjFileInfoModel> hjFileInfoModels = hjFileInfoDao.selectModelList(queryCon);
        for (HjFileInfoModel hjFileInfoModel : hjFileInfoModels) {
            System.out.println(hjFileInfoModel.toString());
        }
    }


    @Test
    public void  testQueryCount() {
        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setFileType("t_act_one_detail");
        // NOT_DELETED
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        // NOT_FINISH
        queryCon.setReadFlag(HjFileFlagConstant.NOT_FINISH);
        queryCon.setFileDate(DateUtil.parseDate("2078-01-01"));
        int count = hjFileInfoDao.selectModelCount(queryCon);
        System.out.println("**********"+count);
    }
}
