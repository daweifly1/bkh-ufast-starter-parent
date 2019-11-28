package yb.ecp.fast.feign;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import yb.ecp.fast.infra.infra.ActionResult;

@EnableFeignClients
@FeignClient("fast-gen-service")
public abstract interface FastGenClient
{
  @RequestMapping(value={"/guid/number"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<Long> numberGuid();
  
  @RequestMapping(value={"/guid/text"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<String> textGuid();
}


/* Location:              D:\maven-snapshots\gen-service-client-0.0.3-20180920.181352-256.jar!\yb\ecp\fast\feign\FastGenClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */