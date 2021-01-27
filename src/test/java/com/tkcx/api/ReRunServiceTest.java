package com.tkcx.api;

import cn.hutool.core.date.DateUtil;
import org.junit.Test;

import java.util.Date;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/1/27 12:35
 */
public class ReRunServiceTest {

    @Test
    public void test1(){
        Date date = new Date();

        System.out.println(DateUtil.offsetDay(date, 1));
    }
}
