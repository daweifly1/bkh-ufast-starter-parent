package yb.ecp.fast.flow.service.vo;

public class HistoryTaskVO
{
  private String processInstanceId;
  private boolean allNodesFlag;
  
  public String getProcessInstanceId()
  {
    return this.processInstanceId;
  }
  
  public void setProcessInstanceId(String processInstanceId)
  {
    this.processInstanceId = processInstanceId;
  }
  
  public boolean isAllNodesFlag()
  {
    return this.allNodesFlag;
  }
  
  public void setAllNodesFlag(boolean allNodesFlag)
  {
    this.allNodesFlag = allNodesFlag;
  }
}
