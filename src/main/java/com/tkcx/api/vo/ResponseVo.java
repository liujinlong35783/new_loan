package com.tkcx.api.vo;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseVo<T> {
	private Integer code;
	private String message;
	private T data;

    /**
     * 接口处理状态(成功)
     */
    public final static Integer CODE_SUCCESS = 0;
    /**
     * 接口处理状态(失败)
     */
    public final static Integer CODE_FAIL = 1;

	public ResponseVo() {
        this.code = CODE_SUCCESS;
		this.message = "success";
	}

	public ResponseVo(Integer code, String message) {
		this.code = code;
		this.message = StringUtils.isEmpty(message)?"未知异常，请检查日志!":message;
	}

	public ResponseVo(T data) {
        this.code = CODE_SUCCESS;
		this.message = "success";
		this.data = data;
	}

	public String toString() {
		return JSON.toJSONString(this);
	}
}
