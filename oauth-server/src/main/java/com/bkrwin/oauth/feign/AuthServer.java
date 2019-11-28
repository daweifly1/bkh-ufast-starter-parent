package com.bkrwin.oauth.feign;

import com.bkrwin.oauth.service.vo.LoginAppUser;
import com.bkrwin.ufast.infra.constants.ErrorCode;
import com.bkrwin.ufast.infra.infra.ActionResult;
import java.util.ArrayList;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServer
{
  @Autowired
  private AuthClient authClient;
  
  public LoginAppUser getUserInfoByName(String name)
    throws Exception
  {
    ActionResult result = this.authClient.userInfo(name);
    if (result.getCode() == ErrorCode.Success.getCode())
    {
      if (result.getValue() == null) {
        return null;
      }
      Map<String, Object> resultMap = (Map)result.getValue();
      
      Integer locked = (Integer)resultMap.get("locked");
      Boolean noneLocked = Boolean.valueOf(false);
      if ((locked != null) && (locked.intValue() == 0)) {
        noneLocked = Boolean.valueOf(true);
      }
      LoginAppUser loginAppUser = new LoginAppUser(String.valueOf(resultMap.get("userId")), String.valueOf(resultMap.get("loginName")), String.valueOf(resultMap.get("password")), true, true, true, noneLocked.booleanValue(), new ArrayList());
      return loginAppUser;
    }
    return null;
  }
}
