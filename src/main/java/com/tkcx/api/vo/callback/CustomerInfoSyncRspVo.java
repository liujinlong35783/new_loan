package com.tkcx.api.vo.callback;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * 客户信息修改同步响应
 * 
 * @author linghujie
 *
 */

@Getter
@Setter
@ToString
public class CustomerInfoSyncRspVo extends ServiceResponseVo {

	@Override
	public Map<String, Object> toMap() {

		Map<String, Object> map = new HashMap<>();
		map.put("State", "success");
		return map;
	}

}
