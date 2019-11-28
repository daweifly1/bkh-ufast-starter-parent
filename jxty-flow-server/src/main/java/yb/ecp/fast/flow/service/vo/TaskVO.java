package yb.ecp.fast.flow.service.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class TaskVO
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private String taskId;
  private String taskName;
  private String approveUserName;
  private Date startTime;
  private Date endTime;
  private Date dueTime;
  private String businessName;
  private String workflowName;
  private String status;
  private String version;
  private String businessEventId;
  private String startUserName;
  private String processInstanceId;
  private String toEndCondition;
  private Map<String, Object> titleParams;
  
  public String getTaskId()
  {
    return this.taskId;
  }
  
  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }
  
  public String getTaskName()
  {
    return this.taskName;
  }
  
  public void setTaskName(String taskName)
  {
    this.taskName = taskName;
  }
  
  public String getApproveUserName()
  {
    return this.approveUserName;
  }
  
  public void setApproveUserName(String approveUserName)
  {
    this.approveUserName = approveUserName;
  }
  
  public Date getStartTime()
  {
    return this.startTime;
  }
  
  public void setStartTime(Date startTime)
  {
    this.startTime = startTime;
  }
  
  public Date getEndTime()
  {
    return this.endTime;
  }
  
  public void setEndTime(Date endTime)
  {
    this.endTime = endTime;
  }
  
  public Date getDueTime()
  {
    return this.dueTime;
  }
  
  public void setDueTime(Date dueTime)
  {
    this.dueTime = dueTime;
  }
  
  public String getBusinessName()
  {
    return this.businessName;
  }
  
  public void setBusinessName(String businessName)
  {
    this.businessName = businessName;
  }
  
  public String getWorkflowName()
  {
    return this.workflowName;
  }
  
  public void setWorkflowName(String workflowName)
  {
    this.workflowName = workflowName;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String status)
  {
    this.status = status;
  }
  
  public String getVersion()
  {
    return this.version;
  }
  
  public void setVersion(String version)
  {
    this.version = version;
  }
  
  public String getBusinessEventId()
  {
    return this.businessEventId;
  }
  
  public void setBusinessEventId(String businessEventId)
  {
    this.businessEventId = businessEventId;
  }
  
  public String getStartUserName()
  {
    return this.startUserName;
  }
  
  public void setStartUserName(String startUserName)
  {
    this.startUserName = startUserName;
  }
  
  public String getProcessInstanceId()
  {
    return this.processInstanceId;
  }
  
  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }
  
  public Map<String, Object> getTitleParams()
  {
    return this.titleParams;
  }
  
  public void setTitleParams(Map<String, Object> titleParams)
  {
    this.titleParams = titleParams;
  }
  
  public String getToEndCondition()
  {
    return this.toEndCondition;
  }
  
  public void setToEndCondition(String toEndCondition)
  {
    this.toEndCondition = toEndCondition;
  }
}
