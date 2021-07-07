package com.example.springbootdemo.utils;

import java.util.HashSet;
import java.util.Set;

public class SetUtil {
	private SetUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static Set<String> makeSet(String... ms) {
		Set<String> l = new HashSet<String>();
		for (int i = 0; i < ms.length; i++) {
			l.add(ms[i]);
		}
		return l;
	}
}
