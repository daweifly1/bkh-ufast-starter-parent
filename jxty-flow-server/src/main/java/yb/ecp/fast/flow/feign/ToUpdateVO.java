package yb.ecp.fast.flow.feign;

import java.util.Map;

public class ToUpdateVO
{
  private String id;
  private Map<String, Object> paramInfos;
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public Map<String, Object> getParamInfos()
  {
    return this.paramInfos;
  }
  
  public void setParamInfos(Map<String, Object> paramInfos)
  {
    this.paramInfos = paramInfos;
  }
}
