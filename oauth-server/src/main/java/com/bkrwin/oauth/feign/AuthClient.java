package com.bkrwin.oauth.feign;

import com.bkrwin.ufast.infra.infra.ActionResult;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableFeignClients
@FeignClient("fast-auth-server")
public abstract interface AuthClient
{
  @RequestMapping(value={"/internal/userInfo"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult userInfo(@RequestParam("username") String paramString)
    throws Exception;
}
