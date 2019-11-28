package com.bkrwin.ufast.infra.feign.vo;

public class AuthInfoVO
{
  private String authId;
  private String verifyCode;
  
  public String getAuthId()
  {
    return this.authId;
  }
  
  public void setAuthId(String authId)
  {
    this.authId = authId;
  }
  
  public String getVerifyCode()
  {
    return this.verifyCode;
  }
  
  public void setVerifyCode(String verifyCode)
  {
    this.verifyCode = verifyCode;
  }
}
