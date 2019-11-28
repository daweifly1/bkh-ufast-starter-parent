package com.bkrwin.ufast.infra.constants;

public enum ErrorCode
{
  Success(0, "操作成功"),  Failure(1, "操作失败"),  NeedLogin(2, "用户没有登录"),  NoAuthorization(3, "没有权限执行此操作"),  AccessDenied(4, "没有权限访问此接口"),  UnExceptedError(4, "未知的错误发生"),  IllegalArument(5, "参数错误"),  YourErrorCodeGoesHere(1000, "你的失败码请在后面定义"),  OAuthUnAuthorized(1116, "用户未授权"),  OAuthTokenIsNull(1102, "缺少accessToken参数"),  OAuthTokenInvalid(1103, "无效的accessToken");
  
  private String desc;
  private int code;
  
  private ErrorCode(int code, String desc)
  {
    this.desc = desc;
    this.code = code;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public int getCode()
  {
    return this.code;
  }
}
