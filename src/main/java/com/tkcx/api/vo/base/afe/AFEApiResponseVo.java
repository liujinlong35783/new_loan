package com.tkcx.api.vo.base.afe;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tkcx.api.vo.base.ServiceVo;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
   * 返回对象
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public abstract class AFEApiResponseVo {
	
	/**
	   * 请求服务对象
	 */
	@JsonIgnore
    private ServiceVo request;
	
	/**
	   * 响应服务对象
	 */
	@JsonIgnore
    private ServiceVo response;
    
    /**
             *本次交互在内部系统中的流水号
             *其值自来于请求服务对象
     */
    private String internalSN;
    
    /**
             *本次交互在外部系统中的流水号
             *其值来自于响应服务对象
     */
    private String externalSN;
    
    /**
	   * 本次交互在外部系统中的交易日期
	   * 其值来自于响应服务对象的SysHead，格式为YYYY-MM-DD，无掩码
	 */
	private String txnDt;
    
    /**
	   * 本次交互在外部系统中的交易时间
	   * 其值来自于响应服务对象的SysHead，格式为HHMMSS，无掩码
	 */
	private String txnTm;
	
	/**
	   * 本次交互在外部系统中的会计日期
	   * 其值来自于响应服务对象的SysHead，格式为YYYY-MM-DD，无掩码
	 */
	private String acgDt;
    
	
    /**
             * 从map中取出属性
     */
    public abstract void withMap(Map<String, Object> map);
    
    /**
     * 通信状态  “0”-成功 “1”-失败
     * 通信状态成功仅表示发送、接收、解析报文成功，不表示业务层面的成功
     * 
     */
    private String commStatus;
    
    /**
     * 业务状态 “0”-成功 “1”-失败 “2”-未知
     */
    private String busiStatus;
    
	/**
	 * 交易返回代码
	 */
    private String code;
    
    /**
	 * 交易返回消息
	 */
    private String msg;
}
