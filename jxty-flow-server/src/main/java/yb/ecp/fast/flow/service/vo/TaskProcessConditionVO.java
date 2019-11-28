package yb.ecp.fast.flow.service.vo;

import java.util.List;

public class TaskProcessConditionVO
{
  private String processInstanceId;
  private List<String> titles;
  
  public String getProcessInstanceId()
  {
    return this.processInstanceId;
  }
  
  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }
  
  public List<String> getTitles()
  {
    return this.titles;
  }
  
  public void setTitles(List<String> titles)
  {
    this.titles = titles;
  }
}
