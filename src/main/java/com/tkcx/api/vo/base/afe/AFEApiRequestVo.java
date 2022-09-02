package com.tkcx.api.vo.base.afe;

import com.tkcx.api.vo.base.AppHeadVo;
import com.tkcx.api.vo.base.SysHeadVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 请求对象
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public abstract class AFEApiRequestVo {
	
	/**
	   * 请求系统头
	 */
	private SysHeadVo sysHead;
	
	/**
	   * 请求应用头
	 */
	private AppHeadVo appHead;

    /**
             * 将对象转换成map
     */
    public abstract Map<String, Object> toMap();
}
