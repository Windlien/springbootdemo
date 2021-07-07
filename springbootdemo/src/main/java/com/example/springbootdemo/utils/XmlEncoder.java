package com.example.springbootdemo.utils;

import org.apache.log4j.Logger;


public class XmlEncoder {
	
	private static final Logger logger = Logger.getLogger(XmlEncoder.class);

	private static final String[] xmlCode = new String[256];

	static {
		for (int i = 0; i < 32; i++) {
			xmlCode[i] = "";
		}

		for (int i = 32; i < 256; i++) {
			xmlCode[i] = String.valueOf((char) i);
		}
		
		xmlCode[9] = String.valueOf((char) 9); 
		xmlCode[10] = String.valueOf((char) 10);
		xmlCode['\"'] = "&quot;"; 
		xmlCode['\''] = "&apos;"; 
		xmlCode['&'] = "&amp;"; 
		xmlCode['<'] = "&lt;"; 
		xmlCode['>'] = "&gt;"; 
		
	}

	/**
	 * <p>
	 * Encode the given text into xml
	 * </p>
	 * 
	 * @param string
	 *            the text to encode
	 * @return the encoded string
	 * 
	 */
	public static String encode(String string) {
		if(string == null){
			return null;
		}
		int n = string.length();
		char character;
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < n; i++) {
			character = string.charAt(i);
			try {
				buffer.append(xmlCode[character]);
			} catch (ArrayIndexOutOfBoundsException aioobe) {
				buffer.append(character);
			}
		}
		return buffer.toString().trim();
	}

	public static void main(String[] args) {
		System.out.println("main(String[]) - " + XmlEncoder.encode("LTD\0\0 1\n\t1")); //$NON-NLS-1$ //$NON-NLS-2$
		//System.out.println("main(String[]) - " + '0'); //$NON-NLS-1$
	}

}
