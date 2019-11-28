package yb.ecp.fast.flow.listener;

import com.fast.starter.logging.LogHelper;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.activiti.engine.HistoryService;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.history.HistoricVariableInstanceQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.AuthClient;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.SpringUtil;
import yb.ecp.fast.infra.infra.ActionResult;

public class ReturnLastPersonListener
  extends AbstractUserTranslateListener
{
  @Autowired
  private HistoryService historyService;
  
  public void notify(DelegateTask delegateTask)
  {
    AuthClient authClient = (AuthClient)SpringUtil.getBean("yb.ecp.fast.flow.feign.AuthClient");
    String assignee = delegateTask.getAssignee();
    ActionResult<String> result = null;
    try
    {
      result = authClient.agentId(assignee);
    }
    catch (Exception e)
    {
      LogHelper.error(e.getMessage(), ErrorCode.Failure.getCode());
    }
    Set<String> userSet = new HashSet();
    if (!StringUtils.isEmpty(result.getValue()))
    {
      delegateTask.setAssignee((String)result.getValue());
      userSet.add(result.getValue());
    }
    String processInstanceId = (String)delegateTask.getVariable("oldProcInstanceId");
    Map<String, Object> variableMap = getHistoryVariables(processInstanceId);
    Object flag = variableMap.get("syncFlag");
    if (flag != null)
    {
      boolean f = ((Boolean)flag).booleanValue();
      if (f) {
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
  }
  
  public Map<String, Object> getHistoryVariables(String processInstanceId)
  {
    List<HistoricVariableInstance> list = this.historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
    Map<String, Object> variables = new HashMap();
    for (HistoricVariableInstance instance : list) {
      variables.put(instance.getVariableName(), instance.getValue());
    }
    return variables;
  }
}
