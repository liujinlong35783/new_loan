package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
   * 对账差错批量文件下载通知请求
   *  由互金核算調用
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public class DifferenceNotificationReqVo extends ServiceRequestVo {

	/**
	 * 发起方系统标识
	 */
	private String sndSysFlg;

	/**
	 * 文件类型
	 */
	private String oprTp;

	/**
	 * 批次号
	 */
	private String batchNo;

	/**
	 * 文件路径
	 */
	private String filPath;

	/**
	 * 文件名称
	 */
	private String fileNm;

	/**
	 * 文件传输代码
	 */
	private String fileTrnsmCd;

	@Override
	public void withMap(Map<String, Object> map) {
		this.sndSysFlg = StringUtils.trim((String) map.get("SndSysFlg"));
		this.oprTp = StringUtils.trim((String) map.get("OprTp"));
		this.batchNo = StringUtils.trim((String) map.get("FILE_BCHNO"));
		this.filPath = StringUtils.trim((String) map.get("FILE_PTH_ADDR"));
		this.fileNm = StringUtils.trim((String) map.get("FILE_APLTN"));
		this.fileTrnsmCd = StringUtils.trim((String) map.get("FILE_TRNSM_CD"));
	}

}
