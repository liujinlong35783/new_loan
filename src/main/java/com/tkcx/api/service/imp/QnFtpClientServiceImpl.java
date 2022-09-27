package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.alibaba.druid.util.StringUtils;
import com.dcfs.esb.ftp.common.error.FtpException;
import com.dcfs.esb.ftp2.client.FtpClientConfig;
import com.dcfs.esb.ftp2.client.FtpGet;
import com.dcfs.esb.ftp2.client.FtpPut;
import com.tkcx.api.exception.FileErrorCode;
import com.tkcx.api.service.FtpClientService;
import com.tkcx.api.utils.SFTPUtil;
import com.tkcx.api.vo.ZhqdQueryReqVo;
import com.tkcx.api.vo.afe.AfeUploadBodyRspVo;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import com.tkcx.api.vo.ftpFile.FileDownloadRspVo;
import com.tkcx.api.vo.ftpFile.FileUploadReqVo;
import com.tkcx.api.vo.ftpFile.FileUploadRspVo;
import common.core.exception.ApplicationException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.stereotype.Service;

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
//            configPath = jarPath + configPath;
            configPath = jarPath + "/ftpClientConfig.properties";

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
    @Autowired
    private AfeCommonService afeCommonService;

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
     * 上传单个文件
     */
    @Override
    public FileUploadRspVo ftpFileUpload(FileUploadReqVo req, ZhqdQueryReqVo queryReq) throws ApplicationException {
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

        //ftp服务器上传对应文件
        //直连esb的上传
        //String uploadUrl = this.upload(localFullPathFileName, remoteFullPathFileName);
        //通过SFTP上传至ftp服务器
        SFTPUtil sftpUtil = new SFTPUtil();
        String txnDt = DateUtil.format(new Date(),"yyyyMMdd");
        String uploadPath=File.separator+basePath+File.separator+File.separator+"send"+File.separator+txnDt+File.separator;
        log.info("上传路径："+uploadPath);
        log.info("上传文件名："+fileName);
        sftpUtil.uploadFile(host,port,username,password,File.separator+basePath+File.separator,File.separator+"send"+File.separator+txnDt+File.separator,fileName,localFullPathFileName);
//        sftpUtil.uploadFile(host,port,username,password,"//upload/KNQNJY01/",txnDt+"/send",fileName,localFullPathFileName);
        //发送上传报文及解析响应报文
        AfeUploadBodyRspVo rspData = afeCommonService.getUpLoadRspData(req,queryReq,fileExt);
        if (rspData==null){
            log.info("AFE下载文件信息响应为空");
        }

        //afe上传文件到文服的路径
        String fileAllPath = rspData.getFileAllPath();
        //afe上传文件的文件传输代码
        String fileTransCode = rspData.getFileTransCode();
        FileUploadRspVo rspVo = new FileUploadRspVo();
        if (!fileAllPath.startsWith("/")) {
            fileAllPath = "/" + fileAllPath;
        }
        rspVo.setUrl(fileAllPath);
        rspVo.setTranCode(fileTransCode);
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
