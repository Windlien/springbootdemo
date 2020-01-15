package com.example.springbootdemo.utils;

import org.springframework.ui.ModelMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class ServletUtil {
	public static void setExportName(HttpServletRequest request, HttpServletResponse response, String fileName) {
		String utf8FileName = null;
		try {
			utf8FileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			utf8FileName = fileName;
		}
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + utf8FileName + ";filename*=utf-8''" + utf8FileName);

	}
	public static String result4Download(ModelMap model, String msg) {
		model.put(
				"content",
				"<script>" + "Ext.require('Ext.ux.window.Notification');" + "Ext.onReady(function() {window.parent.jesAlert('"
						+ msg.replace('\'', '"') + "');});</script>");
		return "nojsp";
	}
}
