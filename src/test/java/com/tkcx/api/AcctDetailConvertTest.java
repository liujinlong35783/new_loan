package com.tkcx.api;

import com.tkcx.api.business.hjtemp.convert.AcctDetailConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/2 17:09
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AcctDetailConvertTest {

    @Test
    public void test(){

        // D:\data\acctprint\fileserver\download\test\20211028
        String path = "/data/acctprint/fileserver/download/test/20211028/t_act_one_detail.txt";
        AcctDetailConvert.makeAcctDetailList(path, 1, 200);
    }
}
