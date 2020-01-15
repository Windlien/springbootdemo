package com.example.springbootdemo.utils;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.*;

public class NetUtil {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(NetUtil.class);

	public final static String LOCAL_IP = StringUtil.collectionToCommaDelimitedString(getMyIp());

	public static List<String> getMyIp() {
		try {
			List<String> ipList = new LinkedList<String>();
			for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {
				NetworkInterface item = e.nextElement();
				for (Enumeration<InetAddress> e2 = item.getInetAddresses(); e2.hasMoreElements();) {
					InetAddress address = e2.nextElement();
					if (address instanceof java.net.Inet4Address && !address.isLoopbackAddress()) {
						ipList.add(address.getHostAddress());
					}
				}
			}
			Collections.sort(ipList);
			return ipList;
		} catch (Exception e) {
			logger.error("getMyIp()", e); //$NON-NLS-1$
			return new LinkedList<String>();
		}
	}

	public static Set<String> getMyMac(boolean upperCase) {
		try {
			HashSet<String> maces = new HashSet<String>();
			for (Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces(); e.hasMoreElements();) {
				NetworkInterface item = e.nextElement();
				byte[] hardwareAddress = item.getHardwareAddress();
				StringBuffer mac = new StringBuffer();
				for (int i = 0; hardwareAddress != null && i < hardwareAddress.length; i++) {
					if (i != 0) {
						mac.append('-');
					}
					String s = Integer.toHexString(hardwareAddress[i] & 0xFF);
					mac.append(s.length() == 1 ? "0" + s : s);
				}
				if (mac.length() > 0) {
					maces.add(upperCase ? mac.toString().toUpperCase() : mac.toString());
				}
			}
			return Collections.unmodifiableSet(maces);
		} catch (Exception e) {
			logger.error("getMyIp()", e); //$NON-NLS-1$
			e.printStackTrace();
			return new HashSet<String>();
		}
	}

	public static void main(String[] args) {
		System.out.println(getMyIp());
		System.out.println(getMyMac(true));
	}
}
