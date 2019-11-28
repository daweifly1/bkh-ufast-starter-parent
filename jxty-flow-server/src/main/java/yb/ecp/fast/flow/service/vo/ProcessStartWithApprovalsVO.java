package yb.ecp.fast.flow.service.vo;

import java.util.HashMap;
import java.util.Map;

public class ProcessStartWithApprovalsVO
{
  private String key;
  private String id;
  private Map<String, Object> variables = new HashMap();
  
  public String getKey()
  {
    return this.key;
  }
  
  public void setKey(String key)
  {
    this.key = key;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setId(String id)
  {
    this.id = id;
  }
  
  public Map<String, Object> getVariables()
  {
    return this.variables;
  }
  
  public void setVariables(Map<String, Object> variables)
  {
    this.variables = variables;
  }
}
