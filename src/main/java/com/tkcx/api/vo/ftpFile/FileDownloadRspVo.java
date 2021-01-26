package com.tkcx.api.vo.ftpFile;

import com.tkcx.api.vo.base.ApiResponseVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 
 * @author wangjunhai
 * @date: 2019年3月13日
 */
@Getter
@Setter
@ToString
public class FileDownloadRspVo extends ApiResponseVo {

	/**
	 * 下载地址
	 */
	private String url;

	public void withMap(Map<String, Object> map) {
	}
}
