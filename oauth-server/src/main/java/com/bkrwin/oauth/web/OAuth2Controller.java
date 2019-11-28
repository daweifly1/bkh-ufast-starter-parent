package com.bkrwin.oauth.web;

import com.bkrwin.oauth.inframe.ActionResult;
import com.bkrwin.ufast.infra.constants.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/oauth"})
public class OAuth2Controller
  extends BasicController
{
  @Autowired
  private ConsumerTokenServices tokenServices;
  
  @RequestMapping(value={"/user-me"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ActionResult principal(String access_token)
  {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    return actionResult(authentication);
  }
  
  @RequestMapping(value={"/remove_token"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ActionResult removeToken(String access_token)
  {
    boolean flag = this.tokenServices.revokeToken(access_token);
    if (flag) {
      return new ActionResult(ErrorCode.Success.getCode(), ErrorCode.Success.getDesc());
    }
    return new ActionResult(ErrorCode.Failure.getCode(), ErrorCode.Failure.getDesc());
  }
}
