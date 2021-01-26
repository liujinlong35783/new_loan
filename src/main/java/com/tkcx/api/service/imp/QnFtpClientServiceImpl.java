package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.exception.FileErrorCode;
import com.tkcx.api.service.FtpClientService;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import com.tkcx.api.vo.ftpFile.FileDownloadRspVo;
import com.tkcx.api.vo.ftpFile.FileUploadReqVo;
import com.tkcx.api.vo.ftpFile.FileUploadRspVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

import com.alibaba.druid.util.StringUtils;
import com.dcfs.esb.ftp.common.error.FtpException;
import com.dcfs.esb.ftp2.client.FtpClientConfig;
import com.dcfs.esb.ftp2.client.FtpGet;
import com.dcfs.esb.ftp2.client.FtpPut;

import java.io.File;
import java.util.Date;

/**
 * 行内文件系统上传下载接口
 *
 * @author wangjunhai
 * @date: 2019年1月31日
 */
@Slf4j
@Service
public class QnFtpClientServiceImpl implements FtpClientService {
    /**
     * 秦农业务接入网关上传传输代码:100030
     */
    @Value("${storage.qnUploadtranCode}")
    private String qnUploadtranCode;

    /**
     * 电子账户核心系统ACC下载传输代码:100024
     */
    @Value("${storage.accDowntranCode}")
    private String accDowntranCode;

    /**
     * 第三方jar需要的配置路径
     */
    @Value("${storage.configPath}")
    private String configPath;

    /**
     * 临时上传目录
     */
    @Value("${storage.tempUpload.path}")
    private String tempUploadPath;

    /**
     * 临时下载目录
     */
    @Value("${storage.tempDownload.path}")
    private String tempDownloadPath;

    /**
     * ftp 远程默认目录
     */
    @Value("${storage.remote.path}")
    private String remotePath;

    private static final int FILE_ID_INDEX = 13;
    private static final int FILE_NAME_LENGTH = 40;

