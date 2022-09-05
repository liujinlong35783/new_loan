package com.acct.job.util;


import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 对map数据进行提取
 *
 * @author Tom
 * @version 1.0 2016年11月9日 Tom create
 * @date 2016年11月9日 下午7:59:56
 * @copyright Copyright © 2016 广电运通 All rights reserved.
 */
@Slf4j
public class MapUtil {


    /**
     * 获取Map中实际数据
     *
     * @param key
     * @param obj
     * @return
     */
    public static String objToStr(String key, Map<String, Object> obj) {
        return obj.get(key) == null ? "" : obj.get(key).toString().trim();
    }

    /**
     * 获取Map中实际数据
     *
     * @param key
     * @param obj
     * @return
     */
    public static String objToStr(String key, Map<String, Object> obj, String defaultValue) {
        String returnStr = obj.get(key) == null ? defaultValue : obj.get(key).toString().trim();
        return "".equals(returnStr) ? defaultValue : returnStr;
    }

    /**
     * 获取Map中实际数据
     *
     * @param key
     * @param obj
     * @return
     */
    public static String strToStr(String key, Map<String, String> obj) {
        obj = obj == null ? new HashMap<String, String>() : obj;
        return obj.get(key) == null ? "" : obj.get(key).toString().trim();
    }

    /**
     * 获取Map中的值 转换为整型
     *
     * @param key
     * @param obj
     * @return
     */
    public static Integer objToInteger(String key, Map<String, Object> obj) {
        return objToInteger(key, obj, 0);
    }


    /**
     * 获取Map中的值 转换为整型
     *
     * @param key
     * @param obj
     * @return
     */
    public static Integer objToInteger(String key, Map<String, Object> obj, int defaultValue) {
        int returnValue = defaultValue;
        String value = obj.get(key) == null ? "" : obj.get(key).toString().trim();
        value = "".equals(value) ? "" + defaultValue : value;
        try {
            returnValue = Integer.valueOf(value);
        } catch (Exception e) {
            log.error("MapUtil objToInteger [" + value + "] error " + e.getMessage());
        }
        return returnValue;
    }

    /**
     * 获取Map中的值 转换为整型
     *
     * @param key
     * @param obj
     * @return
     */
    public static Double objToDouble(String key, Map<String, Object> obj, double defaultValue) {
        double returnValue = defaultValue;
        try {
            String value = obj.get(key) == null ? "" : obj.get(key).toString().trim();
            value = "".equals(value) ? "" + defaultValue : value;
            returnValue = Double.valueOf(value);
        } catch (Exception e) {
            log.error("MapUtil objToDouble error " + e.getMessage());
        }
        return returnValue;
    }

    /**
     * 获取Map中的值 转换为Long型
     *
     * @param key
     * @param obj
     * @return
     */
    public static Long objToLong(String key, Map<String, Object> obj) {
        return objToLong(key, obj, 0L);
    }

    /**
     * 获取Map中的值 转换为Long型
     *
     * @param key
     * @param obj
     * @return
     */
    public static Long objToLong(String key, Map<String, Object> obj, long defaultValue) {
        long returnValue = defaultValue;
        try {
            String value = obj.get(key) == null ? "" : obj.get(key).toString().trim();
            value = "".equals(value) ? "" + defaultValue : value;
            returnValue = Long.valueOf(value);
        } catch (Exception e) {
            log.error("MapUtil objToLong error " + e.getMessage());
        }
        return returnValue;
    }

    /**
     * 获取Map中的值 转换为整型
     *
     * @param key
     * @param obj
     * @return
     */
    public static Integer objToIntegerByMap(String key, Map<String, Object> obj, int defaultValue) {
        int returnValue = defaultValue;
        try {
            String val = obj.get(key) == null ? "" : obj.get(key).toString();
            val = "".equals(val) ? "" + defaultValue : val;
            returnValue = Integer.valueOf(val);
        } catch (Exception e) {
            log.error("MapUtil objToIntegerByMap error " + e.getMessage());
        }
        return returnValue;
    }

