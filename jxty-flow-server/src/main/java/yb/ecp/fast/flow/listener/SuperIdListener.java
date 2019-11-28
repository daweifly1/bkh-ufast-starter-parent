package yb.ecp.fast.flow.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.impl.el.FixedValue;
import yb.ecp.fast.flow.feign.AuthClient;
import yb.ecp.fast.flow.feign.dto.UserDetailDTO;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.SpringUtil;
import yb.ecp.fast.infra.infra.ActionResult;

public class SuperIdListener
  implements TaskListener
{
  private FixedValue param;
  private static final long serialVersionUID = -8523643999755626761L;
  
  public void notify(DelegateTask delegateTask)
  {
    AuthClient authClient = (AuthClient)SpringUtil.getBean("yb.ecp.fast.flow.feign.AuthClient");
    String assignee = delegateTask.getAssignee();
    
    ActionResult<UserDetailDTO> result = authClient.getUserDbDetail(assignee);
    if (ErrorCode.Success.getCode() == result.getCode())
    {
      UserDetailDTO userDetailDTO = (UserDetailDTO)result.getValue();
      String superId = userDetailDTO.getSuperiorId();
      delegateTask.setVariable(this.param.getExpressionText(), superId);
    }
  }
  
  public FixedValue getParam()
  {
    return this.param;
  }
  
  public void setParam(FixedValue param)
  {
    this.param = param;
  }
}
