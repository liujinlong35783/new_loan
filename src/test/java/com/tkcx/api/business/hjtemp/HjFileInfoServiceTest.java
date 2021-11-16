package com.tkcx.api.business.hjtemp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @project： 改造互金数据通知改造
 * @author： zhaodan
 * @description：
 * @create： 2021/2/22 16:05
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class HjFileInfoServiceTest {

    @Autowired
    private HjFileInfoService hjFileInfoService;

    @Test
    public void testSaveOrUpdate(){

        HjFileInfoModel update = new HjFileInfoModel();
        update.setFileDate(new Date());
        update.setDeleteFlag("1");
        update.setFileType("testInsert");
        update.setFileName("testInsert");
        update.setFileTransCode("testInsert");
        hjFileInfoService.saveOrUpdate(update);
    }

    @Test
    public void testQuery() {

        // TODO 待测试
        // t_act_one_detail
        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setFileType("t_act_brch_day_tot");
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        queryCon.setReadFlag(HjFileFlagConstant.NOT_FINISH);
        queryCon.setFileDate(DateUtil.parseDate("2021-10-28"));
        List<HjFileInfoModel> hjFiles = hjFileInfoService.selectModelList(queryCon);
        log.info("互金文件信息查询结果：{}", hjFiles.size());
        if(hjFiles.size() == 0 || hjFiles == null){
            log.error("查询到已下载的互金文件信息异常");
        }
    }

    @Test
    public void testUpdate() {

        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setFileType("t_act_brch_day_tot");
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        queryCon.setFileDate(DateUtil.parseDate("2078-01-01"));
        queryCon.setReadFlag(HjFileFlagConstant.NOT_FINISH);

        queryCon.setFileLineTotalNum(1222);
        queryCon.setFileDownloadPath("test/test/test");
        log.info("更细结果：{}",hjFileInfoService.updateDownloadFile(queryCon));
    }


}
