package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import com.tkcx.api.vo.base.AppHeadVo;
import com.tkcx.api.vo.base.SysHeadVo;

import java.util.Map;

/**
 * 服务请求对象
 * 
 * @author linghujie
 *
 */
@Getter
@Setter
@ToString
public abstract class ServiceRequestVo {

	/**
	 * 系统头
	 */
	private SysHeadVo sysHeadVo;

	/**
	 * 应用头
	 */
	private AppHeadVo appHeadVo;

	/**
	 * 从Map中取出属性
	 */
	public abstract void withMap(Map<String, Object> map);

}
