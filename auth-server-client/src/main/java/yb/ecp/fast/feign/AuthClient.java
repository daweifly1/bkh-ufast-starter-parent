package yb.ecp.fast.feign;

import java.util.List;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import yb.ecp.fast.dto.AccountDTO;
import yb.ecp.fast.dto.AccountPwdDTO;
import yb.ecp.fast.dto.ProfileDTO;
import yb.ecp.fast.dto.RegMemberDTO;
import yb.ecp.fast.dto.SessionDataDTO;
import yb.ecp.fast.dto.TemplateDTO;
import yb.ecp.fast.dto.UserDetailDTO;
import yb.ecp.fast.dto.WorkSpaceDTO;
import yb.ecp.fast.infra.infra.ActionResult;

@EnableFeignClients
@FeignClient("fast-auth-server")
public abstract interface AuthClient
{
  @RequestMapping(value={"/account/updateLoginName"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult updateLoginName(@RequestBody AccountDTO paramAccountDTO)
    throws Exception;
  
  @RequestMapping(value={"/account/withPwd"}, method={org.springframework.web.bind.annotation.RequestMethod.PUT})
  public abstract ActionResult accountPwd(@RequestBody AccountPwdDTO paramAccountPwdDTO)
    throws Exception;
  
  @RequestMapping(value={"/account/account"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
  public abstract ActionResult removeAccount(@RequestParam("loginName") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/account/removeByUserId"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
  public abstract ActionResult removeAccountByUserId(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/account/password"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult updatePassword(@RequestParam("userId") String paramString1, @RequestParam("password") String paramString2)
    throws Exception;
  
  @RequestMapping(value={"/account/password"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
  public abstract ActionResult deletePassword(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/account/resetPassword"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult resetPassword(@RequestBody List<String> paramList)
    throws Exception;
  
  @RequestMapping(value={"/scepter/roleUserIds"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult getUserIdsByRoleId(@RequestParam("roleId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/scepter/roleUser"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult roleUser(@RequestParam("roleId") String paramString1, @RequestParam("userId") String paramString2)
    throws Exception;
  
  @RequestMapping(value={"/scepter/userRolesList"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult userRolesList(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/scepter/removeRoleUser"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult removeRoleUser(@RequestParam("roleId") String paramString1, @RequestParam("userId") String paramString2)
    throws Exception;
  
  @RequestMapping(value={"/scepter/getAuthCodes"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult getAuthCodes(@RequestParam("roleId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/usrCtx/session"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult session(@RequestBody SessionDataDTO paramSessionDataDTO)
    throws Exception;
  
  @RequestMapping(value={"/workspace/add"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult addWrokSpace(@RequestBody WorkSpaceDTO paramWorkSpaceDTO)
    throws Exception;
  
  @RequestMapping(value={"/template/itemById"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult templateById(@RequestParam("id") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/usrCtx/workspaceId"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult queryWorkspaceId(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/workspace/item"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<WorkSpaceDTO> queryWorkspace(@RequestParam("id") String paramString);
  
  @RequestMapping(value={"/profile/userDetail"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult<UserDetailDTO> getUserDetail(@RequestParam("userId") String paramString)
    throws Exception;
  
  @RequestMapping(value={"/profile/addUserWithAccount"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult addUserInfo(@RequestBody AccountPwdDTO paramAccountPwdDTO)
    throws Exception;
  
  @RequestMapping(value={"/profile/updateUserByAccount"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult updateUserByAccount(@RequestBody ProfileDTO paramProfileDTO);
  
  @RequestMapping(value={"/profile/regMember"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult regMember(@RequestBody RegMemberDTO paramRegMemberDTO);
  
  @RequestMapping(value={"/workspace/updateTemplate"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult changeTemplate(@RequestParam("workspaceId") String paramString1, @RequestParam("templateId") String paramString2)
    throws Exception;
  
  @RequestMapping(value={"/workspace/remove"}, method={org.springframework.web.bind.annotation.RequestMethod.DELETE})
  public abstract ActionResult removeWorkspace(@RequestParam("workspaceId") String paramString);
  
  @RequestMapping(value={"/workspace/lock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult lockWorkspace(@RequestParam("workspaceId") String paramString);
  
  @RequestMapping(value={"/workspace/unlock"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult unlockWorkspace(@RequestParam("workspaceId") String paramString);
  
  @RequestMapping(value={"/template/insert"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult addTemplate(@RequestBody TemplateDTO paramTemplateDTO);
  
  @RequestMapping(value={"/template/update"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult updateTemplate(@RequestBody TemplateDTO paramTemplateDTO);
  
  @RequestMapping(value={"/template/removeItem"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public abstract ActionResult removeTemplate(@RequestBody TemplateDTO paramTemplateDTO);
  
  @RequestMapping(value={"/template/itemById"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public abstract ActionResult queryTemplateById(@RequestParam("templateId") String paramString);
}


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\feign\AuthClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */