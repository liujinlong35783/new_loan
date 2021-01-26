package com.tkcx.api.vo.base;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

/**
 * 系统头
 * 
 * @author linghujie
 *
 */
@Getter
@Setter
@ToString
@XStreamAlias("SysHead")
public class SysHeadVo {
	
	/**
	 * 服务代码
	 * 服务唯一标识，由服务治理组提供
	 */
	@XStreamAlias("SvcCd")
	private String svcCd;
	
	/**
	 * 服务场景
	 * 描述每个服务的应用场景，由服务治理组提供,具体在每个服务中说明，如果该服务只有一种使用场景默认值必须为：01
	 */
	@XStreamAlias("SvcScn")
	private String svcScn;
	
	/**
	 * 服务调用方系统编号
	 * 服务请求系统编号；类型见：应用系统编码列表
	 */
	@XStreamAlias("CnsmrSysId")
	private String cnsmrSysId;
	
	/**
	 * 交易日期
	 * 请求时：服务请求系统的日期，格式为YYYY-MM-DD，无掩码
	 * 响应时：应答系统(服务提供系统)返回的日期，格式为YYYY-MM-DD，无掩码
	 */
	@XStreamAlias("TxnDt")
	private String txnDt;
	
	/**
	 * 交易时间
	 * 请求时：服务请求系统的时间，格式为HHMMSS，无掩码
	 * 响应时：应答系统(服务提供系统)返回的时间，格式为HHMMSS，无掩码
	 */
	@XStreamAlias("TxnTm")
	private String txnTm;
	
	/**
	 * 服务调用方系统流水号
	 * 服务请求系统发起每笔报文时的流水号，是每笔报文的唯一标识 
	 */
	@XStreamAlias("CnsmrSeqNo")
	private String cnsmrSeqNo;
	
	/**
	 * 全局业务流水号
	 * 业务源端请求系统的业务流水号,也称为源端流水号，用来唯一标识一笔业务
	 */
	@XStreamAlias("OrigCnsmrSeqNo")
	private String origCnsmrSeqNo;
	
	/**
	 * 服务提供方系统编号
	 * 服务提供系统编号，类型见：应用系统编码列表
	 */
	@XStreamAlias("SvcSplrSysId")
	private String svcSplrSysId;
	
	/**
	 * 服务提供方流水号
	 * 服务提供系统的交易流水号
	 */
	@XStreamAlias("SvcSplrSeqNo")
	private String svcSplrSeqNo;
	
	/**
	 * 渠道类型
	 * 服务请求系统渠道类型，由数据标准组确定具体码值
	 */
	@XStreamAlias("TxnChnlTp")
	private String txnChnlTp;
	
	/**
	 * 服务原发起方服务器标识
	 * 业务源端请求系统的服务器标识号，在报文中放IP地址
	 */
	@XStreamAlias("OrigCnsmrSvrId")
	private String origCnsmrSvrId;
	
	/**
	 * 交易返回状态
	 * 取值范围：S－系统处理成功 F－系统处理失败
	 */
	@XStreamAlias("TxnSt")
	private String txnSt;
	
	/**
	 * 交易返回代码
	 * 后台系统对业务错误代码的定义
	 */
	@XStreamAlias("RetCd")
	private String retCd;
	
	/**
	 * 交易返回信息
	 * 后台系统对业务错误的定义
	 */
	@XStreamAlias("RetMsg")
	private String retMsg;
	
	/**
	 * 会计日期
	 * 请求时：服务请求方记账日期
	 * 响应时：服务提供方记账日期
	 */
	@XStreamAlias("AcgDt")
	private String acgDt;
	
    public SysHeadVo() {
        Date now = new Date();
        this.txnDt = FastDateFormat.getInstance("yyyy-MM-dd").format(now);
        this.txnTm = FastDateFormat.getInstance("HHmmss").format(now);
    }
}
