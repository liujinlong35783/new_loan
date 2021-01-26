package com.tkcx.api.vo.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 应用头
 * 
 * @author linghujie
 *
 */
@Getter
@Setter
@ToString
@XStreamAlias("AppHead")
public class AppHeadVo {
	
	/**
	 * 柜员号
	 * 服务请求系统柜员的唯一标识
	 */
	@XStreamAlias("TxnTlrId")
	private String txnTlrId;
	
	/**
	 * 机构代码
	 * 服务请求者（柜员）的机构归属
	 */
	@XStreamAlias("OrgId")
	private String orgId;
	
	/**
	 * 柜员密码
	 * 服务请求者的密码（对于ATMP等接入渠道使用核心系统分配的虚拟柜员）
	 */
	@XStreamAlias("TlrPwsd")
	private String tlrPwsd;
	
	/**
	 * 柜员级别
	 * 服务请求者的柜员级别
	 */
	@XStreamAlias("TlrLvl")
	private String tlrLvl;
	
	
	
    public AppHeadVo() {
        
    }
}
