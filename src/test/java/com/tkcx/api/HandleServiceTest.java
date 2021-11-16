package com.tkcx.api;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.handle.HandleService;
import com.tkcx.api.business.hjtemp.model.AcctDetailTempModel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaodan
 * @project
 * @description
 * @create 2020/12/29 12:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class HandleServiceTest {

    @Autowired
    private HandleService handleService;

    @Test
    public void testMakeListData(){

        String detailPath = "C:\\Users\\ccjh\\Desktop\\h9s8x0rha17i86ulufitzrtoxigizproo82df0ik.txt";
        handleService.makeListData(detailPath, AcctDetailTempModel.class);
    }

    @Test
    public void test() {
        String detailType = "t_act_one_detail";
        handleService.startHandle(detailType, DateUtil.parseDate("2078-01-01 00:00:00"));
    }
}
