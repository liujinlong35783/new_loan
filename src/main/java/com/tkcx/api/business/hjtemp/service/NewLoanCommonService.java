package com.tkcx.api.business.hjtemp.service;

import cn.hutool.core.date.DateUtil;
import com.google.inject.internal.util.Lists;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @project：
 * @author： liujinlong
 * @description： 新网贷文件处理公共逻辑
 * @create： 2022/5/16 14:23
 */
@Slf4j
@Service
public class NewLoanCommonService {

    @Autowired
    private HjFileInfoService hjFileInfoService;

    /**
     * 更新已读取的文件信息
     *
     * @param queryResult
     */
    public boolean updateReadFileInfo(HjFileInfoModel queryResult) {

        int readEndNum = queryResult.getNextReadEndNum();
        int txtTotalNum = queryResult.getFileLineTotalNum();
        /** 更新已读取的文件信息 */
        HjFileInfoModel updateInfo = new HjFileInfoModel();
        updateInfo.setFileId(queryResult.getFileId());
        updateInfo.setNextReadStartNum(readEndNum);
        updateInfo.setNextReadEndNum(calReadEndLine(readEndNum, txtTotalNum));
        updateInfo.setReadFlag(judgeReadFlag(readEndNum, txtTotalNum));
        return hjFileInfoService.updateById(updateInfo);
    }

    /**
     * 获取fileDate日未删除未读取的互金会计科目文件信息
     *
     * @param fileDate
     * @return
     */
    public HjFileInfoModel queryNLFileInfo(Date fileDate, String fileType) {

        HjFileInfoModel queryCon = new HjFileInfoModel();
        queryCon.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
        queryCon.setFileDate(fileDate);
        queryCon.setFileType(fileType);
        return hjFileInfoService.selectOneModel(queryCon);
    }


