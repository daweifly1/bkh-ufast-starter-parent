package yb.ecp.fast.flow.listener;

import com.fast.starter.logging.LogHelper;
import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.AuthClient;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.SpringUtil;
import yb.ecp.fast.infra.infra.ActionResult;

public class RoleListener
  implements TaskListener
{
  public void notify(DelegateTask delegateTask)
  {
    AuthClient authClient = (AuthClient)SpringUtil.getBean("yb.ecp.fast.flow.feign.AuthClient");
    Set<IdentityLink> identityLinks = delegateTask.getCandidates();
    for (IdentityLink identityLink : identityLinks) {
      if ("candidate".equals(identityLink.getType()))
      {
        String roleName = identityLink.getGroupId();
        ActionResult<String> result = null;
        try
        {
          String spaceId = (String)delegateTask.getVariable("spaceId");
          if (StringUtils.isEmpty(spaceId)) {
            spaceId = "0";
          }
          result = authClient.getRoleIdByName(roleName, spaceId);
        }
        catch (Exception e)
        {
          LogHelper.error(e.getMessage(), ErrorCode.Failure.getCode());
        }
        if (result.getCode() != ErrorCode.Success.getCode()) {
          LogHelper.error(ErrorCode.Failure.getDesc(), ErrorCode.Failure.getCode());
        }
        String roleId = (String)result.getValue();
        delegateTask.deleteCandidateGroup(roleName);
        delegateTask.addCandidateGroup(roleId);
      }
    }
  }
}
