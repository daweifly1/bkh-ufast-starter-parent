package yb.ecp.fast.flow.service.vo;

import java.util.Date;
import java.util.Map;

public class CommRecordVO
{
  private Map<String, Object> params;
  private String comments;
  private Date time;
  
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
  
  public Date getTime()
  {
    return this.time;
  }
  
  public void setTime(Date time)
  {
    this.time = time;
  }
}
