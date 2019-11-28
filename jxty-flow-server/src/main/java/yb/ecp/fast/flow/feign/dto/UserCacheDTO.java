package yb.ecp.fast.flow.feign.dto;

import io.swagger.annotations.ApiModelProperty;

public class UserCacheDTO
{
  private String userId;
  private String name;
  private String openId;
  private String deptId;
  private String deptCode;
  private String deptName;
  private String idNumber;
  private String mobile;
  private String areaCode;
  private String loginName;
  @ApiModelProperty("erp编码")
  private String erpCode;
  @ApiModelProperty("员工编码")
  private String code;
  
  public String getLoginName()
  {
    return this.loginName;
  }
  
  public void setLoginName(String loginName)
  {
    this.loginName = loginName;
  }
  
  public String getAreaCode()
  {
    return this.areaCode;
  }
  
  public void setAreaCode(String areaCode)
  {
    this.areaCode = areaCode;
  }
  
  public String getMobile()
  {
    return this.mobile;
  }
  
  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }
  
  public String getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String userId)
  {
    this.userId = userId;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String name)
  {
    this.name = name;
  }
  
  public String getOpenId()
  {
    return this.openId;
  }
  
  public void setOpenId(String openId)
  {
    this.openId = openId;
  }
  
  public String getDeptId()
  {
    return this.deptId;
  }
  
  public void setDeptId(String deptId)
  {
    this.deptId = deptId;
  }
  
  public String getDeptCode()
  {
    return this.deptCode;
  }
  
  public void setDeptCode(String deptCode)
  {
    this.deptCode = deptCode;
  }
  
  public String getDeptName()
  {
    return this.deptName;
  }
  
  public void setDeptName(String deptName)
  {
    this.deptName = deptName;
  }
  
  public String getIdNumber()
  {
    return this.idNumber;
  }
  
  public void setIdNumber(String idNumber)
  {
    this.idNumber = idNumber;
  }
  
  public String getErpCode()
  {
    return this.erpCode;
  }
  
  public void setErpCode(String erpCode)
  {
    this.erpCode = erpCode;
  }
  
  public String getCode()
  {
    return this.code;
  }
  
  public void setCode(String code)
  {
    this.code = code;
  }
}
