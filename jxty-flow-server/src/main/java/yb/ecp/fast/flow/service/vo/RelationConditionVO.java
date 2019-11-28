package yb.ecp.fast.flow.service.vo;

public class RelationConditionVO
{
  private KeyValue keyValue;
  private String condition;
  
  public KeyValue getKeyValue()
  {
    return this.keyValue;
  }
  
  public void setKeyValue(KeyValue keyValue)
  {
    this.keyValue = keyValue;
  }
  
  public String getCondition()
  {
    return this.condition;
  }
  
  public void setCondition(String condition)
  {
    this.condition = condition;
  }
}
