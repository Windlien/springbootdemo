package com.example.springbootdemo.utils.third;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *
 * @author jay
 * @time Dec 29, 20154:59:56 PM
 *
 */
public class JavaScript3DESUtil {

    /**
     * 生成密钥
     * 
     * @return
     */
    public static String generageKey() {
        String key = "";
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DESede");
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] bytesKey = secretKey.getEncoded();
            key = Base64.encode(bytesKey);
            System.out.println("key : " + key);
        } catch (NoSuchAlgorithmException e) {
        	throw new RuntimeException(e);
        }
        return key;
    }


    /**
     * 加密
     * @param str    待加密字符串
     * @param keyOfBase64    密钥
     * @return
     */
    public static String encrypt(String str, String keyOfBase64) {
        String returnStr = "";
        try {
            // key转换
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(Base64.decode(keyOfBase64));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(deSedeKeySpec);
            // 加密
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            IvParameterSpec param = new IvParameterSpec("01234567".getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, convertSecretKey,param);
            byte[] result = cipher.doFinal(str.getBytes());
            returnStr = Base64.encode(result);
        } catch (Exception e) {
        	throw new RuntimeException(e);
        }
        return returnStr;
    }


    /**
     * 解密
     * @param str    待解密字符串
     * @param keyOfBase64    密钥
     * @return
     */
    public static String decrypt(String str, String keyOfBase64) {
        String returnStr = "";
        try {
            // key转换
            DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(Base64.decode(keyOfBase64));
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DESede");
            Key convertSecretKey = factory.generateSecret(deSedeKeySpec);
            Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
            // 用密匙初始化Cipher对象
            IvParameterSpec param = new IvParameterSpec("01234567".getBytes());
            cipher.init(Cipher.DECRYPT_MODE, convertSecretKey,param);
            byte[] result = cipher.doFinal(Base64.decode(str));
//            System.out.println("decrypt : " + new String(result));
            returnStr = new String(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnStr;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {

//        String str = "hello 1111111111";
//        String key = generageKey();
//        String encryptStr = encrypt("Message","RGxMdmJsbHRSbE5GT0VyY1dLc2FJY1Fl");
//        System.out.println(encryptStr);
//        decrypt(encryptStr, key);

//        decrypt("njMj1tVuRha5ElrbAJ3CVm1QbZVBZM", "hmRSaD5MFs1ncJ30x2hDiiNRT6eXFc5D");
    	
    	String encryptied = encrypt("abcd",Base64.encode("__JES_DES_ENCRYPT_KEY1__".getBytes("utf-8")));
		System.out.println(encryptied);
    	System.out.println( decrypt(encryptied,Base64.encode("__JES_DES_ENCRYPT_KEY1__".getBytes("utf-8"))));

    }

}