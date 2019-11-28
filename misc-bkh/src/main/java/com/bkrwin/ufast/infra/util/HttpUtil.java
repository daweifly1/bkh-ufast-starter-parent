package com.bkrwin.ufast.infra.util;

import com.bkrwin.ufast.infra.infra.log.LogHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;

public class HttpUtil
{
  public static <T> boolean addJsonBodyToResponse(HttpServletResponse response, T value)
  {
    try
    {
      ObjectMapper objectMapper = new ObjectMapper();
      String json = objectMapper.writeValueAsString(value);
      response.addHeader("content-type", "application/json;charset=utf-8");
      response.setCharacterEncoding("UTF-8");
      PrintWriter printWriter = response.getWriter();
      printWriter.write(json);
      printWriter.flush();
      printWriter.close();
    }
    catch (Exception e)
    {
      LogHelper.fatal(e.getMessage(), e);
      return false;
    }
    return true;
  }
}
