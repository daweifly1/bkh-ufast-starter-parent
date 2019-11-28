package com.bkrwin.ufast.infra.annotation;

import com.bkrwin.ufast.infra.constants.AccessLabel;
import com.bkrwin.ufast.infra.constants.ErrorCode;
import com.bkrwin.ufast.infra.feign.AppClient;
import com.bkrwin.ufast.infra.feign.AuthClient;
import com.bkrwin.ufast.infra.infra.ActionResult;
import com.bkrwin.ufast.infra.util.StringUtil;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class FastAccessGrantAspect
{
  @Autowired
  private AuthClient authClient;
  @Autowired
  private AppClient appClient;
  
  private ActionResult actionResult(ErrorCode code)
  {
    return new ActionResult(code.getCode(), code.getDesc());
  }
  
  @Around("@annotation(com.bkrwin.ufast.infra.annotation.FastMappingInfo) && @annotation(fastMappingInfo)")
  public Object accessCheck(ProceedingJoinPoint proceedingJoinPoint, FastMappingInfo fastMappingInfo)
    throws Throwable
  {
    HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
    
    String accessClient = request.getHeader("x-access-client");
    String userId = request.getHeader("x-user-id");
    String appId = request.getHeader("x-app-id");
    String site = request.getHeader("x-from-site");
    
    AccessLabel accesslevel = AccessLabel.DenyAll;
    if ("oauth2".equals(accessClient)) {
      accesslevel = AccessLabel.Oauth2;
    } else if ("gateway".equals(accessClient)) {
      accesslevel = AccessLabel.Client;
    } else if (StringUtil.isNullOrEmpty(accessClient)) {
      accesslevel = AccessLabel.FullTrusted;
    }
    if (fastMappingInfo.actionLevel() > accesslevel.getLevelNum()) {
      return actionResult(ErrorCode.AccessDenied);
    }
    if (AccessLabel.FullTrusted.equals(accesslevel)) {
      return proceedingJoinPoint.proceed();
    }
    boolean needLogin = false;
    if ((fastMappingInfo.needLogin()) || (fastMappingInfo.code() > 0L)) {
      needLogin = true;
    }
    if ((needLogin == true) && (StringUtil.isNullOrSpace(userId))) {
      return actionResult(ErrorCode.NeedLogin);
    }
    if (fastMappingInfo.code() == 0L) {
      return proceedingJoinPoint.proceed();
    }
    ActionResult actionResult;
    if (StringUtil.isNullOrEmpty(appId)) {
      actionResult = this.authClient.checkAuthCode(userId, fastMappingInfo.code(), Integer.valueOf(StringUtils.isEmpty(site) ? 0 : Integer.valueOf(site).intValue()));
    } else {
      actionResult = actionResult(ErrorCode.Success);
    }
    if (actionResult.getCode() != 0) {
      return actionResult;
    }
    return proceedingJoinPoint.proceed();
  }
}
