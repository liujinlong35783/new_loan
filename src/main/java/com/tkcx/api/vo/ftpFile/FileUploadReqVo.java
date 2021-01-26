package com.tkcx.api.vo.ftpFile;

import com.tkcx.api.vo.base.ApiRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 上传文件数据格式
 * 
 * @author wangjunhai
 * @date: 2019年3月13日
 */
@Getter
@Setter
@ToString
public class FileUploadReqVo extends ApiRequestVo {
	/**
	 * 文件类型
	 */
	private String fileType;
	/**
	 * 文件名称
	 */
	private String fileName;
	/**
	 * 文件路径
	 */
	private String filePath;
	/**
	 * 文件远程存储路径
	 */
	private String remoteFullPath;

	@Override
	public Map<String, Object> toMap() {
		return null;
	}
}
