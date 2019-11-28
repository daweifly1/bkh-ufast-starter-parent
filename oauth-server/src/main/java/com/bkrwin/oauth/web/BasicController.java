package com.bkrwin.oauth.web;

import com.bkrwin.oauth.inframe.ActionResult;
import com.bkrwin.ufast.infra.constants.ErrorCode;
import javax.servlet.http.HttpServletRequest;

public class BasicController
{
  public <T> ActionResult<T> actionResult(ErrorCode code, T value)
  {
    return new ActionResult(code.getCode(), 
      code.getDesc(), 
      value);
  }
  
  public <T> ActionResult<T> actionResult(T value)
  {
    ErrorCode code = ErrorCode.Success;
    return actionResult(code, value);
  }
  
  public ActionResult actionResult(ErrorCode code)
  {
    return actionResult(code, null);
  }
  
  public String getUserId(HttpServletRequest request)
  {
    String userId = request.getHeader("x-user-id");
    return userId;
  }
}
