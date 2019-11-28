package com.bkrwin.ufast.infra.infra.log;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPLogConfig
  extends ClassicConverter
{
  public String convert(ILoggingEvent iLoggingEvent)
  {
    try
    {
      return InetAddress.getLocalHost().getHostAddress();
    }
    catch (UnknownHostException e)
    {
      e.printStackTrace();
    }
    return null;
  }
}
