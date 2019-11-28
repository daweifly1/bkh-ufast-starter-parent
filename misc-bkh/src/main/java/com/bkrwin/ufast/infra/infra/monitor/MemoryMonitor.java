package com.bkrwin.ufast.infra.infra.monitor;

import com.bkrwin.ufast.infra.infra.log.LogHelper;

public abstract class MemoryMonitor
{
  private static final int MEGA = 1048576;
  
  public static void takeMemoryLog()
  {
    LogHelper.monitor(
      "Available processors (cores): " + Runtime.getRuntime().availableProcessors());
    
    LogHelper.monitor("Free memory (MB): " + 
      Runtime.getRuntime().freeMemory() / 1048576L);
    
    long maxMemory = Runtime.getRuntime().maxMemory();
    
    LogHelper.monitor("Maximum memory (MB): " + (maxMemory == Long.MAX_VALUE ? "no limit" : 
      Long.valueOf(maxMemory / 1048576L)));
    
    LogHelper.monitor("Total memory (MB): " + 
      Runtime.getRuntime().totalMemory() / 1048576L);
  }
  
  public abstract void scheduleMonitor();
}
