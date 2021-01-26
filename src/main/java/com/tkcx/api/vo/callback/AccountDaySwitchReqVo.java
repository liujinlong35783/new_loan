package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
   * 日切请求
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public class AccountDaySwitchReqVo extends ServiceRequestVo {

	/**
	   * 系统标识
	 */
	private String sysId;

	/**
	   * 账务日期
	 */
	private String acctDt;

	/**
	   * 日切批次
	 * 01-日切准备,02-日切通知,03-账务核算核对,04-日切结束通知
	 */
	private String dayCutNo;

	/**
	   * 交易类型
	 * start-启动  check-检查
	 */
	private String txnTp;
	
	/**
	   * 日切类型
	 * 01-会计日切  02-交易日切
	 */
	private String dayCutTp;

	@Override
	public void withMap(Map<String, Object> map) {
		this.sysId = StringUtils.trim((String) map.get("CnsmrSysId"));
		this.acctDt = StringUtils.trim((String) map.get("AcctDt"));
		this.dayCutNo = StringUtils.trim((String) map.get("DayCutNo"));
		this.txnTp = StringUtils.trim((String) map.get("TxnTp"));
		this.dayCutTp = StringUtils.trim((String) map.get("DayCutTp"));
	}

}
