package com.example.springbootdemo.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NumberUtil {
	private NumberUtil() {

	}

	public static boolean equalTo(double l, double r) {
		return Math.abs(l - r) < 0.000001d;
	}
	
	public static List<Map<String,Object>> getStrictNumerical4View(List<Map<String,Object>> source){
		if(source==null || source.isEmpty()) {
			return source;
		}
		
		List<Map<String,Object>> strictDatas=new ArrayList<Map<String,Object>>();
		for(Map<String,Object> row: source) {
			Map<String,Object> strictRow = new LinkedHashMap<String,Object>();
			for(Map.Entry<String, Object> entry : row.entrySet()) {
				if(entry.getValue()!=null && entry.getValue() instanceof Number) {
					if(entry.getValue() instanceof BigDecimal) {
						strictRow.put(entry.getKey(),  ((BigDecimal)entry.getValue()).toPlainString());
					}else if(entry.getValue() instanceof BigInteger) {
						strictRow.put(entry.getKey(),  ((BigInteger)entry.getValue()).toString());
					}else {
						strictRow.put(entry.getKey(),  StringUtil.doubleToStringNotEndWithZero(((Number)entry.getValue()).doubleValue(), 6));
					}
				}else {
					strictRow.put(entry.getKey(), entry.getValue());
				}
			}
			strictDatas.add(strictRow);
		}
		
		return strictDatas;
	}
	
}
