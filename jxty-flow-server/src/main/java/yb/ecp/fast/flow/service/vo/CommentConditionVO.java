package yb.ecp.fast.flow.service.vo;

public class CommentConditionVO
{
  private String pkId;
  private Integer type;
  private String processId;
  
  public String getPkId()
  {
    return this.pkId;
  }
  
  public void setPkId(String pkId)
  {
    this.pkId = pkId;
  }
  
  public Integer getType()
  {
    return this.type;
  }
  
  public void setType(Integer type)
  {
    this.type = type;
  }
  
  public String getProcessId()
  {
    return this.processId;
  }
  
  public void setProcessId(String processId)
  {
    this.processId = processId;
  }
}
