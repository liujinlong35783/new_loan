package com.tkcx.api;

import com.tkcx.api.business.hjtemp.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/19 14:01
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class FileUtilTest {

    @Test
    public void calTextNum(){

        String downFilePath = "E:\\works\\tkcx-jydb-files\\会计凭证\\会计凭证CPU使用率高问题\\kjpz20211118日志\\1.2\\jtbejpvh446rhhsf3a22tumie0zeadorwntqptr4.txt";
        System.out.println(FileUtil.calTextLineNum(downFilePath));
    }


}
