package com.example.springbootdemo.utils;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.FileWriterWithEncoding;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class FileUtil extends FileUtils {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(FileUtil.class);
	private static final String UDF_TEMP_DIR = System.getProperty("udf.user.home");

	/**
	 * 临时文件存放目录路径 System.getProperty("user.home") + File.separator + "jes" +
	 * File.separator + "temp" + File.separator;
	 */
	public static final String TEMP_PATH_NAME = "";


	private static MessageDigest MD5 = null;

	static {
		try {
			MD5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error("初始化MD5环境失败.", e);
		}
	}

	private static MessageDigest SHA256 = null;

	static {
		try {
			SHA256 = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			logger.error("初始化MD5环境失败.", e);
		}
	}

	public final static String getJesTempDir() {
		return TEMP_PATH_NAME;
	}

	public final static String getJesUserDir() {
		return StringUtil.isBlank(UDF_TEMP_DIR) ? System.getProperty("user.home") : UDF_TEMP_DIR;
	}

	public final static String getJesTempFileName(String ssId, String fileKey, boolean withPath) {
		String f = "tf_" + ssId + "_" + fileKey + ".temp";
		return withPath ? TEMP_PATH_NAME + f : f;
	}


	public final static String getTempFileName(String suffix, boolean withPath) throws IOException {
		String f = File.createTempFile("jes_", suffix, new File(TEMP_PATH_NAME)).getName();
		return withPath ? TEMP_PATH_NAME + f : f;
	}

	public final static String getTempDir(boolean withPath) throws IOException {
		File f = File.createTempFile("jes_", "", new File(TEMP_PATH_NAME));
		f.mkdir();
		return withPath ? TEMP_PATH_NAME + f.getName() : f.getName();
	}

	public static String getTempDirSon(String sonName) {
		String sonTempPathName = TEMP_PATH_NAME + sonName + File.separator;
		try {
			FileUtils.forceMkdir(new File(sonTempPathName));
		} catch (IOException e) {
			logger.error("创建TEMP_PATH_NAME:[" + sonTempPathName + "]失败.", e);
		}
		return sonTempPathName;
	}

	public static void copy(String source, String dest) {
		try {
			FileUtils.copyFile(new File(source), new File(dest));
		} catch (IOException e) {
			logger.error("copy(String, String) - source=" + source + ", dest=" + dest, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public static void copyToDir(String source, String destDir) {
		try {
			FileUtils.copyFileToDirectory(new File(source), new File(destDir));
		} catch (IOException e) {
			logger.error("copy(String, String) - source=" + source + ", destDir=" + destDir, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	public static void copyDir(String sourceDir, String destDir) {
		try {
			FileUtils.copyDirectory(new File(sourceDir), new File(destDir));
		} catch (IOException e) {
			logger.error("copy(String, String) - source=" + sourceDir + ", destDir=" + destDir, e); //$NON-NLS-1$ //$NON-NLS-2$
		}
	}

	/**
	 * @param dir
	 * @return 路径全名的list
	 */
	public static List<String> listFiles(String dir) {

		List<String> list = new LinkedList<String>();
		File[] files = new File(dir).listFiles();
		try {
			if (files == null) {
				FileUtils.forceMkdir(new File(dir));
				files = new File(dir).listFiles();
			}
		} catch (IOException e) {
		}
		if (files != null) {
			for (File f : files) {
				list.add(f.getPath());
			}
		}
		return list;
	}

	public static void deleteXMLFiles(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				File file = new File(dir, children[i]);
				if (file.toString().lastIndexOf(".xml") > 0) {
					file.delete();
				}
			}
		}
	}

	public static boolean deleteDir(String dirPath) {
		return deleteDir(new File(dirPath));
	}

	public static boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		// 目录此时为空，可以删除
		return dir.delete();
	}

	/**
	 * 使用当前操作系统目录分隔符处理Path字符串
	 *
	 * @param path
	 * @return
	 */
	public static String formatPathString(String path) {
		if (path == null) {
			return null;
		}
		return path.replace('/', File.separatorChar).replace('\\', File.separatorChar);
	}

	public static String getFileSHA256(String file) {
		return getFileSHA256(new File(file));
	}

	/**
	 * 对一个文件获取sha值
	 *
	 * @return sha串
	 */
	public static String getFileSHA256(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				SHA256.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (FileNotFoundException e) {
			logger.error("getFileSHA256(File)", e);
			return null;
		} catch (IOException e) {
			logger.error("getFileSHA256(File)", e);
			return null;
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				logger.error("getFileSHA256(File)", e);
			}
		}
	}

	public static String getFileMD5(String file) {
		return getMD5(new File(file));
	}

	/**
	 * 对一个文件获取md5值
	 *
	 * @return md5串
	 */
	public static String getMD5(File file) {
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			byte[] buffer = new byte[8192];
			int length;
			while ((length = fileInputStream.read(buffer)) != -1) {
				MD5.update(buffer, 0, length);
			}
			return new String(Hex.encodeHex(MD5.digest()));
		} catch (FileNotFoundException e) {
			logger.error("getMD5(File)", e);
			return null;
		} catch (IOException e) {
			logger.error("getMD5(File)", e);
			return null;
		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
			} catch (IOException e) {
				logger.error("getMD5(File)", e);
			}
		}
	}

	// 获得指定文件的byte数组
	public static byte[] getBytes(File file) {
		byte[] buffer = null;
		FileInputStream fis = null;
		ByteArrayOutputStream bos = null;
		try {
			fis = new FileInputStream(file);
			bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			bos.flush();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			logger.error("", e);
			return new byte[0];
		} catch (IOException e) {
			logger.error("", e);
			return new byte[0];
		} finally {
			IOUtil.closeQuietly(fis);
			IOUtil.closeQuietly(bos);
		}
		return buffer;
	}

	public static File saveMultipartFileToTempDir(MultipartFile file) {
		File tempFile = null;
		FileOutputStream fo = null;
		InputStream fi = null;
		try {
			tempFile = File.createTempFile("temp", file.getOriginalFilename(), new File(getJesTempDir()));
			fo = new FileOutputStream(tempFile);
			fi = file.getInputStream();
			IOUtil.copy(fi, fo);
		} catch (IOException e) {
			logger.error("创建临时文件失败:", e);
		} finally {
			IOUtil.closeQuietly(fo);
			IOUtil.closeQuietly(fi);
		}
		return tempFile;
	}

	public static void writeTextFile(String fileName, String charset, String content) throws IOException {
		Writer w = new FileWriterWithEncoding(fileName, charset);
		try {
			w.write(content);
		} finally {
			w.close();
		}
	}
	/**
	 * @param path
	 * @return 文件目录信息
	 */
	public static List<Map<String, String>> getFilesFromPath(String path) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		// 遍历获取文件目录信息
		for (File file : new File(path).listFiles()) {
			String filesName = file.getName();
			// 获取文件大小、日期、名称
			if (file.isFile()) {
				DecimalFormat df = new DecimalFormat("0.00");
				long filesize = file.length();
				// if (filesize != 0) { //由于EAST使用,0字节文件也显示
				Map<String, String> map = new HashMap<String, String>();
				map.put("filesize",
						(filesize > 1024)
								? (filesize / 1024) > 1024 ? df.format(filesize / 1048576.0) + " M" : df.format(filesize / 1024.0) + " KB"
								: String.valueOf(filesize) + " B");
				map.put("filesName", filesName);
				DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String filetime = format.format(new Date(file.lastModified())).toString();
				map.put("modifytime", filetime);
				String[] filetype = filesName.split("\\.");
				map.put("filetype", filetype[(filetype.length - 1)]);
				map.put("logfilepath", file.getAbsolutePath());
				list.add(map);
			}
		}
		return list;
	}

	/**
	 * 新建文件时,使用此方法创建其目录,支持多级目录直接创建
	 * 
	 * @param fullFileName
	 * @throws IOException
	 */
	public static void testAndMakeDir(String fullFileName) throws IOException {
		fullFileName = fullFileName.replace('\\', '/');
		if (fullFileName.lastIndexOf('/') > 0) {
			String pathName = fullFileName.substring(0, fullFileName.lastIndexOf('/'));
			File dir = new File(pathName);
			if (!dir.exists()) {
				FileUtils.forceMkdir(dir);
			}
		}
	}

	public static void main(String[] args) throws IOException {
		testAndMakeDir("E:/aaa/bbb/ccc/");
	}

}