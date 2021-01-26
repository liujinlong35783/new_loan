package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * 客户信息修改同步请求
 * 由ECIF調用
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public class CustomerInfoSyncReqVo extends ServiceRequestVo {

	/**
	 * 客户编号
	 */
	private String prmyCstId;

	/**
	 * 客户名称
	 */
	private String cstChinNm;
	
	/**
	 * 证件类型
	 */
	private String identTp;
	
	/**
	 * 证件号码
	 */
	private String identId;
	
	/**
	 * 主手机号码
	 */
	private String mainMblNo;

	@Override
	public void withMap(Map<String, Object> map) {
		this.prmyCstId = StringUtils.trim((String) map.get("PrmyCstId"));
		this.cstChinNm = StringUtils.trim((String) map.get("CstChinNm"));
		this.identTp = StringUtils.trim((String) map.get("IdentTp"));
		this.identId = StringUtils.trim((String) map.get("IdentId"));
		this.mainMblNo = StringUtils.trim((String) map.get("MainMblNo"));
	}

}
