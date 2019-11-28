package yb.ecp.fast.feign;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import yb.ecp.fast.infra.infra.ActionResult;

@EnableFeignClients
@FeignClient("fast-gen-service")
public interface GenClient {

   @RequestMapping(
      value = {"/guid/next"},
      method = {RequestMethod.GET}
   )
   ActionResult<Long> newGuid();

   @RequestMapping(
      value = {"/guid/text"},
      method = {RequestMethod.GET}
   )
   ActionResult<String> newGuidText();
}
