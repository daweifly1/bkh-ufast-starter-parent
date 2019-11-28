package yb.ecp.fast.flow.feign;

import java.util.Set;

public class SyncVO
{
  private String pkId;
  private Set<String> toApproveUserIds;
  private String lastComments;
  
  public String getPkId()
  {
    return this.pkId;
  }
  
  public void setPkId(String pkId)
  {
    this.pkId = pkId;
  }
  
  public Set<String> getToApproveUserIds()
  {
    return this.toApproveUserIds;
  }
  
  public void setToApproveUserIds(Set<String> toApproveUserIds)
  {
    this.toApproveUserIds = toApproveUserIds;
  }
  
  public String getLastComments()
  {
    return this.lastComments;
  }
  
  public void setLastComments(String lastComments)
  {
    this.lastComments = lastComments;
  }
}
