package com.dlshouwen.core.base.utils;

import java.net.*;
import java.util.*;
import java.security.*;

/**
 * GUID工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-13 15:18:19
 */
public class GUID {

	/** 原GUID值 */
	public String valueBeforeMD5 = "";

	/** 经过MD5加密后的GUID值 */
	public String valueAfterMD5 = "";

	/** 随机对象 */
	private static Random random;

	/** 安全码随机生成对象 */
	private static SecureRandom secureRand;

	/** 本机Host地址 */
	private static String host;

	/**
	 * 初始化实例对象
	 */
	static {
		secureRand = new SecureRandom();
		long secureInitializer = secureRand.nextLong();
		random = new Random(secureInitializer);
		try {
			host = InetAddress.getLocalHost().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 构造方法生成GUID值，使用未加密随机数
	 */
	public GUID() {
		getRandomGUID(false);
	}

	/**
	 * 构造方法生成GUID值
	 * @param secure 是否使用加密随机数
	 */
	public GUID(boolean secure) {
		getRandomGUID(secure);
	}

	/**
	 * 生成GUID值
	 * <p>生成GUID值，并使用MD5加密
	 * @param secure 是否使用加密随机数
	 */
	private void getRandomGUID(boolean secure) {
		MessageDigest md5 = null;
		StringBuffer sbValueBeforeMD5 = new StringBuffer();
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error: " + e);
		}
		try {
			long time = System.currentTimeMillis();
			long rand = secure ? secureRand.nextLong() : random.nextLong();
			sbValueBeforeMD5.append(host);
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(time));
			sbValueBeforeMD5.append(":");
			sbValueBeforeMD5.append(Long.toString(rand));
			valueBeforeMD5 = sbValueBeforeMD5.toString();
			md5.update(valueBeforeMD5.getBytes());
			byte[] array = md5.digest();
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < array.length; ++j) {
				int b = array[j] & 0xFF;
				if (b < 0x10)
					sb.append('0');
				sb.append(Integer.toHexString(b));
			}
			valueAfterMD5 = sb.toString();
		} catch (Exception e) {
			System.out.println("Error:" + e);
		}
	}

	/**
	 * 返回GUID
	 * <p>用于返回GUID字符串
	 * @return GUID字符串
	 */
	public String toString() {
		return valueAfterMD5;
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			System.out.println(new GUID().toString());
		}
	}

}
