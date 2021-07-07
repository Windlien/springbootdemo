package com.example.springbootdemo.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EnumUtils {
	@SuppressWarnings("rawtypes")
	public static List<Map<String, Object>> toList(Enum[] enumConstants) {
		List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
		for (Enum<?> en : enumConstants) {
			Map<String, Object> map = MapUtil.javaBeanToMap(en);
			map.put("name", en.name());
			list.add(map);
		}
		return list;
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, Map<String, Object>> toMap(Enum[] enumConstants) {
		Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();
		for (Enum<?> en : enumConstants) {
			Map<String, Object> map = MapUtil.javaBeanToMap(en);
			result.put(en.name(), map);
		}
		return result;
	}

	public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
		return getEnum(enumClass, enumName) != null;
	}

	public static <E extends Enum<E>> E getEnum(final Class<E> enumClass, final String enumName) {
		if (enumName == null) {
			return null;
		}
		try {
			return Enum.valueOf(enumClass, enumName);
		} catch (final IllegalArgumentException ex) {
			return null;
		}
	}
}
