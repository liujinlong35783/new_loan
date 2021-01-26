package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
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
public class AccountDaySwitchRspVo extends ServiceResponseVo {

	/**
	 * 交易类型
	 */
	private String txnTp;

	/**
	 * 日切批次
	 */
	private String dayCutNo;

	/**
	 * 结果代码
	 * 111111 失败，EEEEEE 异常，RRRRRR 正在运行，000000 成功
	 */
	private String rsltCd;

	/**
	 * 账务日期
	 */
	private String acctDt;
	
	/**
	   * 系统标识
	 */
	private String sysId;
	
	/**
	   * 日切类型
	 * 01-会计日切  02-交易日切
	 */
	private String dayCutTp;
//	//发起日期
//	private String sndDate;
//	//发起时间
//	private String sndTime;
//	//交易类型
//	private String tranType;
//	//发起日期
//	private String date;
//	//结果
//	private String result;
//	//结果描述
//	private String desc;
	

	@Override
	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<>();
		map.put("TxnTp", txnTp);
		map.put("DayCutNo", dayCutNo);
		map.put("RsltCd", rsltCd);
		map.put("AcctDt", acctDt);
		map.put("SysId", sysId);
		map.put("DayCutTp", dayCutTp);
//		map.put("SndDate", sndDate);
//		map.put("SndTime", sndTime);
//		map.put("TranType", tranType);
//		map.put("Date", date);
//		map.put("Result", result);
//		map.put("Desc", desc);
		return map;
	}

}
