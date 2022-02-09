package com.tkcx.api.business.hjtemp.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/3 14:54
 */
@Slf4j
public class HjStringUtils {

    public static StringBuffer[] convertString2Buffer(String lineStr, int length,int readStartNum, int lineNum) {

        String[] columns = lineStr.split("\\^@");
        if(columns == null || columns.length != length) {
            log.error("第{}行，内容错误", readStartNum+lineNum);
            return null;
        }
        StringBuffer[] buffers = new StringBuffer[length];
        for (int i=0; i<columns.length ; i++) {
            buffers[i] = new StringBuffer(columns[i].trim());
        }
        return buffers;
    }

}
