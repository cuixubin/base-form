package com.dlshouwen.core.base.utils;

import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 安全工具类
 * @author 大连首闻科技有限公司
 * @version 2013-7-13 15:18:31
 */
public class SecurityUtils {

	// ****************************************************
	// Base64方案
	// ****************************************************

	/**
	 * Base64加密
	 * <p>用于Base64加密，Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
	 * 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
	 * @param data 需要加密的字节数组
	 * @return 加密后的字符串
	 * @throws Exception 抛出全部异常
	 */
	public static String encryptBASE64(byte[] data) throws Exception {
		return (new BASE64Encoder()).encodeBuffer(data);
	}

	/**
	 * Base64解密
	 * <p>用于Base64解密，Base64内容传送编码被设计用来把任意序列的8位字节描述为一种不易被人直接识别的形式。
	 * 常见于邮件、http加密，截取http信息，你就会发现登录操作的用户名、密码字段通过BASE64加密的。
	 * @param data 需要解密的字符串
	 * @return 解密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] decryptBASE64(String data) throws Exception {
		return (new BASE64Decoder()).decodeBuffer(data);
	}

	// ****************************************************
	// MD5方案
	// ****************************************************

	/**
	 * MD5加密
	 * <p>用于MD5加密。MD5——message-digest algorithm 5 （信息-摘要算法）缩写，广泛用于加密和解密技术，常用于文档校验。
	 * @param data 需要加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] encryptMD5(byte[] data) throws Exception {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(data);
		return md5.digest();
	}

	// ****************************************************
	// SHA方案
	// ****************************************************

	/**
	 * SHA加密
	 * <p>用于SHA加密。SHA(Secure Hash
	 * Algorithm，安全散列算法），数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。
	 * 虽然SHA与MD5通过碰撞法都已经被破解，但SHA仍是公认较MD5更为安全的加密算法。
	 * @param data 需要加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] encryptSHA(byte[] data) throws Exception {
		MessageDigest sha = MessageDigest.getInstance("SHA1");
		sha.update(data);
		return sha.digest();
	}

	// ****************************************************
	// HMAC方案
	// ****************************************************

	/**
	 * 初始化HMAC密钥
	 * <p>用于初始化HMAC密钥。HMAC(Hash Message Authentication
	 * Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
	 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
	 * 使用一个密钥生成一个固定大小的小数据块，并将其加入到消息中然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
	 * @return HMAC密钥
	 * @throws Exception 抛出全部异常
	 */
	public static String initMacKey() throws Exception {
		KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacMD5");
		SecretKey secretKey = keyGenerator.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * HMAC加密
	 * <p>用于HMAC加密。HMAC(Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
	 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
	 * 使用一个密钥生成一个固定大小的小数据块，并将其加入到消息中然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
	 * @param data 需要加密的字节数组
	 * @param key HMAC密钥
	 * @return 加密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), "HmacMD5");
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}

	// ****************************************************
	// DES方案
	// ****************************************************

	/**
	 * 生成密钥
	 * <p> 用于生成DES密钥。DES——Data Encryption Standard，数据加密算法。是IBM公司于1975年研究成功并公开发表的。
	 * DES算法的入口参数有三个:Key、Data、Mode。其中Key为8个字节共64位，是DES算法的工作密钥，
	 * Data也为8个字节64位，是要被加密或被解密的数据。
	 * @param seed 密钥生成的基础信息
	 * @return DES密钥
	 * @throws Exception 抛出全部异常
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;
		if (seed != null)
			secureRandom = new SecureRandom(decryptBASE64(seed));
		else
			secureRandom = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(secureRandom);
		SecretKey secretKey = kg.generateKey();
		return encryptBASE64(secretKey.getEncoded());
	}

	/**
	 * 转换DES密钥
	 * <p>用于转换DES密钥。DES——Data Encryption Standard，数据加密算法。是IBM公司于1975年研究成功并公开发表的。
	 * DES算法的入口参数有三个:Key、Data、Mode。其中Key为8个字节共64位，是DES算法的工作密钥，
	 * Data也为8个字节64位，是要被加密或被解密的数据。
	 * @param key DES密钥
	 * @return DES转换后密钥
	 * @throws Exception 抛出全部异常
	 */
	public static Key toKey(byte[] key) throws Exception {
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(dks);
		return secretKey;
	}

	/**
	 * DES加密
	 * <p>用于DES加密。DES——Data Encryption Standard，数据加密算法。是IBM公司于1975年研究成功并公开发表的。
	 * DES算法的入口参数有三个:Key、Data、Mode。其中Key为8个字节共64位，是DES算法的工作密钥，
	 * Data也为8个字节64位，是要被加密或被解密的数据。
	 * @param data 需要解密的字节数组
	 * @param key DES密钥
	 * @return 加密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	/**
	 * DES解密
	 * <p>用于DES解密。DES——Data Encryption Standard，数据加密算法。是IBM公司于1975年研究成功并公开发表的。
	 * DES算法的入口参数有三个:Key、Data、Mode。其中Key为8个字节共64位，是DES算法的工作密钥，
	 * Data也为8个字节64位，是要被加密或被解密的数据。
	 * @param data 需要解密的字节数组
	 * @param key DES密钥
	 * @return 解密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] decrypt(byte[] data, String key) throws Exception {
		Key k = toKey(decryptBASE64(key));
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, k);
		return cipher.doFinal(data);
	}

	// ****************************************************
	// PEB方案
	// ****************************************************

	/**
	 * 随机字节初始化
	 * <p>用于生成随机数。PBE——Password-based encryption（基于密码加密）。其特点在于口令由用户自己掌管，
	 * 不借助任何物理媒体；采用随机数杂凑多重加密等方法保证数据的安全性。
	 * @return 随机字节
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] initRandomBytes() throws Exception {
		byte[] randomBytes = new byte[8];
		Random random = new Random();
		random.nextBytes(randomBytes);
		return randomBytes;
	}

	/**
	 * 转换PEB密钥
	 * <p>用于转换PEB密钥。PBE——Password-based encryption（基于密码加密）。其特点在于口令由用户自己掌管，
	 * 不借助任何物理媒体；采用随机数杂凑多重加密等方法保证数据的安全性。
	 * @param key 需要转换的PEB密钥
	 * @return PEB转换后密钥
	 * @throws Exception 抛出全部异常
	 */
	private static Key toKey(String key) throws Exception {
		PBEKeySpec keySpec = new PBEKeySpec(key.toCharArray());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWITHMD5andDES");
		SecretKey secretKey = keyFactory.generateSecret(keySpec);
		return secretKey;
	}

	/**
	 * PEB加密
	 * <p>用于PEB加密。PBE——Password-based encryption（基于密码加密）。其特点在于口令由用户自己掌管，
	 * 不借助任何物理媒体；采用随机数杂凑多重加密等方法保证数据的安全性。
	 * @param data 需要加密的字节数组
	 * @param key PEB密钥
	 * @param randomBytes 随机字节数组
	 * @return 加密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] encrypt(byte[] data, String key, byte[] randomBytes)
			throws Exception {
		PBEParameterSpec paramSpec = new PBEParameterSpec(randomBytes, 100);
		Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
		cipher.init(Cipher.ENCRYPT_MODE, toKey(key), paramSpec);
		return cipher.doFinal(data);
	}

	/**
	 * PEB解密
	 * <p>用于PEB解密。PBE——Password-based encryption（基于密码加密）。其特点在于口令由用户自己掌管，
	 * 不借助任何物理媒体；采用随机数杂凑多重加密等方法保证数据的安全性。
	 * @param data 需要解密的字节数组
	 * @param key PEB密钥
	 * @param randomBytes 随机字节数组
	 * @return 解密后的字节数组
	 * @throws Exception 抛出全部异常
	 */
	public static byte[] decrypt(byte[] data, String key, byte[] randomBytes)
			throws Exception {
		PBEParameterSpec paramSpec = new PBEParameterSpec(randomBytes, 100);
		Cipher cipher = Cipher.getInstance("PBEWITHMD5andDES");
		cipher.init(Cipher.DECRYPT_MODE, toKey(key), paramSpec);
		return cipher.doFinal(data);
	}

	// ****************************************************
	// 抽取方法
	// ****************************************************

	/* 十六进制基础字符列表 */
	private static final char[] HEX_DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * 格式化字节数组为字符串
	 * <p>用于格式化字节数组为字符串
	 * @param bytes 需要格式化的字节数组
	 * @return 格式化后的字符串
	 * @throws Exception 抛出全部异常
	 */
	private static String getFormattedText(byte[] bytes) throws Exception {
		int len = bytes.length;
		StringBuilder buf = new StringBuilder(len * 2);
		for (int j = 0; j < len; j++) {
			buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
			buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
		}
		return buf.toString();
	}

	/**
	 * SHA加密
	 * <p>用于将字符串进行SHA加密，返回加密后字符串
	 * @param data 需要SHA加密的字符串
	 * @return 经SHA加密后的字符串
	 * @throws Exception 抛出全部异常
	 */
	public static String getSHA(String data) throws Exception {
		return getFormattedText(encryptSHA(data.getBytes()));
	}

	/**
	 * MD5加密
	 * <p>用于将字符串进行MD5加密，返回加密后字符串
	 * @param data 需要MD5加密的字符串
	 * @return 经MD5加密后的字符串
	 * @throws Exception 抛出全部异常
	 */
	public static String getMD5(String data) throws Exception {
		return getFormattedText(encryptMD5(data.getBytes()));
	}

	public static void main(String[] args) throws Exception {
		String data = "123456";
		System.err.println("原文:" + data);

		// Base64加密测试
		byte[] inputData = data.getBytes();
		String code = encryptBASE64(inputData);
		System.err.println("Base64加密后:" + code);
		byte[] output = decryptBASE64(code);
		String outputStr = new String(output);
		System.err.println("Base64解密后:" + outputStr);

		// MD5加密测试
		byte[] md5 = encryptMD5(inputData);
		System.err.println("MD5:" + getFormattedText(md5));

		// SHA加密测试
		byte[] sha = encryptSHA(inputData);
		System.err.println("SHA:" + getFormattedText(sha));

		// HMAC加密测试
		byte[] hmac = encryptHMAC(inputData, data);
		System.err.println("HMAC:" + getFormattedText(hmac));

		// DES加密
		String key = initKey(null);
		System.err.println("原文:" + data);
		System.err.println("密钥:" + key);
		inputData = encrypt(inputData, key);
		System.err.println("加密后:" + encryptBASE64(inputData));
		byte[] outputData = decrypt(inputData, key);
		outputStr = new String(outputData);
		System.err.println("解密后:" + outputStr);

		// PBE加密
		byte[] input = data.getBytes();
		String password = "dlshouwen";
		System.err.println("密码: " + password);
		byte[] randomBytes = initRandomBytes();
		inputData = encrypt(input, password, randomBytes);
		System.err.println("加密后: " + encryptBASE64(inputData));
		output = decrypt(inputData, password, randomBytes);
		outputStr = new String(output);
		System.err.println("解密后: " + outputStr);

		System.err.println("MD5: " + getMD5(data));
		System.err.println("SHA: " + getSHA(data));
	}

}
