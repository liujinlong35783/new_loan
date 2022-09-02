package com.tkcx.api.service;

import com.tkcx.api.vo.ZhqdQueryReqVo;
import com.tkcx.api.vo.ftpFile.FileDownloadReqVo;
import com.tkcx.api.vo.ftpFile.FileDownloadRspVo;
import com.tkcx.api.vo.ftpFile.FileUploadReqVo;
import com.tkcx.api.vo.ftpFile.FileUploadRspVo;
import common.core.exception.ApplicationException;


/**
 * 行内文件系统上传下载接口
 * 
 * @author wangjunhai
 * @date: 2019年1月31日
 */
public interface FtpClientService {

	/**
	 * 上传单个文件
	 * 
	 * @param fileUploadReqVo 上传文件的临时路径
	 * @return
	 * @throws ApplicationException
	 */
	public FileUploadRspVo ftpFileUpload(FileUploadReqVo fileUploadReqVo, ZhqdQueryReqVo queryReq) throws ApplicationException;

	/**
	 * 通过传输码下载文件
	 * 
	 * @param fileDownloadReqVo 下载文件的请求参数
	 * @return
	 * @throws ApplicationException
	 */
	public FileDownloadRspVo ftpFileDownload(FileDownloadReqVo fileDownloadReqVo) throws ApplicationException;
}