    /**
     * 计算读取文件结束行数
     *
     * @param readEndNum
     * @param txtTotalNum
     * @return
     */
    private int calReadEndLine(int readEndNum, int txtTotalNum) {

        if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) > HjFileFlagConstant.ONE_TIME_READ_LINE_NUM) {
            log.debug("文件总行数查询 - 已读取行数【大于】500行，文件读取继续");
            // 结束行数与文件总行数之差大于500，则结束行数更新为当前结束行数+500(步长)
            return readEndNum + HjFileFlagConstant.ONE_TIME_READ_LINE_NUM;
        }else if(readEndNum < txtTotalNum &&
                (txtTotalNum-readEndNum) <= HjFileFlagConstant.ONE_TIME_READ_LINE_NUM){
            log.debug("文件总行数查询 - 已读取行数【小于等于】500行，文件读取继续");
            /**
             * 结束行数与文件总行数之差小于500，结束行数更新为当前结束行数+剩下未读取的行数
             * 结束行数是文件总行数的倍数时，结束行数更新为当前结束行数+剩下未读取的行数+1，这里这样设置，是因为读取文件的条件是当前行数小于文件总行数
             */
            return readEndNum + (txtTotalNum-readEndNum) + HjFileFlagConstant.READ_START_NUM_INITIAL;
        }else if(readEndNum >= txtTotalNum) {
            log.debug("文件总行数查询 = 已读取行数，文件读取结束");
            return txtTotalNum;
        }else {
            log.error("文件读取结束行数判断异常！！！！");
            return 0;
        }
    }

    /**
     * 判断文件是否读取完成
     * @param readEndNum
     * @param txtTotalNum
     * @return
     */
    private String judgeReadFlag(int readEndNum, int txtTotalNum) {

        if(readEndNum == txtTotalNum){
            return HjFileFlagConstant.FINISHED;
        }
        return HjFileFlagConstant.NOT_FINISH;
    }

    @Value("${newLoan.filePath}")
    private String filePath;
    /**
     * 处理新网贷文件解析入库基本信息
     * @return
     */
    public Map<String, String> excuteFiles() {
        Date startDate = new Date();
        log.info("NewLoanCommonService excuteFiles ..." + startDate);
        String produceFilePath = new SimpleDateFormat("yyyyMMdd").format(new Date());
        log.info("新网贷解析文件路径："+filePath+File.separator+produceFilePath);
        File file = new File(filePath+File.separator+produceFilePath);
        File[] files = file.listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<HjFileInfoModel> hjFileList = Lists.newArrayList();
        HashMap<String, String> typeNameMap = new HashMap<>();
        for (File file1 : files) {
            HjFileInfoModel hjFileInfoModel = new HjFileInfoModel();
//            hjFileInfoModel.setFileId();
            hjFileInfoModel.setFileName(file1.getName());
            hjFileInfoModel.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
            hjFileInfoModel.setReadFlag(HjFileFlagConstant.NOT_FINISH);
            hjFileInfoModel.setNextReadStartNum(HjFileFlagConstant.READ_START_NUM_INITIAL);
            hjFileInfoModel.setNextReadEndNum(HjFileFlagConstant.READ_END_NUM_INITIAL);
            hjFileInfoModel.setFileTransCode("100024");
            String fileType = file1.getName().substring(file1.getName().indexOf("_")+1, file1.getName().lastIndexOf("_"));
            hjFileInfoModel.setFileType(fileType);
            hjFileInfoModel.setCreateDate(DateUtil.date());
            hjFileInfoModel.setFileDownloadPath(file1.getPath());

            int totalNum = FileUtil.calTextLineNum(file1.getPath());
            hjFileInfoModel.setFileLineTotalNum(totalNum);
            // 文件创建时间日期
            FileTime fileCreateTime=null;
            String createTime=null;
            try {
                fileCreateTime = Files.readAttributes(Paths.get(file1.getPath()), BasicFileAttributes.class).creationTime();
                createTime = sdf.format(fileCreateTime.toMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Date fileDate=null;
            try {
              fileDate = sdf.parse(createTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hjFileInfoModel.setFileDate(fileDate);
            // 文件路径
            hjFileInfoModel.setFilePath(file1.getPath());
            hjFileList.add(hjFileInfoModel);
            log.info("组装保存入库新网贷文件文件名称:"+file1.getName() );
            typeNameMap.put(fileType,file1.getName());
        }
        Date endDate = new Date();
        if(hjFileInfoService.saveBatch(hjFileList)) {
            log.info("组装保存入库新网贷文件信息全部完成");
            log.info("组装保存入库新网贷文件信息全部完成文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
            return typeNameMap;
        }
        return null;
    }


    /**
     * 处理新网贷文件解析入库基本信息
     * @return
     */
    public Map<String, String> excuteProduceFiles() {
        Date startDate = new Date();
        log.info("NewLoanCommonService excuteFiles ..." + startDate);
//        log.info("新网贷解析文件路径："+"D:\\\\JAVA\\\\Company\\\\tkcx\\\\kjpz_file"+File.separator+produceFilePath);
//        File file = new File("D:\\\\JAVA\\\\Company\\\\tkcx\\\\kjpz_file"+File.separator+produceFilePath);
        String path="D:\\\\\\\\JAVA\\\\\\\\Company\\\\\\\\tkcx\\\\\\\\kjpz_file";
        File[] files1 = new File(path).listFiles();
        ArrayList<Integer> dateFile = new ArrayList<>();
        for (File file : files1) {
            try {
                dateFile.add(Integer.parseInt(file.getName()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String fileName = Collections.max(dateFile) + "";
        File[] files = new File(path+File.separator+fileName).listFiles();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        List<HjFileInfoModel> hjFileList = Lists.newArrayList();
        HashMap<String, String> typeNameMap = new HashMap<>();
        for (File file1 : files) {
            HjFileInfoModel hjFileInfoModel = new HjFileInfoModel();
            hjFileInfoModel.setFileName(file1.getName());
            hjFileInfoModel.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
            hjFileInfoModel.setReadFlag(HjFileFlagConstant.NOT_FINISH);
            hjFileInfoModel.setNextReadStartNum(HjFileFlagConstant.READ_START_NUM_INITIAL);
            hjFileInfoModel.setNextReadEndNum(HjFileFlagConstant.READ_END_NUM_INITIAL);
            hjFileInfoModel.setFileTransCode("100024");
            String fileType = file1.getName().substring(file1.getName().indexOf("_")+1, file1.getName().lastIndexOf("_"));
            hjFileInfoModel.setFileType(fileType);
            hjFileInfoModel.setCreateDate(DateUtil.date());
            hjFileInfoModel.setFileDownloadPath(file1.getPath());

            int totalNum = FileUtil.calTextLineNum(file1.getPath());
            hjFileInfoModel.setFileLineTotalNum(totalNum);
            // 文件创建时间日期
            FileTime fileCreateTime=null;
            String createTime=null;
            try {
                fileCreateTime = Files.readAttributes(Paths.get(file1.getPath()), BasicFileAttributes.class).creationTime();
                createTime = sdf.format(fileCreateTime.toMillis());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Date fileDate=null;
            try {
                fileDate = sdf.parse(createTime);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            hjFileInfoModel.setFileDate(fileDate);
            // 文件路径
            hjFileInfoModel.setFilePath(file1.getPath());
            hjFileList.add(hjFileInfoModel);
            log.info("组装保存入库新网贷文件文件名称:"+file1.getName() );
            typeNameMap.put(fileType,file1.getName());
        }
        Date endDate = new Date();
        if(hjFileInfoService.saveBatch(hjFileList)) {
            log.info("组装保存入库新网贷文件信息全部完成");
            log.info("组装保存入库新网贷文件信息全部完成文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
            return typeNameMap;
        }
        return null;
    }

//    public static void main(String[] args) {
//        int v =(int)(20+Math.random()*(80-20+1));
//        System.out.println(v);
//    }
//    public static void main(String[] args) {
//        Date startDate = new Date();
//        log.info("NewLoanCommonService excuteFiles ..." + startDate);
////        String produceFilePath = new SimpleDateFormat("yyyyMMdd").format(DateUtils.dateIncreaseByDay(new Date(), -1));
////        log.info("新网贷解析文件路径："+"D:\\\\JAVA\\\\Company\\\\tkcx\\\\kjpz_file"+File.separator+produceFilePath);
////        File file = new File("D:\\\\JAVA\\\\Company\\\\tkcx\\\\kjpz_file"+File.separator+produceFilePath);
//        String path="D:\\\\\\\\JAVA\\\\\\\\Company\\\\\\\\tkcx\\\\\\\\kjpz_file";
//        File[] files1 = new File(path).listFiles();
//        ArrayList<Integer> dateFile = new ArrayList<>();
//        for (File file : files1) {
//            try {
//                dateFile.add(Integer.parseInt(file.getName()));
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//        }
//        String fileName = Collections.max(dateFile) + "";
//        File[] files = new File(path+File.separator+fileName).listFiles();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//        List<HjFileInfoModel> hjFileList = Lists.newArrayList();
//        HashMap<String, String> typeNameMap = new HashMap<>();
//        for (File file1 : files) {
//            HjFileInfoModel hjFileInfoModel = new HjFileInfoModel();
//            hjFileInfoModel.setFileName(file1.getName());
//            hjFileInfoModel.setDeleteFlag(HjFileFlagConstant.NOT_DELETED);
//            hjFileInfoModel.setReadFlag(HjFileFlagConstant.NOT_FINISH);
//            hjFileInfoModel.setNextReadStartNum(HjFileFlagConstant.READ_START_NUM_INITIAL);
//            hjFileInfoModel.setNextReadEndNum(HjFileFlagConstant.READ_END_NUM_INITIAL);
//            hjFileInfoModel.setFileTransCode("100024");
//            String fileType = file1.getName().substring(file1.getName().indexOf("_")+1, file1.getName().lastIndexOf("_"));
//            hjFileInfoModel.setFileType(fileType);
//            hjFileInfoModel.setCreateDate(DateUtil.date());
//            hjFileInfoModel.setFileDownloadPath(file1.getPath());
//
//            int totalNum = FileUtil.calTextLineNum(file1.getPath());
//            hjFileInfoModel.setFileLineTotalNum(totalNum);
//            // 文件创建时间日期
//            FileTime fileCreateTime=null;
//            String createTime=null;
//            try {
//                fileCreateTime = Files.readAttributes(Paths.get(file1.getPath()), BasicFileAttributes.class).creationTime();
//                createTime = sdf.format(fileCreateTime.toMillis());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            Date fileDate=null;
//            try {
//                fileDate = sdf.parse(createTime);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            hjFileInfoModel.setFileDate(fileDate);
//            // 文件路径
//            hjFileInfoModel.setFilePath(file1.getPath());
//            hjFileList.add(hjFileInfoModel);
//            log.info("组装保存入库新网贷文件文件名称:"+file1.getName() );
//            typeNameMap.put(fileType,file1.getName());
//        }
//        Date endDate = new Date();
//        if(true) {
//            log.info("组装保存入库新网贷文件信息全部完成");
//            log.info("组装保存入库新网贷文件信息全部完成文件解析耗时：{}", DateUtil.formatBetween(startDate, endDate));
//        }
//    }
}
