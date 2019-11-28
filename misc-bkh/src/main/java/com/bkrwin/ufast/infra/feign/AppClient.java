package com.bkrwin.ufast.infra.feign;

import com.bkrwin.ufast.infra.infra.ActionResult;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@EnableFeignClients
@FeignClient("${oauth2.feign.name:fast-oauth2-server}")
public abstract interface AppClient
{
  @RequestMapping(value={"/App/checkAuthCode"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult checkAuthCode(@RequestParam("appId") String paramString1, @RequestParam("authCode") String paramString2);
}
