package com.tkcx.api.utils;

import org.apache.commons.lang3.StringUtils;

public class BusinessUtils {

    /**
     * 获取 根据key获取json字符串中的值
     * @param jsonMsg
     * @return
     */
    public static String getValueByKey(String jsonMsg, String key){
        String value = null;
        if(StringUtils.isNotEmpty(jsonMsg) && StringUtils.isNotEmpty(key)){
            int start = jsonMsg.indexOf(key + "\":\"");
            if (start != -1) {
                start += key.length() + 3;
                int end = jsonMsg.indexOf("\",\"", start);
                if(end!=-1)
                    value = jsonMsg.substring(start, end);
            }
        }
        return value;
    }
    /**
     * 获取 根据key获取json字符串中的数值
     * @param jsonMsg
     * @return
     */
    public static String getNumByKey(String jsonMsg, String key){
        String value = null;
        if(StringUtils.isNotEmpty(jsonMsg) && StringUtils.isNotEmpty(key)){
            int start = jsonMsg.indexOf(key + "\":");
            if (start != -1) {
                start += key.length() + 2;
                int end = jsonMsg.indexOf(",\"", start);
                if(end!=-1)
                    value = jsonMsg.substring(start, end);
            }
        }
        return value;
    }
    public static String getPrincipalStatus(String message){
        String value = getValueByKey(message,"ZHCHBJIN");//正常本金
        if(value!=null){
            return "ZHCHBJIN";
        }
        value = getValueByKey(message,"YUQIBJIN");//逾期本金
        if(value!=null){
            return "YUQIBJIN";
        }
        value = getValueByKey(message,"DZHIBJIN");//呆滞本金
        if(value!=null){
            return "DZHIBJIN";
        }
        value = getValueByKey(message,"DAIZBJIN");//呆账本金
        if(value!=null){
            return "DAIZBJIN";
        }
        return "ZHCHBJIN";
    }


    public static String addDefaultVal(int size) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            buffer.append(" ");
        }
        return buffer.toString();
    }
}
