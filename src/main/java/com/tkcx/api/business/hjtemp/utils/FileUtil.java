package com.tkcx.api.business.hjtemp.utils;

import com.google.inject.internal.util.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 文件操作工具类
 * @author
 */
@Slf4j
public class FileUtil {

    /**

     * 读取文件最后N行
     * 根据换行符判断当前的行数，
     * 使用统计来判断当前读取第N行
     * PS:输出的List是倒叙，需要对List反转输出
     * @param file 待文件
     * @param numRead 读取的行数
     * @return List
     */
    public static List readLastNLine(File file, long numRead) {

        // 定义结果集
        List result = new ArrayList();
        //行数统计
        long count = 0;
        // 排除不可读状态
        if (!file.exists() || file.isDirectory() || !file.canRead()) {
            return null;
        }
        // 使用随机读取
        RandomAccessFile fileRead = null;
        try {
            //使用读模式
            fileRead = new RandomAccessFile(file, "r");
            //读取文件长度
            long length = fileRead.length();
            //如果是0，代表是空文件，直接返回空结果
            if (length == 0L) {
                return result;
            } else {
                //初始化游标
                long pos = length - 1;
                while (pos > 0) {
                    pos--;
                    //开始读取
                    fileRead.seek(pos);
                    //如果读取到\n代表是读取到一行
                    if (fileRead.readByte() == '\n') {
                        //使用readLine获取当前行
                        String line = fileRead.readLine();
                        //保存结果
                        result.add(line);
                        //打印当前行
                        System.out.println(line);
                        //行数统计，如果到达了numRead指定的行数，就跳出循环
                        count++;
                        if (count == numRead) {
                            break;
                        }
                    }
                }
                if (pos == 0) {
                    fileRead.seek(0);
                    result.add(fileRead.readLine());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileRead != null) {
                try {
                    //关闭资源
                    fileRead.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }


    /**
     * 计算文件一共有多少行
     * @param filePath
     * @return
     */
    public static int calTextLineNum(String filePath) {

        int count=0;
        LineIterator it = null;
        try {
            File file = new File(filePath);
            it = FileUtils.lineIterator(file, "UTF-8");

            while (it.hasNext()) {
                StringBuffer lineStr = new StringBuffer(it.nextLine());
                if(StringUtils.isEmpty(lineStr)){
                    continue;
                }
                count++;
            }
        } finally {
            LineIterator.closeQuietly(it);
            return count;
        }
    }

    /**
     * 读取文件N行为StringBuffer格式
     *
     * @param filePath
     * @param readStartNum
     * @param readEndNum
     * @return
     */
    public static List<StringBuffer> readFileNLine(String filePath, int readStartNum, int readEndNum) {

        LineIterator it = null;
        List<StringBuffer> strList = Lists.newArrayList();
        int count=0;
        try {
            File file = new File(filePath);
            it = FileUtils.lineIterator(file, "UTF-8");

            while (it.hasNext()) {
                StringBuffer lineStr = new StringBuffer(it.nextLine());
                count++;
                if(StringUtils.isEmpty(lineStr)){
                    continue;
                }
                if(count >= readStartNum && count < readEndNum){
                    strList.add(lineStr);
                }
            }
        }catch(Exception e) {
            log.error("按行读取文件异常：{}",e);
        } finally {
            LineIterator.closeQuietly(it);
            return strList;
        }
    }


    public static void main(String[] args) {
        String path="C:\\Users\\ccjh\\Desktop\\test2.txt";
        int total = FileUtil.calTextLineNum(path);
        int readStartNum = HjFileFlagConstant.READ_START_NUM_INITIAL;
        int readEndNum = HjFileFlagConstant.READ_END_NUM_INITIAL;
        System.out.println(readStartNum +"**********"+ readEndNum);
        System.out.println(FileUtil.readFileNLine(path,readStartNum,readEndNum).size());
        readStartNum = readEndNum;
        readEndNum = FileUtil.calReadEndLine(readEndNum, total);
        System.out.println(readStartNum +"**********"+ readEndNum);
        System.out.println(FileUtil.readFileNLine(path,readStartNum,readEndNum).size());
    }



    public static int calReadEndLine(int readEndNum, int txtTotalNum) {

        log.info("已读取文件行数：【{}】，文件总行数：【{}】", readEndNum, txtTotalNum);
        if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) > HjFileFlagConstant.ONE_TIME_READ_LINE_NUM) {
            log.info("文件总行数查询 - 已读取行数【大于】500行，文件读取继续");
            // 结束行数与文件总行数之差大于500，则结束行数更新为当前结束行数+500(步长)
            return readEndNum + HjFileFlagConstant.ONE_TIME_READ_LINE_NUM;
        }else if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) <= HjFileFlagConstant.ONE_TIME_READ_LINE_NUM){
            log.info("文件总行数查询 - 已读取行数【小于等于】500行，文件读取继续");
            /**
             * 结束行数与文件总行数之差小于500，结束行数更新为当前结束行数+剩下未读取的行数
             * 结束行数是文件总行数的倍数时，结束行数更新为当前结束行数+剩下未读取的行数+1，这里这样设置，是因为读取文件的条件是当前行数小于文件总行数
             */
            return readEndNum + (txtTotalNum-readEndNum) + HjFileFlagConstant.READ_START_NUM_INITIAL;
        }else if(readEndNum == txtTotalNum) {
            log.info("文件总行数查询 = 已读取行数，文件读取结束");
            return txtTotalNum;
        }else {
            log.error("文件读取结束行数判断异常！！！！");
            return 0;
        }
    }


    public static String readFileByLine(String path) {

        FileInputStream inputStream = null;
        Scanner sc = null;
        String line = null;
        try{
            inputStream = new FileInputStream(path);
            sc = new Scanner(inputStream,"UTF-8");
            while(sc.hasNextLine()) {
                line = sc.nextLine();
                System.out.println(line);
            }
            // note that Scanner suppresses exceptions
            if(sc.ioException() != null) {
                throw sc.ioException();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(sc != null) {
                sc.close();
            }
            return line;
        }
    }

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
            log.error("读取文件{}，异常信息{}", filePath, e);
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
