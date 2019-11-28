package yb.ecp.fast.flow.service.vo;

import java.util.List;
import java.util.Map;

public class CompleteTaskVO
{
  private List<ToCompleteTaskVO> toCompleteTasks;
  private String reviewer;
  private Map<String, Object> params;
  private String comments;
  
  public List<ToCompleteTaskVO> getToCompleteTasks()
  {
    return this.toCompleteTasks;
  }
  
  public void setToCompleteTasks(List<ToCompleteTaskVO> toCompleteTasks)
  {
    this.toCompleteTasks = toCompleteTasks;
  }
  
  public String getReviewer()
  {
    return this.reviewer;
  }
  
  public void setReviewer(String reviewer)
  {
    this.reviewer = reviewer;
  }
  
  public Map<String, Object> getParams()
  {
    return this.params;
  }
  
  public void setParams(Map<String, Object> params)
  {
    this.params = params;
  }
  
  public String getComments()
  {
    return this.comments;
  }
  
  public void setComments(String comments)
  {
    this.comments = comments;
  }
}
