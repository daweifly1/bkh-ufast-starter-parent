package com.bkrwin.ufast.infra.util;

public class StringUtil
{
  public static boolean isNullOrEmpty(String str)
  {
    if (str == null) {
      return true;
    }
    if (str.isEmpty()) {
      return true;
    }
    return false;
  }
  
  public static boolean isNullOrSpace(String str)
  {
    if (str == null) {
      return true;
    }
    return isNullOrEmpty(str.trim());
  }
}
