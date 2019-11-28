package com.bkrwin.ufast.infra.infra.log;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import java.util.HashMap;
import java.util.Map;

public class FastLevelConverter
  extends ClassicConverter
{
  private static final Map<String, String> levelConvertMap = new HashMap();
  
  static
  {
    levelConvertMap.put("ERROR", "FATAL");
    levelConvertMap.put("WARN", "ERROR");
    levelConvertMap.put("INFO", "MONITOR");
  }
  
  public String convert(ILoggingEvent iLoggingEvent)
  {
    String levelString = iLoggingEvent.getLevel().toString();
    String convertLevel = (String)levelConvertMap.get(levelString);
    if (null != convertLevel) {
      return convertLevel;
    }
    return levelString;
  }
}
