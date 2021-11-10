package com.tkcx.api.business.hjtemp.utils;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/3 14:54
 */
public class HjStringUtils {

    public static StringBuffer[] convertString2Buffer(String lineStr, int length) {

        String[] columns = lineStr.split("\\^@");
        if(columns == null && columns.length != length) {
            return null;
        }
        StringBuffer[] buffers = new StringBuffer[length];
        for (int i=0; i<columns.length ; i++) {
            buffers[i] = new StringBuffer(columns[i].trim());
        }
        return buffers;
    }

}
