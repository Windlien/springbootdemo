package com.example.springbootdemo.utils.third;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class AESEncrypter {
   /**
    * 解密
    *
    * @param content
    *            待解密内容
    * @param key
    *            解密的密钥
    * @return
    * @throws Exception
    */
   public static String decrypt(String content, String key) throws Exception {
       if (content.length() < 1) {
           return null;
       }
       byte[] byteRresult = new byte[content.length() / 2];
       for (int i = 0; i < content.length() / 2; i++) {
           int high = Integer.parseInt(content.substring(i * 2, i * 2 + 1), 16);
           int low = Integer.parseInt(content.substring(i * 2 + 1, i * 2 + 2), 16);
           byteRresult[i] = (byte) (high * 16 + low);
       }
           KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            kgen.init(128,random);

           SecretKey secretKey = kgen.generateKey();
           byte[] enCodeFormat = secretKey.getEncoded();
           SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
           Cipher cipher = Cipher.getInstance("AES");
           cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
           byte[] result = cipher.doFinal(byteRresult);
           return new String(result);

   }

   /**
    * 解密
    * @param content
    *            待解密内容
    * @param key
    *            解密的密钥
    * @return
    * @throws NoSuchPaddingException
    * @throws NoSuchAlgorithmException
    */
   public static String encrypt(String content, String key) throws Exception {
            KeyGenerator kgen = KeyGenerator.getInstance("AES");

            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(key.getBytes());
            kgen.init(128,random);

           SecretKey secretKey = kgen.generateKey();
           byte[] enCodeFormat = secretKey.getEncoded();
           SecretKeySpec secretKeySpec = new SecretKeySpec(enCodeFormat, "AES");
           Cipher cipher = Cipher.getInstance("AES");
           byte[] byteContent = content.getBytes("utf-8");
           cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
           byte[] byteRresult = cipher.doFinal(byteContent);
           String sb = new String("");

           for (int i = 0; i < byteRresult.length; i++) {
               String hex = Integer.toHexString(byteRresult[i] & 0xFF);
               if (hex.length() == 1) {
                   hex = '0' + hex;
               }
               sb = sb.concat(hex.toUpperCase());
           }
           return sb;

   }


   public static void main(String[] args) throws Exception {
       String test="This is test.";
       String ens=encrypt(test, "aaa");
       System.out.println(ens);
       String des=decrypt(ens,"aaa");
       System.out.println(des);
   }
}
