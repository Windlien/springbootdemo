package com.example.springbootdemo.utils;

import java.io.InputStream;
import java.net.URL;

public class UrlUtil {

	public static URL getUrl(String name) {
		URL path = Thread.currentThread().getContextClassLoader().getResource(name);
		if(null==path) {
			path=UrlUtil.class.getResource(name);
		}
		return path;
	}
	
	public static InputStream getInputStream(String name) {
		InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		if(null==in) {
			in=UrlUtil.class.getResourceAsStream(name);
		}
		return in;
	}
}
