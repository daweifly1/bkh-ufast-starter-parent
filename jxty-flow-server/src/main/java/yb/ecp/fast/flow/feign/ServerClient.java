package yb.ecp.fast.flow.feign;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import yb.ecp.fast.flow.service.vo.ToParseInfo;
import yb.ecp.fast.infra.infra.ActionResult;

public abstract interface ServerClient
{
  @RequestMapping(value={"/updateInfo"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult updateInfo(@RequestBody ToUpdateVO paramToUpdateVO)
    throws Exception;
  
  @RequestMapping(value={"/parseUsers"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult parseUsers(@RequestBody ToParseInfo paramToParseInfo)
    throws Exception;
  
  @RequestMapping(value={"/pushData"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult pushData(@RequestBody SyncVO paramSyncVO)
    throws Exception;
}
