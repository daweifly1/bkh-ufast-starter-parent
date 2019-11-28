package com.bkrwin.ufast.infra.infra.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogHelper
{
  private static final Logger log = LoggerFactory.getLogger("ufast");
  private static final String split = ":";
  
  public static void error(String message, int errorCode)
  {
    if (log.isWarnEnabled()) {
      log.warn(errorCode + ":" + message);
    }
  }
  
  public static void fatal(String message, Throwable e)
  {
    log.error(message, e);
  }
  
  public static void debug(String message)
  {
    if (log.isDebugEnabled()) {
      log.debug(message);
    }
  }
  
  public static void monitor(String message)
  {
    if (log.isInfoEnabled()) {
      log.info(message);
    }
  }
}
