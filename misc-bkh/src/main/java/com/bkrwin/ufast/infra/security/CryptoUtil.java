package com.bkrwin.ufast.infra.security;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptoUtil
{
  private static final String KEY_MD5 = "MD5";
  private static final String KEY_SHA = "SHA";
  public static final String KEY_MAC = "HmacSHA1";
  public static final String KEY_MAC_256 = "HmacSHA256";
  public static final String ALGORITHM = "AES";
  
  public static byte[] decryptBASE64(String key)
    throws Exception
  {
    return new BASE64Decoder().decodeBuffer(key);
  }
  
  public static String decryptBASE642String(String key)
    throws Exception
  {
    return new String(new BASE64Decoder().decodeBuffer(key));
  }
  
  public static String encryptBASE64(byte[] key)
    throws Exception
  {
    return new BASE64Encoder().encodeBuffer(key);
  }
  
  public static String encryptBASE64String(String key)
    throws Exception
  {
    return new BASE64Encoder().encodeBuffer(key.getBytes());
  }
  
  public static byte[] encryptMD5(byte[] data)
    throws Exception
  {
    MessageDigest md5 = MessageDigest.getInstance("MD5");
    md5.update(data);
    
    return md5.digest();
  }
  
  public static byte[] encryptSHA(byte[] data)
    throws Exception
  {
    MessageDigest sha = MessageDigest.getInstance("SHA");
    sha.update(data);
    
    return sha.digest();
  }
  
  public static String initMacKey()
    throws Exception
  {
    KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA1");
    SecretKey secretKey = keyGenerator.generateKey();
    return encryptBASE64(secretKey.getEncoded());
  }
  
  public static byte[] encryptHMAC(byte[] data, String key)
    throws Exception
  {
    SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), "HmacSHA1");
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    mac.init(secretKey);
    return mac.doFinal(data);
  }
  
  public static String encryptHMAC(String text, String key)
    throws Exception
  {
    SecretKey secretKey = new SecretKeySpec(decryptBASE64(key), "HmacSHA1");
    Mac mac = Mac.getInstance(secretKey.getAlgorithm());
    mac.init(secretKey);
    byte[] cippher = mac.doFinal(text.getBytes("UTF-8"));
    return encryptBASE64(cippher);
  }
  
  public static String byteArrayToHexString(byte[] b)
  {
    StringBuilder hs = new StringBuilder();
    for (int n = 0; (b != null) && (n < b.length); n++)
    {
      String stmp = Integer.toHexString(b[n] & 0xFF);
      if (stmp.length() == 1) {
        hs.append('0');
      }
      hs.append(stmp);
    }
    return hs.toString().toLowerCase();
  }
  
  public static String encryptHMACSha256(String message, String secret)
    throws Exception
  {
    String hash = "";
    Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
    SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
    sha256_HMAC.init(secret_key);
    byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
    hash = byteArrayToHexString(bytes);
    return hash;
  }
  
  private static Key toKey(byte[] key)
    throws Exception
  {
    SecretKey secretKey = null;
    if (("AES".equals("DES")) || ("AES".equals("DESede")))
    {
      DESKeySpec dks = new DESKeySpec(key);
      SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("AES");
      secretKey = keyFactory.generateSecret(dks);
    }
    else
    {
      secretKey = new SecretKeySpec(key, "AES");
    }
    return secretKey;
  }
  
  public static byte[] decrypt(byte[] data, String key)
    throws Exception
  {
    Key k = toKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(2, k);
    return cipher.doFinal(data);
  }
  
  public static byte[] encrypt(byte[] data, String key)
    throws Exception
  {
    Key k = toKey(decryptBASE64(key));
    Cipher cipher = Cipher.getInstance("AES");
    cipher.init(1, k);
    return cipher.doFinal(data);
  }
  
  public static String initKey()
    throws Exception
  {
    return initKey(null);
  }
  
  public static String initKey(String seed)
    throws Exception
  {
    SecureRandom secureRandom = null;
    if (seed != null) {
      secureRandom = new SecureRandom(decryptBASE64(seed));
    } else {
      secureRandom = new SecureRandom();
    }
    KeyGenerator kg = KeyGenerator.getInstance("AES");
    kg.init(secureRandom);
    SecretKey secretKey = kg.generateKey();
    return encryptBASE64(secretKey.getEncoded());
  }
  
  public static void main(String[] args)
  {
    try
    {
      String s = "这是一个伟大的开端";
      String b = encryptBASE64(s.getBytes("UTF-8"));
      System.out.println("BASE64加密后:" + b);
      byte[] c = decryptBASE64(b);
      System.out.println("BASE64解密后:" + new String(c, "UTF-8"));
      
      c = encryptMD5(s.getBytes());
      System.out.println("MD5   加密后:" + new BigInteger(c).toString(16));
      
      c = encryptSHA(s.getBytes());
      System.out.println("SHA   加密后:" + new BigInteger(c).toString(16));
      
      String key = initMacKey();
      System.out.println("HMAC密匙:" + key);
      c = encryptHMAC(s.getBytes(), key);
      System.out.println("HMAC  加密后:" + new BigInteger(c).toString(16));
      
      key = initKey();
      System.out.println("AES密钥:\t" + key);
      c = encrypt(s.getBytes("UTF-8"), key);
      System.out.println("AES   加密后:" + new BigInteger(c).toString(16));
      c = decrypt(c, key);
      System.out.println("AES   解密后:" + new String(c, "UTF-8"));
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
