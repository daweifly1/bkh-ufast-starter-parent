package yb.ecp.fast.flow.feign;

import java.util.List;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yb.ecp.fast.flow.feign.dto.UserDetailDTO;
import yb.ecp.fast.infra.infra.ActionResult;

@EnableFeignClients
@FeignClient("fast-auth-server")
public abstract interface AuthClient
{
  @RequestMapping(value={"/scepter/userRolesList"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult userRolesList(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/profile/userDetail"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<UserDetailDTO> getUserDetail(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping({"/leave/agentId"})
  public abstract ActionResult<String> agentId(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping({"/scepter/getRoleIdByName"})
  public abstract ActionResult<String> getRoleIdByName(@RequestParam("roleName") String paramString1, @RequestParam("spaceId") String paramString2)
    throws Exception;
  
  @RequestMapping({"/scepter/roleUserIds"})
  public abstract ActionResult<List<String>> roleUserIds(@RequestParam("roleId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/profile/detail"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<UserDetailDTO> getUserDbDetail(@RequestParam("userId") String paramString);
  
  @RequestMapping(value={"/profile/getUserIdsByName"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<List<String>> getUserIdsByName(@RequestParam("userName") String paramString);
}
