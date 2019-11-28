package yb.ecp.fast.flow.service.vo;

import java.util.List;

public class PageTaskVO
{
  private long totalCount;
  private List<TaskVO> taskList;
  
  public long getTotalCount()
  {
    return this.totalCount;
  }
  
  public void setTotalCount(long totalCount)
  {
    this.totalCount = totalCount;
  }
  
  public List<TaskVO> getTaskList()
  {
    return this.taskList;
  }
  
  public void setTaskList(List<TaskVO> taskList)
  {
    this.taskList = taskList;
  }
}