    public static boolean objToBoolean(String key, Map<String, Object> obj) {
        boolean returnValue = false;
        try {
            String val = obj.get(key) == null ? "false" : obj.get(key).toString().trim();
            returnValue = Boolean.valueOf(val);
        } catch (Exception e) {
            log.error("MapUtil objToBoolean error " + e.getMessage());
        }
        return returnValue;
    }

    /**
     * 获取Map中实际数据
     *
     * @param key
     * @param obj
     * @return
     */
    public static Date objToDate(String key, Map<String, Object> obj) {
        Date returnStr = null;
        try {
            returnStr = (Date) (obj.get(key) == null ? null : obj.get(key));
        } catch (Exception e) {
            log.error("MapUtil objToDate error " + e.getMessage());
        }
        return returnStr;
    }

    /**
     * 获取Map中实际数据
     *
     * @param key
     * @param obj
     * @return
     */
    public static Date objToDate(String key, Map<String, Object> obj, String praseFormat) {
        Date returnStr = null;
        try {
            String str = objToStr(key, obj);
            returnStr = new SimpleDateFormat(praseFormat).parse(str);
        } catch (Exception e) {
            log.error("MapUtil objToDate error " + e.getMessage());
        }
        return returnStr;
    }

    /**
     * 根据MAP中的值对Key值进行转换
     *
     * @param key
     * @param changeObj
     * @return
     */
    public static String strTostrStatus(String key, Map<String, String> changeObj) {
        return changeObj.get(key) == null ? key : changeObj.get(key);
    }

    /**
     * 将集合转化为二维数组
     *
     * @param arrList
     * @return
     */
    public static String[][] toDualStrs(List<Map<String, Object>> arrList) {
        String[][] ret = null;
        if (arrList == null)
            return null;
        try {
            int x = arrList.size();

            for (int i = 0; i < arrList.size(); i++) {
                Map<String, Object> map = arrList.get(i);
                if (i == 0) {
                    int y = map.size();
                    ret = new String[x][y];
                }
                Iterator<String> keyIter = map.keySet().iterator();
                int j = 0;
                while (keyIter.hasNext()) {
                    String key = keyIter.next();
                    String value = MapUtil.objToStr(key, map);
                    if (ret != null) {
                        ret[i][j] = value;
                    }
                    j++;
                }
            }
        } catch (Exception e) {
            log.error("MapUtil toDualStrs error " + e.getMessage());
        }
        return ret;
    }

    /**
     * 转化Map中的数据 即将原始数据进行翻译处理
     *
     * @param realName
     * @param queryInfoMap
     * @param changeStrMap
     * @return
     */
    public static String changeStrInfo(String realName, Map<String, Object> queryInfoMap, Map<String, String> changeStrMap) {
        return changeStrMap.get(objToStr(realName, queryInfoMap)) == null ? objToStr(realName, queryInfoMap) : changeStrMap.get(objToStr(realName, queryInfoMap));
    }

    /**
     * 替换注诸如 1,2,3 需翻译的数据
     *
     * @param realName
     * @param queryInfoMap
     * @param changeStrMap
     * @return
     */
    public static String changeSomeStrInfo(String realName, Map<String, Object> queryInfoMap, Map<String, String> changeStrMap) {
        StringBuilder returnStr = new StringBuilder("");
        String realValue = objToStr(realName, queryInfoMap);
        if (!"".equals(realValue)) {
            String[] realValueArr = realValue.split("\\,");
            for (String string : realValueArr) {
                returnStr.append(changeStrMap.get(string) + ",");
            }
            returnStr.setCharAt(returnStr.length() - 1, ' ');
        }
        return returnStr.toString();

    }
    /**
     * 转化Map的key值
     */
    public  static  void transMap(Map<String,Object> dataMap,String [][] data){

        Set<String> nameSet= dataMap.keySet();
        for (int row=0 ; row<data.length;row++){
        String name=data[row][0];
        if(nameSet.contains(name)){
            String nameDB=data[row][1];
            dataMap.put(nameDB,dataMap.get(name));
        }
    }

}


}
