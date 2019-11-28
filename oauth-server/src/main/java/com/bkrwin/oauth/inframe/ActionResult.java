package com.bkrwin.oauth.inframe;

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
