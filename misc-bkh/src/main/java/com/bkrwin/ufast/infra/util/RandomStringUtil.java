package com.bkrwin.ufast.infra.util;

import com.bkrwin.ufast.infra.security.CryptoUtil;
import java.security.SecureRandom;

public class RandomStringUtil
{
  public static String RandomString(int length)
  {
    try
    {
      SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
      byte[] bytes = new byte[length / 2];
      random.nextBytes(bytes);
      return CryptoUtil.byteArrayToHexString(bytes);
    }
    catch (Exception e) {}
    return "";
  }
}
