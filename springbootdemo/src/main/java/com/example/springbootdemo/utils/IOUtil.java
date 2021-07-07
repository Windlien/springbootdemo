package com.example.springbootdemo.utils;

import org.apache.commons.io.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 
 * 
 * @author CXF
 * 
 */
public abstract class IOUtil {
	public static int copy(InputStream input, OutputStream output) throws IOException {
		return IOUtils.copy(input, output);
	}

	public static void closeQuietly(Closeable closeable) {
		IOUtils.closeQuietly(closeable);
	}
}
