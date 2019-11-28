package yb.ecp.fast.flow.service.vo;

import java.util.List;
import java.util.Map;

public class TaskQueryVO
{
  private String defineKey;
  private String businessKey;
  private String assignee;
  private String taskId;
  private String startUserName;
  private List<String> userIds;
  private List<String> roleIds;
  private Map<String, ConditionValueVO> args;
  private List<String> titles;
  
  public String getDefineKey()
  {
    return this.defineKey;
  }
  
  public void setDefineKey(String defineKey)
  {
    this.defineKey = defineKey;
  }
  
  public String getBusinessKey()
  {
    return this.businessKey;
  }
  
  public void setBusinessKey(String businessKey)
  {
    this.businessKey = businessKey;
  }
  
  public String getAssignee()
  {
    return this.assignee;
  }
  
  public void setAssignee(String assignee)
  {
    this.assignee = assignee;
  }
  
  public String getTaskId()
  {
    return this.taskId;
  }
  
  public void setTaskId(String taskId)
  {
    this.taskId = taskId;
  }
  
  public List<String> getRoleIds()
  {
    return this.roleIds;
  }
  
  public void setRoleIds(List<String> roleIds)
  {
    this.roleIds = roleIds;
  }
  
  public Map<String, ConditionValueVO> getArgs()
  {
    return this.args;
  }
  
  public void setArgs(Map<String, ConditionValueVO> args)
  {
    this.args = args;
  }
  
  public List<String> getTitles()
  {
    return this.titles;
  }
  
  public void setTitles(List<String> titles)
  {
    this.titles = titles;
  }
  
  public String getStartUserName()
  {
    return this.startUserName;
  }
  
  public void setStartUserName(String startUserName)
  {
    this.startUserName = startUserName;
  }
  
  public List<String> getUserIds()
  {
    return this.userIds;
  }
  
  public void setUserIds(List<String> userIds)
  {
    this.userIds = userIds;
  }
}
