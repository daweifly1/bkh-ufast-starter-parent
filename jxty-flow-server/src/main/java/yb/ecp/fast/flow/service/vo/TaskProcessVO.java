package yb.ecp.fast.flow.service.vo;

public class TaskProcessVO
{
  private String processInstanceId;
  private boolean isEnd;
  
  public String getProcessInstanceId()
  {
    return this.processInstanceId;
  }
  
  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }
  
  public boolean isEnd()
  {
    return this.isEnd;
  }
  
  public void setEnd(boolean isEnd)
  {
    this.isEnd = isEnd;
  }
}
