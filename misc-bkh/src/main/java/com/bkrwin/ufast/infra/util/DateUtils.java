package com.bkrwin.ufast.infra.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
  public static String getLastMonth()
  {
    Calendar cal = Calendar.getInstance();
    cal.add(2, -1);
    SimpleDateFormat dft = new SimpleDateFormat("yyyyMM");
    String lastMonth = dft.format(cal.getTime());
    return lastMonth;
  }
  
  public static Date getYestday()
  {
    Calendar cal = Calendar.getInstance();
    cal.add(5, -1);
    return cal.getTime();
  }
  
  public static long getCurrentHourSecond()
  {
    Calendar now = Calendar.getInstance();
    
    int year = now.get(1);
    int month = now.get(2);
    int day = now.get(5);
    int hour = now.get(11);
    
    now.set(year, month, day, hour, 0, 0);
    return now.getTimeInMillis() / 1000L;
  }
  
  public static long getCurrentDayMinute()
  {
    Calendar now = Calendar.getInstance();
    
    int year = now.get(1);
    int month = now.get(2);
    int day = now.get(5);
    
    now.set(year, month, day, 0, 0, 0);
    return now.getTimeInMillis() / 1000L / 60L;
  }
  
  public static String secToTime(int time)
  {
    String timeStr = null;
    int hour = 0;
    int minute = 0;
    int second = 0;
    if (time <= 0) {
      return "00:00";
    }
    minute = time / 60;
    if (minute < 60)
    {
      second = time % 60;
      timeStr = unitFormat(minute) + ":" + unitFormat(second);
    }
    else
    {
      hour = minute / 60;
      if (hour > 99) {
        return "99:59:59";
      }
      minute %= 60;
      second = time - hour * 3600 - minute * 60;
      timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
    }
    return timeStr;
  }
  
  public static String unitFormat(int i)
  {
    String retStr = null;
    if ((i >= 0) && (i < 10)) {
      retStr = "0" + Integer.toString(i);
    } else {
      retStr = "" + i;
    }
    return retStr;
  }
}