    /**
     * 文件上传
     *
     * @param localFullPathFileName 本地路径
     * @param remotePath            远程路径
     * @return
     * @throws ApplicationException
     */
    public String upload(String localFullPathFileName, String remotePath) throws ApplicationException {
        log.info("开始上传文件,本地文件：{},ftp服务器路径:{}", localFullPathFileName, remotePath);
        try {
            // 获取当前jar运行目录
            String jarPath = new ApplicationHome(getClass()).getSource().getParentFile().toString();
            log.info("jar路徑" + jarPath);
            configPath = jarPath + configPath;

            // 初始化ftp配置
            FtpClientConfig.setConfFilePath(configPath);
            FtpClientConfig config = FtpClientConfig.getInstance();
            FtpPut put = new FtpPut(localFullPathFileName, remotePath, qnUploadtranCode, 0, config);
            String retFilePath = put.doPutFile();
            put.close();
            log.info("上传结果：{}", retFilePath);
            return retFilePath;
        } catch (FtpException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.UPLOAD_FILE_ERROR);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.UPLOAD_FILE_ERROR);
        }
    }

    /**
     * 文件下载
     *
     * @param localFullPathFileName 本地保存路径
     * @param remotePath            远程路径
     * @return
     * @throws ApplicationException
     */
    public Boolean getQnFileStream(String localFullPathFileName, String remotePath) throws ApplicationException {
        try {

            // 获取当前jar运行目录
            String jarPath = new ApplicationHome(getClass()).getSource().getParentFile().toString();
            log.info("jar路径" + jarPath);
            configPath = jarPath + configPath;
            // 初始化ftp配置
            FtpClientConfig.setConfFilePath(configPath);
            FtpClientConfig config = FtpClientConfig.getInstance();
            FtpGet ftpGet = new FtpGet(remotePath, localFullPathFileName, qnUploadtranCode, 0, config);
            Boolean result = ftpGet.doGetFile();
            ftpGet.close();
            return result;
        } catch (FtpException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.GET_FILE_FAIL);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.GET_FILE_FAIL);
        }
    }

    /**
     * 文件下载
     *
     * @param localFullPathFileName 本地保存路径
     * @param remotePath            远程路径
     * @return
     * @throws ApplicationException
     */
    public Boolean getAccFileStream(String localFullPathFileName, String remotePath) throws ApplicationException {
        try {

            // 获取当前jar运行目录
            String jarPath = new ApplicationHome(getClass()).getSource().getParentFile().toString();
            log.info("jar路径" + jarPath);
            configPath = jarPath + configPath;
            // 初始化ftp配置
            FtpClientConfig.setConfFilePath(configPath);
            FtpClientConfig config = FtpClientConfig.getInstance();
            FtpGet ftpGet = new FtpGet(remotePath, localFullPathFileName, accDowntranCode, 0, config);
            Boolean result = ftpGet.doGetFile();
            ftpGet.close();
            return result;
        } catch (FtpException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.GET_FILE_FAIL);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.GET_FILE_FAIL);
        }
    }

    /**
     * 通过传输码下载文件
     *
     * @param localFullPathFileName
     * @param remotePath
     * @param downloadTranCode
     * @return
     * @throws ApplicationException
     */
    public Boolean getFileStreamByTranCode(String localFullPathFileName, String remotePath, String downloadTranCode)
            throws ApplicationException {
        try {

            // 获取当前jar运行目录
            String jarPath = new ApplicationHome(getClass()).getSource().getParentFile().toString();
            log.info("jar路径" + jarPath);
            configPath = jarPath + configPath;
            // 初始化ftp配置
            FtpClientConfig.setConfFilePath(configPath);
            FtpClientConfig config = FtpClientConfig.getInstance();
            FtpGet ftpGet = new FtpGet(remotePath, localFullPathFileName, downloadTranCode, 0, config);
            Boolean result = ftpGet.doGetFile();
            ftpGet.close();
            return result;
        } catch (FtpException e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.GET_FILE_FAIL);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new ApplicationException(FileErrorCode.GET_FILE_FAIL);
        }
    }

    /**
     * 上传单个文件
     */
    @Override
    public FileUploadRspVo ftpFileUpload(FileUploadReqVo req) throws ApplicationException {
        if (req == null) {
            throw new ApplicationException(FileErrorCode.FILES_CANNOT_BE_EMPTY);
        }

        if (req.getFilePath() == null || req.getFilePath().isEmpty()) {
            throw new ApplicationException(FileErrorCode.FILES_CANNOT_BE_EMPTY);
        }

        String filePath = req.getFilePath();
        String fileName = req.getFileName();
        String fileExt = req.getFileType();
        File remoteFile = new File(filePath);
        if (StringUtils.isEmpty(fileName)) {
            fileName = remoteFile.getName();
        }

        if (StringUtils.isEmpty(fileExt)) {
            fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        if (!StringUtils.isEmpty(tempUploadPath)) {
            File tempFile = new File(tempUploadPath);
            if (!tempFile.exists()) {
                tempFile.mkdirs();
            }
        }
        filePath = filePath + "." +fileExt;
        fileName = fileName + "." +fileExt;
        String localFullPathFileName = filePath;
        if(filePath.indexOf(tempUploadPath)==-1){
            localFullPathFileName = tempUploadPath + filePath;
        }
        String remoteFullPathFileName;
        if(StringUtils.isEmpty(req.getRemoteFullPath())){
            String txnDt = DateUtil.format(new Date(),"yyyyMMdd");
            txnDt = "/" + txnDt + "/";
            //String remoteFullPathFileName = remotePath + fileName;
            remoteFullPathFileName = txnDt + fileName;
        }else {
            remoteFullPathFileName = req.getRemoteFullPath() + fileName;
        }
        if (!new File(localFullPathFileName).exists()) {
            log.info("本地文件不存在" + localFullPathFileName);
            throw new ApplicationException(FileErrorCode.FILE_CANNOT_BE_EMPTY);
        }

        String uploadUrl = this.upload(localFullPathFileName, remoteFullPathFileName);
        FileUploadRspVo rspVo = new FileUploadRspVo();
        if (!uploadUrl.startsWith("/")) {
            uploadUrl = "/" + uploadUrl;
        }
        rspVo.setUrl(uploadUrl);
        rspVo.setTranCode(qnUploadtranCode);
        return rspVo;
    }

    /**
     * 通过传输码下载文件
     *
     * @param req
     * @return
     * @throws ApplicationException
     */
    @Override
    public FileDownloadRspVo ftpFileDownload(FileDownloadReqVo req) throws ApplicationException {
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

        if (!StringUtils.isEmpty(tempDownloadPath)) {
            File tempDownloadDir = new File(tempDownloadPath);
            if (!tempDownloadDir.exists()) {
                tempDownloadDir.mkdir();
            }
        }

        File remoteFile = new File(remoteFilePath);
        String fileName = remoteFile.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);
        String localFullPathFileName = tempDownloadPath
                + RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH).toLowerCase() + "." + fileExt;
        this.getFileStreamByTranCode(localFullPathFileName, remoteFilePath, downloadTranCode);
        FileDownloadRspVo rspVo = new FileDownloadRspVo();
        rspVo.setUrl(localFullPathFileName);
        return rspVo;
    }

}
