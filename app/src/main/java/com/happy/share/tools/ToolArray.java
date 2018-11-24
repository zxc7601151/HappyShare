package com.happy.share.tools;

import android.support.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * desc: 数组工具类 <br/>
 * time: 2017/11/10 下午3:16 <br/>
 * author: rooky <br/>
 * since V 1.0 <br/>
 */
public class ToolArray {

	/**
	 * 将数组转换成Map <br/>
	 *
	 * @param keysArr   Keys
	 * @param valuesArr Values
	 * @return Map集合
	 */
	@Nullable
	public static Map<String, String> createStringMap(String[] keysArr, String[] valuesArr) {
		if (keysArr == null || valuesArr == null || keysArr.length != valuesArr.length) {
			return null;
		}

		Map<String, String> paramsMap = new HashMap<>(keysArr.length);

		for (int i = 0; i < keysArr.length; i++) {
			paramsMap.put(keysArr[i], valuesArr[i]);
		}

		return paramsMap;
	}

}
