package com.tkcx.api.vo.ftpFile;

import com.tkcx.api.vo.base.ApiRequestVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 下载文件数据格式
 * 
 * @author wangjunhai
 *
 */
@Getter
@Setter
@ToString
public class FileDownloadReqVo extends ApiRequestVo {
	/**
	 * 下载传输码
	 */
	private String downloadTranCode;

	/**
	 * 下载路径
	 */
	private String filePath;

	/**
	 * 是否清楚数据 1-清除，0-不清除
	 */
	private Integer isRemove = 0;

	@Override
	public Map<String, Object> toMap() {
		return null;
	}
}
