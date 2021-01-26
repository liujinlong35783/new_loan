package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 服务响应对象
 * 
 * @author linghujie
 *
 */
@Getter
@Setter
@ToString
public abstract class ServiceResponseVo {

	/**
	 * 返回代码
	 */
	private String retCd;

	/**
	 * 返回信息
	 */
	private String retMsg;

	/**
	 * 返回状态 S－系统处理成功 F－系统处理失败
	 */
	private String txnSt;

	/**
	 * 将对象转变成Map
	 */
	public abstract Map<String, Object> toMap();

}
