package yb.ecp.fast.flow.service.vo;

public class ToCompleteTaskVO
{
  private String taskId;
  private String processInstanceId;
  
  public String getTaskId()
  {
    return this.taskId;
  }
  
  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }
  
  public String getProcessInstanceId()
  {
    return this.processInstanceId;
  }
  
  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }
}
