package yb.ecp.fast.flow.service.vo;

public class HistoryRecordVO
{
  private String id;
  private String userId;
  private String userName;
  private String nodeName;
  private CommRecordVO commentsRecord;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String userName)
  {
    this.userName = userName;
  }
  
  public String getNodeName()
  {
    return this.nodeName;
  }
  
  public void setNodeName(String nodeName)
  {
    this.nodeName = nodeName;
  }
  
  public CommRecordVO getCommentsRecord()
  {
    return this.commentsRecord;
  }
  
  public void setCommentsRecord(CommRecordVO commentsRecord)
  {
    this.commentsRecord = commentsRecord;
  }
}
