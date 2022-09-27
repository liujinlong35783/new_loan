package com.tkcx.api;

import com.acct.job.jobhandler.ScheduleRunnable;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/1/20 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class ScheduleRunnableTest {

    @Test
    public void testRun(){

        new ScheduleRunnable().run();
    }

}
