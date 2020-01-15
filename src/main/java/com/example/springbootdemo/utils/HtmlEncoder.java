package com.example.springbootdemo.utils;

import org.apache.log4j.Logger;

/**
 * 
 * 类说明：对字符串进行HTML编码，可以在页面上显示正确的字符串值<br>
 * 例如: & 会被编码为 &amp; 创建时间：2006-2-22<br>
 * 
 * @author 李涛<br>
 * @email:oofrank@163.com<br>
 * 							Copyright 1999,2004 The Apache Software
 *                            Foundation.
 */
public class HtmlEncoder {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(HtmlEncoder.class);

	private static final String[] htmlCode = new String[256];

	static {
		for (int i = 0; i < 10; i++) {
			htmlCode[i] = "&#00" + i + ";";
		}

		for (int i = 10; i < 32; i++) {
			htmlCode[i] = "&#0" + i + ";";
		}

		for (int i = 32; i < 128; i++) {
			htmlCode[i] = String.valueOf((char) i);
		}

		// Special characters
		htmlCode[' '] = "&nbsp;";
		htmlCode['\n'] = "<BR>\n";
		htmlCode['\"'] = "&quot;"; // double quote
		htmlCode['\''] = "&acute;"; // double quote
		htmlCode['&'] = "&amp;"; // ampersand
		htmlCode['<'] = "&lt;"; // lower than
		htmlCode['>'] = "&gt;"; // greater than

		for (int i = 128; i < 256; i++) {
			htmlCode[i] = "&#" + i + ";";
		}
	}

	public static String encode(Object source) {
		if (source == null) {
			return null;
		} else {
			return encode(source.toString());
		}
	}

	/**
	 * <p>
	 * Encode the given text into html.
	 * </p>
	 * 
	 * @param source
	 *            the text to encode
	 * @return the encoded string
	 * 
	 */
	public static String encode(String source) {
		if (source == null) {
			return source;
		}
		int n = source.length();
		char character;
		StringBuffer buffer = new StringBuffer();
		// loop over all the characters of the String.
		for (int i = 0; i < n; i++) {
			character = source.charAt(i);
			// the Htmlcode of these characters are added to a StringBuffer one
			// by one
			try {
				buffer.append(htmlCode[character]);
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				buffer.append(character);
			}
		}
		return buffer.toString();
	}

	public static void main(String[] args) {
		System.out.println("main(String[]) - " + HtmlEncoder.encode("0'<dd>&&")); //$NON-NLS-1$ //$NON-NLS-2$
		System.out.println("main(String[]) - " + '0'); //$NON-NLS-1$
	}

}
