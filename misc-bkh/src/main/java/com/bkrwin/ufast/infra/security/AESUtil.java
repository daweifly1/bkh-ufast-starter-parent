package com.bkrwin.ufast.infra.security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class AESUtil
{
  private static String sKey = "abcdef0123456789";
  private static String ivParameter = "0123456789abcdef";
  
  public static String encrypt(String sSrc)
  {
    String result = new String();
    try
    {
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      byte[] raw = sKey.getBytes();
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
      cipher.init(1, skeySpec, iv);
      byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
      result = new BASE64Encoder().encode(encrypted);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return result;
  }
  
  public static String decrypt(String sSrc)
  {
    String originalString = new String();
    try
    {
      byte[] raw = sKey.getBytes("ASCII");
      SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
      Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
      IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
      cipher.init(2, skeySpec, iv);
      byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);
      byte[] original = cipher.doFinal(encrypted1);
      originalString = new String(original, "utf-8");
    }
    catch (Exception ex)
    {
      ex.printStackTrace();
    }
    return originalString;
  }
}
