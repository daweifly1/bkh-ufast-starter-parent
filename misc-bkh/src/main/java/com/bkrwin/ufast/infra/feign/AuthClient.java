package com.bkrwin.ufast.infra.feign;

import com.bkrwin.ufast.infra.feign.vo.UserLoginVO;
import com.bkrwin.ufast.infra.infra.ActionResult;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableFeignClients
@FeignClient("fast-auth-server")
public abstract interface AuthClient
{
  @RequestMapping(value={"/usrCtx/checkAuthCode"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult checkAuthCode(@RequestParam("userId") String paramString, @RequestParam("authCode") long paramLong, @RequestHeader(name="x-from-site") Integer paramInteger)
    throws Exception;
  
  @RequestMapping(value={"/auth/authInfo"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult GetAuthId();
  
  @RequestMapping(value={"/auth/login"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult Login(@RequestBody UserLoginVO paramUserLoginVO);
}
