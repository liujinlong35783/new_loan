package com.tkcx.api.business.hjtemp.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类
 */
@Slf4j
public class FileUtil {

    /**
     * 按行读取文件
     * @return
     */
    public static List<String> readLine(String filePath){
        List<String> lineList = new ArrayList<String>();
        try {
            File file =  new File(filePath);
            if(file.exists()){
                lineList = FileUtils.readLines(file,"UTF-8");
                log.info("文件读取成功");
            }else{
                log.error(filePath+"--路径下文件不存在");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineList;
    }

    /**
     *  根据结果集生成txt文件
     * @param dataList 数据结果集
     * @param pathname 保存路径
     * @return 是否成功
     */
    public static boolean makeFile(List dataList, String pathname){
        boolean result = true;
        File file = new File(pathname);
        try {
            FileUtils.writeLines(file, "GBK", dataList);
        } catch (IOException e) {
            log.error("生成文件异常：{}", e);
            e.printStackTrace();
            result = false;
        }finally {
            return result;
        }
    }
}
