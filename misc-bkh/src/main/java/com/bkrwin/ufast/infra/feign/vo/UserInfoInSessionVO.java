package com.bkrwin.ufast.infra.feign.vo;

public class UserInfoInSessionVO
{
  private String userId;
  private String spaceId;
  private String userName;
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getSpaceId()
  {
    return this.spaceId;
  }
  
  public void setSpaceId(String spaceId)
  {
    this.spaceId = spaceId;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
}
