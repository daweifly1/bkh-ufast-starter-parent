package yb.ecp.fast.flow.service.vo;

public class ConditionValueVO
{
  private String value;
  private Boolean accurateFlag = Boolean.valueOf(true);
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
  }
  
  public Boolean getAccurateFlag()
  {
    return this.accurateFlag;
  }
  
  public void setAccurateFlag(Boolean accurateFlag)
  {
    this.accurateFlag = accurateFlag;
  }
}
