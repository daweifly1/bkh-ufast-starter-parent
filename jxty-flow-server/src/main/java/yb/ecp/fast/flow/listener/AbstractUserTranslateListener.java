package yb.ecp.fast.flow.listener;

import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.ClientConfiguration;
import yb.ecp.fast.flow.feign.ServerClient;
import yb.ecp.fast.flow.feign.SyncVO;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.SpringUtil;
import yb.ecp.fast.infra.infra.ActionResult;

public abstract class AbstractUserTranslateListener
  implements TaskListener
{
  private FixedValue syncParam;
  
  public FixedValue getSyncParam()
  {
    return this.syncParam;
  }
  
  public void setSyncParam(FixedValue syncParam)
  {
    this.syncParam = syncParam;
  }
  
  public void syncParam(DelegateTask delegateTask, Set<String> userSet)
    throws Exception
  {
    if (this.syncParam == null) {
      return;
    }
    ClientConfiguration clientConfig = (ClientConfiguration)SpringUtil.getBean("clientConfiguration");
    String value = this.syncParam.getExpressionText();
    if ((StringUtils.isEmpty(value)) || (!"1".equals(value))) {
      return;
    }
    String apiUrl = (String)delegateTask.getVariable("apiUrl");
    String pkId = (String)delegateTask.getVariable("pkId");
    if (!StringUtils.isEmpty(apiUrl))
    {
      ServerClient serverClient = clientConfig.newInstanceByName(apiUrl);
      SyncVO syncVO = new SyncVO();
      syncVO.setPkId(pkId);
      syncVO.setToApproveUserIds(userSet);
      if (delegateTask.getVariable("lastComments") != null)
      {
        String lastComments = (String)delegateTask.getVariable("lastComments");
        syncVO.setLastComments(lastComments);
      }
      ActionResult result = serverClient.pushData(syncVO);
      if (ErrorCode.Success.getCode() != result.getCode()) {
        throw new RuntimeException(result.getMessage());
      }
      delegateTask.setVariable("syncFlag", Boolean.valueOf(true));
    }
  }
  
  public abstract void notify(DelegateTask paramDelegateTask);
}
