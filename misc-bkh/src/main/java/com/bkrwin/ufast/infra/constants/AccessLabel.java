package com.bkrwin.ufast.infra.constants;

public enum AccessLabel
{
  DenyAll("最低权限", 0),  Client("客户端访问", 1),  Oauth2("Oauth2 服务器访问", 2),  Inner("内部服务调用", 3),  FullTrusted("信任域访问", 100);
  
  private String name;
  private int levelNum;
  
  private AccessLabel(String name, int levelNum)
  {
    this.name = name;
    this.levelNum = levelNum;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public int getLevelNum()
  {
    return this.levelNum;
  }
  
  public void setLevelNum(int levelNum)
  {
    this.levelNum = levelNum;
  }
}
