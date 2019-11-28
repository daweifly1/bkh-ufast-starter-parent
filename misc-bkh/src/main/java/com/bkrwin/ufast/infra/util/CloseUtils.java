package com.bkrwin.ufast.infra.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CloseUtils
{
  private static final Log logger = LogFactory.getLog(CloseUtils.class);
  
  public static void close(ResultSet rs)
  {
    if (rs != null) {
      try
      {
        rs.close();
      }
      catch (SQLException e)
      {
        logger.error("close ResultSet failed.", e);
      }
    }
  }
  
  public static void close(Statement stmt)
  {
    if (stmt != null) {
      try
      {
        stmt.close();
      }
      catch (SQLException e)
      {
        logger.error("close Statement failed.", e);
      }
    }
  }
  
  public static void close(PreparedStatement stmt)
  {
    if (stmt != null) {
      try
      {
        stmt.close();
      }
      catch (SQLException e)
      {
        logger.error("close PreparedStatement failed.", e);
      }
    }
  }
  
  public static void close(Connection connection)
  {
    if (connection != null) {
      try
      {
        connection.close();
      }
      catch (SQLException e)
      {
        logger.error("close Connection failed.", e);
      }
    }
  }
  
  public static void close(AutoCloseable... closes)
  {
    for (AutoCloseable closeable : closes) {
      if (closeable != null) {
        try
        {
          closeable.close();
        }
        catch (Exception e)
        {
          logger.error("close closeable failed.", e);
        }
      }
    }
  }
  
  public static void close(Socket psocket)
  {
    if (psocket != null) {
      try
      {
        psocket.close();
      }
      catch (IOException e)
      {
        logger.error("close socket failed.", e);
      }
    }
  }
  
  public static void close(ServerSocket psocket)
  {
    if (psocket != null) {
      try
      {
        psocket.close();
      }
      catch (IOException e)
      {
        logger.error("close socket failed.", e);
      }
    }
  }
  
  public static void close(Object... objs)
  {
    for (Object obj : objs) {
      if (obj != null) {
        try
        {
          Class<?> objClass = obj.getClass();
          Method meyhod = objClass.getMethod("close", new Class[0]);
          if (null != meyhod) {
            meyhod.invoke(obj, new Object[0]);
          } else {
            logger.error("This object can not be close, because this object don not have close method.");
          }
        }
        catch (Exception e)
        {
          logger.error("close Object failed.", e);
        }
      }
    }
  }
}
