package com.bkrwin.ufast.infra.feign.vo;

public class UserLoginVO
{
  private String loginName;
  private String password;
  private String code;
  private String authId;
  
  public String getLoginName()
  {
    return this.loginName;
  }
  
  public void setLoginName(String loginName)
  {
    this.loginName = loginName;
  }
  
  public String getPassword()
  {
    return this.password;
  }
  
  public void setPassword(String password)
  {
    this.password = password;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
  
  public String getAuthId()
  {
    return this.authId;
  }
  
  public void setAuthId(String authId)
  {
    this.authId = authId;
  }
  
  public String toString()
  {
    return "UserLoginVO{loginName='" + this.loginName + '\'' + ", pass='" + this.password + '\'' + ", code='" + this.code + '\'' + '}';
  }
}
