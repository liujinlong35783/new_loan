package com.tkcx.api;

import com.tkcx.api.business.hjtemp.Handle.HandleService;
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
}
