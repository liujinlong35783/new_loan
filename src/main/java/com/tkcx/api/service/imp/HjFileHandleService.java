package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.handle.HandleService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.exception.FileErrorCode;
import com.tkcx.api.utils.SFTPUtil;
import com.tkcx.api.vo.HjFileDataReqVo;
import com.tkcx.api.vo.afe.AfeDownFileBodyRspVo;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @project：
 * @author： zhaodan
 * @description：
 * @create： 2021/4/8 17:13
 */
@Slf4j
@Service
public class HjFileHandleService {

    @Autowired
    private HandleService handleService;

    @Autowired
    private HjFileInfoService hjFileInfoService;

    @Autowired
    private AfeCommonService afeCommonService;

    @Value("${storage.tempDownload.path}")
    private String tempDownloadPath;

    private static final int FILE_NAME_LENGTH = 40;

    /** SFTP 登录用户名*/
    @Value("${afe.sftp.username}")
    String username;
    /** SFTP 登录密码*/
    @Value("${afe.sftp.password}")
    private String password;
    /** SFTP 服务器地址IP地址*/
    @Value("${afe.sftp.host}")
    private String host;
    /** SFTP 端口*/
    @Value("${afe.sftp.port}")
    private int port;
    /** SFTP 基础路径*/
    @Value("${afe.sftp.basePath}")
    private String basePath;

    /**
     * 异步下载文件信息
     *
     * @param hjFileList
     * @throws ApplicationException
     */
    public void downloadHjFile(List<HjFileInfoModel> hjFileList, Date fileDate, HjFileDataReqVo req) throws ApplicationException {
        log.info("HjFileHandleService downloadHjFile hjFileList:"+hjFileList+",fileDate"+fileDate);
        for (HjFileInfoModel hjFileInfoModel : hjFileList) {
            FileDownloadReqVo fileVo = new FileDownloadReqVo();
            // 文件传输码
            fileVo.setDownloadTranCode(hjFileInfoModel.getFileTransCode());
            // 文件下载路径
            fileVo.setFilePath(hjFileInfoModel.getFilePath());
            // 文件下载日期
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String fileDate1 = format.format(hjFileInfoModel.getFileDate());
            fileVo.setFileDate(fileDate1);
            // 下载文件
            String downFilePath = downloadFile(fileVo,req);
            String fileType = hjFileInfoModel.getFileType();
            if(StringUtils.isNotEmpty(downFilePath)){
                log.info("日期{}，文件 {} ====== 下载成功", hjFileInfoModel.getFileDate(), fileType);

                /** TODO 要改成定时器方式触发解析互金文件逻辑
                 * 需要保存下载路径到数据库，然后定时器触发时，需要根据文件类型、文件日期，读取未删除、未读取的互金文件信息
                 */
                saveHjFileDownloadPath(downFilePath, hjFileInfoModel);
                // 下载成功后，解析文件，入库
                handleService.startHandle(fileType, fileDate);
            }
        }
    }

    public String downloadFile(FileDownloadReqVo req, HjFileDataReqVo reqCn) throws ApplicationException {
        if (req == null) {
            throw new ApplicationException(FileErrorCode.FILES_CANNOT_BE_EMPTY);
        }

        if (req.getFilePath() == null || req.getFilePath().isEmpty()) {
            throw new ApplicationException(FileErrorCode.FILES_CANNOT_BE_EMPTY);
        }

        String downloadTranCode = req.getDownloadTranCode();
        if (downloadTranCode == null || downloadTranCode.isEmpty()) {
            throw new ApplicationException(FileErrorCode.FILE_DOWNLOAD_CODE_CANNOT_BE_EMPTY);
        }

        String remoteFilePath = req.getFilePath();
        if (!remoteFilePath.startsWith("/")) {
            remoteFilePath = "/" + remoteFilePath;
        }
        String downloadPath = tempDownloadPath + DateUtil.format(new Date(),"yyyyMMddHHmmss") + "/";

        if (!StringUtils.isEmpty(downloadPath)) {
            File tempDownloadDir = new File(downloadPath);
            if (!tempDownloadDir.exists()) {
                tempDownloadDir.mkdir();
            }
        }

        File remoteFile = new File(remoteFilePath);
        String fileName = remoteFile.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

//        String localFullPathFileName = downloadPath
//                + RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH).toLowerCase() + "." + fileExt;
        String localFullPathFileName = downloadPath
                + fileName;
        File file = new File(downloadPath);
        if (!file.exists()){
            boolean result = file.mkdirs();
            log.info("创建下载路径："+downloadPath+","+result);
        }

        //发送下载报文及解析响应报文
        AfeDownFileBodyRspVo rspData = afeCommonService.getDownLoanRspData(req,reqCn);
        if (rspData==null){
            log.info("AFE下载文件信息响应为空");
        }
        //ftp服务器路径
        String ftpFilePath = rspData.getLocalFilePath();
        //ftp服务器获取对应文件
//        qnFtpClientServiceImpl.getFileStreamByTranCode(localFullPathFileName, remoteFilePath, downloadTranCode);
        //文服直连通过传输码下载文件
//        qnFtpClientServiceImpl.getFileStreamByTranCode(localFullPathFileName, localFilePath, downloadTranCode);
        //通过SFTP工具下载文件
        SFTPUtil sftpUtil = new SFTPUtil();
        List<String> fileInfo = fileExcute(ftpFilePath);
        //0:sftp服务器的路径 1:sftp端文件名
        log.info("sftp服务器的路径"+fileInfo.get(0));
        log.info("sftp端文件名"+fileInfo.get(1));
        boolean b = sftpUtil.downloadFile(fileInfo.get(0), fileInfo.get(1), localFullPathFileName,host,port,username,password);
        if (!b){
            log.error("ftp服务器获取对应文件失败");
        }
        return localFullPathFileName;
    }

    public static List<String> fileExcute(String ftpFilePath){
        int start = ftpFilePath.lastIndexOf(File.separator);
        String path = ftpFilePath.substring(0, start);
        String filename = ftpFilePath.substring(start + 1);
        ArrayList<String> info = new ArrayList<>();
        info.add(path);
        info.add(filename);
        return info;
    }

    /**
     * 保存互金下载路径
     *
     * @param downFilePath
     * @param hjFileInfoModel
     */
    public void saveHjFileDownloadPath(String downFilePath, HjFileInfoModel hjFileInfoModel){

        log.info("日期{}，文件类型:{}，下载路径：{}", hjFileInfoModel.getFileDate(), hjFileInfoModel.getFileType(), downFilePath);
        hjFileInfoModel.setFileDownloadPath(downFilePath);
        int totalNum = FileUtil.calTextLineNum(downFilePath);
        hjFileInfoModel.setFileLineTotalNum(totalNum);
        if(totalNum <= HjFileFlagConstant.READ_END_NUM_INITIAL){
            // 如果互金文件总行数小于501，则直接更新文件读取结束行数为文件的总行数
            hjFileInfoModel.setNextReadEndNum(totalNum);
        }

        int number = hjFileInfoService.updateDownloadFile(hjFileInfoModel);
        System.out.println(number);
    }
}
