package com.tkcx.api.service.imp;

import cn.hutool.core.date.DateUtil;
import com.tkcx.api.business.hjtemp.handle.HandleService;
import com.tkcx.api.business.hjtemp.model.HjFileInfoModel;
import com.tkcx.api.business.hjtemp.service.HjFileInfoService;
import com.tkcx.api.business.hjtemp.utils.FileUtil;
import com.tkcx.api.business.hjtemp.utils.HjFileFlagConstant;
import com.tkcx.api.exception.FileErrorCode;
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
    private QnFtpClientServiceImpl qnFtpClientServiceImpl;

    @Autowired
    private HjFileInfoService hjFileInfoService;

    @Value("${storage.tempUpload.path}")
    private String tempUploadPath;
    @Value("${storage.tempDownload.path}")
    private String tempDownloadPath;
    @Value("${storage.remote.path}")
    private String remotePath;
    private static final int FILE_NAME_LENGTH = 40;

    /**
     * 异步下载文件信息
     *
     * @param hjFileList
     * @throws ApplicationException
     */
    @Async
    public void downloadHjFile(List<HjFileInfoModel> hjFileList, Date fileDate) throws ApplicationException {

        for (HjFileInfoModel hjFileInfoModel : hjFileList) {
            FileDownloadReqVo fileVo = new FileDownloadReqVo();
            // 文件传输码
            fileVo.setDownloadTranCode(hjFileInfoModel.getFileTransCode());
            // 文件下载路径
            fileVo.setFilePath(hjFileInfoModel.getFilePath());
            // 下载文件
            String downFilePath = downloadFile(fileVo);
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

    /**
     * 保存互金下载路径
     *
     * @param downFilePath
     * @param hjFileInfoModel
     */
    private void saveHjFileDownloadPath(String downFilePath, HjFileInfoModel hjFileInfoModel){

        log.info("日期{}，文件类型:{}，下载路径：{}", hjFileInfoModel.getFileDate(), hjFileInfoModel.getFileType(), downFilePath);
        hjFileInfoModel.setFileDownloadPath(downFilePath);
        int totalNum = FileUtil.calTextLineNum(downFilePath);
        hjFileInfoModel.setFileLineTotalNum(totalNum);
        if(totalNum <= HjFileFlagConstant.READ_END_NUM_INITIAL){
            // 如果互金文件总行数小于501，则直接更新文件读取结束行数为文件的总行数
            hjFileInfoModel.setNextReadEndNum(totalNum);
        }

        hjFileInfoService.updateDownloadFile(hjFileInfoModel);
    }

    private String downloadFile(FileDownloadReqVo req) throws ApplicationException {
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

        // TODO 这里下载文件要改造成afe，即先访问前置系统

        File remoteFile = new File(remoteFilePath);
        String fileName = remoteFile.getName();
        String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1);

        String localFullPathFileName = downloadPath
                + RandomStringUtils.randomAlphanumeric(FILE_NAME_LENGTH).toLowerCase() + "." + fileExt;
        qnFtpClientServiceImpl.getFileStreamByTranCode(localFullPathFileName, remoteFilePath, downloadTranCode);

        return localFullPathFileName;
    }

}
