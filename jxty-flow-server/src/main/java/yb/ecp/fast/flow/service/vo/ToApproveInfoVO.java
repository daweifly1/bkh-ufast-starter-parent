package yb.ecp.fast.flow.service.vo;

import java.util.List;

public class ToApproveInfoVO
{
  private List<List<RelationConditionVO>> userConditions;
  private List<List<RelationConditionVO>> endConditions;
  
  public List<List<RelationConditionVO>> getUserConditions()
  {
    return this.userConditions;
  }
  
  public void setUserConditions(List<List<RelationConditionVO>> userConditions)
  {
    this.userConditions = userConditions;
  }
  
  public List<List<RelationConditionVO>> getEndConditions()
  {
    return this.endConditions;
  }
  
  public void setEndConditions(List<List<RelationConditionVO>> endConditions)
  {
    this.endConditions = endConditions;
  }
}
