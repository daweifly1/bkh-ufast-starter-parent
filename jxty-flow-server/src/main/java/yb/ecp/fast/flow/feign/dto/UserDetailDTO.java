package yb.ecp.fast.flow.feign.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserDetailDTO
  extends UserCacheDTO
{
  @ApiModelProperty("员工上级Id")
  private String superiorId;
  @ApiModelProperty("上级名称")
  private String superiorName;
  @ApiModelProperty("工作空间")
  private String spaceId;
  
  public String getSuperiorName()
  {
    return this.superiorName;
  }
  
  public void setSuperiorName(String superiorName)
  {
    this.superiorName = superiorName;
  }
  
  public String getSuperiorId()
  {
    return this.superiorId;
  }
  
  public void setSuperiorId(String superiorId)
  {
    this.superiorId = superiorId;
  }
  
  public String getSpaceId()
  {
    return this.spaceId;
  }
  
  public void setSpaceId(String spaceId)
  {
    this.spaceId = spaceId;
  }
}
