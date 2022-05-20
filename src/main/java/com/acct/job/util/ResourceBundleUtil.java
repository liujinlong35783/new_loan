package com.acct.job.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ResourceBundle数据进行提取
 *
 * @author Tom
 * @date 2016年11月25日 上午10:13:19
 *
 * @version 1.0 2016年11月25日 Tom create
 * 
 * @copyright Copyright © 2016 广电运通 All rights reserved.
 */
public class ResourceBundleUtil {

	/**
	 * 获取ResourceBundle map
	 * 
	 * @return
	 */
	public static Map<String, Object> getBundleMap(ResourceBundle bundle) {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		Enumeration<String> keys = bundle.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = bundle.getString(key);
			mapResult.put(key, value);
		}
		return mapResult;
	}

}
