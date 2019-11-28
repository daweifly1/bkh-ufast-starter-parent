package com.bkrwin.ufast.infra.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MathUtil
{
  public static BigDecimal MathRount(BigDecimal value)
  {
    return new BigDecimal(Math.round(value.doubleValue()));
  }
  
  public static String MathRount(String value)
  {
    try
    {
      return Long.toString(Math.round(Double.parseDouble(value)));
    }
    catch (Exception exp) {}
    return "0";
  }
  
  public static double MathRount(double value)
  {
    return Math.round(value);
  }
  
  public static double MathRount(double value, int length)
  {
    return MathRount(new BigDecimal(value), length).doubleValue();
  }
  
  public static BigDecimal MathRount(BigDecimal value, int length)
  {
    return value.setScale(length, RoundingMode.HALF_EVEN);
  }
}
