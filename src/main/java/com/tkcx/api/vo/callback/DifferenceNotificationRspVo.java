package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
   * 对账差错批量文件下载通知响应
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public class DifferenceNotificationRspVo extends ServiceResponseVo {

	@Override
	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<>();
//		map.put("State", "success");
		return map;
	}

}
