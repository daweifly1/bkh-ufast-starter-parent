package com.bkrwin.ufast.infra.infra;

import com.bkrwin.ufast.infra.constants.ErrorCode;

public class ActionResult<T>
{
  protected int code;
  protected String message;
  protected T value;
  
  public ActionResult()
  {
    this.code = 0;
    this.message = "成功";
  }
  
  public ActionResult(T value)
  {
    this.code = 0;
    this.message = "成功";
    this.value = value;
  }
  
  public ActionResult(int code, String message, T value)
  {
    this.code = code;
    this.message = message;
    this.value = value;
  }
  
  public ActionResult(int code, String message)
  {
    this.code = code;
    this.message = message;
    this.value = null;
  }
  
  public ActionResult(ErrorCode errorCode)
  {
    this.code = errorCode.getCode();
    this.message = errorCode.getDesc();
    this.value = null;
  }
  
  public ActionResult(ErrorCode errorCode, T value)
  {
    this.code = errorCode.getCode();
    this.message = errorCode.getDesc();
    this.value = value;
  }
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int code)
  {
    this.code = code;
  }
  
  public String getMessage()
  {
    return this.message;
  }
  
  public void setMessage(String message)
  {
    this.message = message;
  }
  
  public T getValue()
  {
    return (T)this.value;
  }
  
  public void setValue(T value)
  {
    this.value = value;
  }
}
