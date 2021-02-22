package com.tkcx.api;

import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @project： 改造互金数据通知改造
 * @author： zhaodan
 * @description：
 * @create： 2021/2/22 16:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HjFileInfoServiceTest {

    @Autowired
    private HjFileInfoService hjFileInfoService;

    @Test
    public void testSaveOrUpdate(){

        HjFileInfoModel update = new HjFileInfoModel();
        update.setFileDate(new Date());
        update.setDeleteFlag("1");
        hjFileInfoService.saveOrUpdate(update);
    }


}
