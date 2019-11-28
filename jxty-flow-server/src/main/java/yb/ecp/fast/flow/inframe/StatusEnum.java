package yb.ecp.fast.flow.inframe;

public enum StatusEnum
{
  Complated(0, "已完成"),  Approving(1, "审批中"),  Refused(0, "拒绝");
  
  private String desc;
  private int code;
  
  private StatusEnum(int code, String desc)
  {
    this.desc = desc;
    this.code = code;
  }
  
  public String getDesc()
  {
    return this.desc;
  }
  
  public int getCode()
  {
    return this.code;
  }
}
