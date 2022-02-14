package com.tkcx.api.business.hjtemp.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/11/3 14:54
 */
@Slf4j
public class AcctStringUtils {

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

    /**
     * 转化字符串为十六进制编码
     * @param s
     * @return
     */
    public static String toHexString(String s)
    {
        String str="";

        for (int i=0;i<s.length();i++)
        {
            int ch = (int)s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return "0x" + str;//0x表示十六进制
    }

}
