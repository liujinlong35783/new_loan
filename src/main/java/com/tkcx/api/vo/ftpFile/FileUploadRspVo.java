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
public class FileUploadRspVo extends ApiResponseVo {

	/**
	 * 下载地址
	 */
	private String url;

	/**
	 * 传输码
	 */
	private String tranCode;

	public void withMap(Map<String, Object> map) {
	}
}
