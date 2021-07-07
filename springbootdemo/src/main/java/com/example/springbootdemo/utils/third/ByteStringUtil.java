package com.example.springbootdemo.utils.third;

import java.util.HashMap;

public class ByteStringUtil {

	private static String hexStr = "0123456789ABCDEF";
	private static String[] binaryArray = { "0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011",
			"1100", "1101", "1110", "1111" };
	private final static HashMap<String, String> binaryMap = (HashMap<String, String>) new HashMap<String, String>();
	static {
		for (int i = 0; i < binaryArray.length; i++) {
			binaryMap.put(binaryArray[i], hexStr.substring(i, i + 1));
		}
	}

	public static String toBinary(String hex, boolean hexSplit, boolean letterSplit) {
		StringBuffer r = new StringBuffer();
		for (int i = 0; i < hex.length(); i++) {
			r.append(binaryArray[hexStr.indexOf(hex.charAt(i))]);

			if (letterSplit && i % 2 == 1) {
				r.append(';');
			} else {
				if (hexSplit) {
					r.append(' ');
				}
			}
		}
		return r.toString();
	}

	public static String toHex(String binary) {
		return binaryMap.get(binary);
	}

	/**
	 * 
	 * 字符串转十六行制字符串
	 * 
	 * @param b
	 * 
	 * @return
	 * 
	 */

	public static String byte2hex(byte[] b) {

		String hs = "";

		String stmp = "";

		for (int n = 0; n < b.length; n++) {

			stmp = (Integer.toHexString(b[n] & 0XFF));

			if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }

		}

		return hs.toUpperCase();

	}

	/**
	 * 十六行制字符串的bytes转原始字符串数组
	 * 
	 * @param b
	 * @return
	 */
	public static byte[] hex2byte(byte[] b) {

		if ((b.length % 2) != 0) {
			throw new IllegalArgumentException("长度不是偶数");
		}

		byte[] b2 = new byte[b.length / 2];

		for (int n = 0; n < b.length; n += 2) {

			String item = new String(b, n, 2);

			b2[n / 2] = (byte) Integer.parseInt(item, 16);

		}

		return b2;
	}

	public static boolean isHexString(String src) {
		for (int i = 0; i < src.length(); i++) {
			if ("abcdef0123456789ABCDEF".indexOf(src.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}

}
