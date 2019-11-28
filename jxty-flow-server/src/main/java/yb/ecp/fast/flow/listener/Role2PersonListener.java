package yb.ecp.fast.flow.listener;

import com.fast.starter.logging.LogHelper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.IdentityLink;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.AuthClient;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.SpringUtil;
import yb.ecp.fast.infra.infra.ActionResult;

public class Role2PersonListener
  extends AbstractUserTranslateListener
{
  public void notify(DelegateTask delegateTask)
  {
    AuthClient authClient = (AuthClient)SpringUtil.getBean("yb.ecp.fast.flow.feign.AuthClient");
    Set<IdentityLink> identityLinks = delegateTask.getCandidates();
    for (IdentityLink identityLink : identityLinks) {
      if ("candidate".equals(identityLink.getType()))
      {
        String roleName = identityLink.getGroupId();
        
        Set<String> toApproveIds = new HashSet();
        ActionResult<List<String>> result = null;
        try
        {
          String spaceId = (String)delegateTask.getVariable("spaceId");
          if (StringUtils.isEmpty(spaceId)) {
            spaceId = "0";
          }
          ActionResult<String> roleIdResult = authClient.getRoleIdByName(roleName, spaceId);
          
          String roleId = (String)roleIdResult.getValue();
          if (roleId != null) {
            result = authClient.roleUserIds(roleId);
          }
          List<String> userIds = (List)result.getValue();
          for (String userId : userIds)
          {
            toApproveIds.add(userId);
            ActionResult<String> agentResult = authClient.agentId(userId);
            if ((agentResult.getCode() == ErrorCode.Success.getCode()) && (!StringUtils.isEmpty(agentResult.getValue()))) {
              toApproveIds.add(agentResult.getValue());
            }
          }
        }
        catch (Exception e)
        {
          LogHelper.error(e.getMessage(), ErrorCode.Failure.getCode());
        }
        if (!CollectionUtils.isEmpty(toApproveIds)) {
          delegateTask.addCandidateUsers(toApproveIds);
        }
        try
        {
          syncParam(delegateTask, toApproveIds);
        }
        catch (Exception e)
        {
          LogHelper.error(e.getMessage(), ErrorCode.Failure.getCode());
        }
      }
    }
  }
}
