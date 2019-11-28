package yb.ecp.fast.flow.service.vo;

import java.util.Map;

public class NodeConditionVO
{
  private String processInstanceId;
  private Map<String, String> params;
  
  public String getProcessInstanceId()
  {
    return this.processInstanceId;
  }
  
  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }
  
  public Map<String, String> getParams()
  {
    return this.params;
  }
  
  public void setParams(Map<String, String> params)
  {
    this.params = params;
  }
}
