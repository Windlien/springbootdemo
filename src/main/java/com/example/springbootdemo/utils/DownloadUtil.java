package com.example.springbootdemo.utils;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
public abstract class DownloadUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DownloadUtil.class);

	public static void makeResponseHeader(HttpServletResponse response, String fileName) {
		String utf8FileName = null;
		try {
			utf8FileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("UnsupportedEncodingException", e);
			utf8FileName = fileName;
		}
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + utf8FileName + ";filename*=utf-8''" + utf8FileName);

	}
}
