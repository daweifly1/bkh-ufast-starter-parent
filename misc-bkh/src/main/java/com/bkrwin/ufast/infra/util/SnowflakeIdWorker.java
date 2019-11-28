package com.bkrwin.ufast.infra.util;

import com.bkrwin.ufast.infra.infra.log.LogHelper;

public class SnowflakeIdWorker
{
  private final long twepoch = 1483200000000L;
  private final long workerIdBits = 5L;
  private final long datacenterIdBits = 5L;
  private final long maxWorkerId = 31L;
  private final long maxDatacenterId = 31L;
  private final long sequenceBits = 12L;
  private final long workerIdShift = 12L;
  private final long datacenterIdShift = 17L;
  private final long timestampLeftShift = 22L;
  private final long sequenceMask = 4095L;
  private long workerId;
  private long datacenterId;
  private long sequence = 0L;
  private long lastTimestamp = -1L;
  
  public SnowflakeIdWorker(long workerId, long datacenterId)
  {
    if ((workerId > 31L) || (workerId < 0L)) {
      throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", new Object[] { Long.valueOf(31L) }));
    }
    if ((datacenterId > 31L) || (datacenterId < 0L)) {
      throw new IllegalArgumentException(String.format("datacenter Id can't be greater than %d or less than 0", new Object[] { Long.valueOf(31L) }));
    }
    this.workerId = workerId;
    this.datacenterId = datacenterId;
    LogHelper.monitor("======Snow falke id worker is initilized ====");
  }
  
  public synchronized long nextId()
  {
    long timestamp = timeGen();
    if (timestamp < this.lastTimestamp) {
      throw new RuntimeException(String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", new Object[] {Long.valueOf(this.lastTimestamp - timestamp) }));
    }
    if (this.lastTimestamp == timestamp)
    {
      this.sequence = (this.sequence + 1L & 0xFFF);
      if (this.sequence == 0L) {
        timestamp = tilNextMillis(this.lastTimestamp);
      }
    }
    else
    {
      this.sequence = 0L;
    }
    this.lastTimestamp = timestamp;
    
    return timestamp - 1483200000000L << 22 | this.datacenterId << 17 | this.workerId << 12 | this.sequence;
  }
  
  protected long tilNextMillis(long lastTimestamp)
  {
    long timestamp = timeGen();
    while (timestamp <= lastTimestamp) {
      timestamp = timeGen();
    }
    return timestamp;
  }
  
  protected long timeGen()
  {
    return System.currentTimeMillis();
  }
}
