package yb.ecp.fast.flow.listener;

import com.fast.starter.logging.LogHelper;
import java.util.HashSet;
import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.task.IdentityLink;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.AuthClient;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.SpringUtil;
import yb.ecp.fast.infra.infra.ActionResult;

public class CandidateUsersListener
  extends AbstractUserTranslateListener
{
  public void notify(DelegateTask delegateTask)
  {
    AuthClient authClient = (AuthClient)SpringUtil.getBean("yb.ecp.fast.flow.feign.AuthClient");
    Set<IdentityLink> identityLinks = delegateTask.getCandidates();
    
    Set<String> userSet = new HashSet();
    for (IdentityLink identityLink : identityLinks)
    {
      ActionResult<String> result = null;
      try
      {
        result = authClient.agentId(identityLink.getUserId());
      }
      catch (Exception e)
      {
        LogHelper.error(e.getMessage(), ErrorCode.Failure.getCode());
      }
      if (!StringUtils.isEmpty(result.getValue()))
      {
        delegateTask.addCandidateUser((String)result.getValue());
        userSet.add(result.getValue());
      }
    }
    try
    {
      syncParam(delegateTask, userSet);
    }
    catch (Exception e)
    {
      LogHelper.error(e.getMessage(), ErrorCode.Failure.getCode());
    }
  }
}
