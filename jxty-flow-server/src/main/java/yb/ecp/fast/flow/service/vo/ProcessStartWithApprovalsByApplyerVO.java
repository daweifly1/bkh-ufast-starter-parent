package yb.ecp.fast.flow.service.vo;

public class ProcessStartWithApprovalsByApplyerVO
  extends ProcessStartWithApprovalsVO
{
  private String applyerId;
  
  public String getApplyerId()
  {
    return this.applyerId;
  }
  
  public void setApplyerId(String applyerId)
  {
    this.applyerId = applyerId;
  }
}
