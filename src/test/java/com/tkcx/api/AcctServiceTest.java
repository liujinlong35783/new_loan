package com.tkcx.api;

import com.tkcx.api.business.acctPrint.model.AcctLogModel;
import com.tkcx.api.business.acctPrint.service.AcctLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/10/31 14:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AcctServiceTest {

    @Autowired
    private AcctLogService acctLogService;

    @Test
    public void  testSelectList(){

        acctLogService.selectList(new AcctLogModel());
    }
}
