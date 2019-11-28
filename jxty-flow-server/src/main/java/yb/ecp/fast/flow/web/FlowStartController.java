package yb.ecp.fast.flow.web;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yb.ecp.fast.flow.service.FlowStartService;
import yb.ecp.fast.flow.service.vo.ProcessStartVO;
import yb.ecp.fast.flow.service.vo.ProcessStartWithApprovalsByApplyerVO;
import yb.ecp.fast.flow.service.vo.ProcessStartWithApprovalsVO;
import yb.ecp.fast.infra.infra.ActionResult;

@RestController
@RequestMapping({"startFlow"})
public class FlowStartController
  extends BasicController
{
  @Autowired
  private FlowStartService flowStartService;
  
  @RequestMapping(value={"start"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult start(@RequestBody ProcessStartVO processStartVO)
  {
    return actionResult(this.flowStartService.start(processStartVO, getUserId()));
  }
  
  @RequestMapping(value={"startWithApprovals"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult startWithApprovals(@RequestBody ProcessStartWithApprovalsVO processStartWithApprovalsVO)
  {
    ProcessInstance processInstance = this.flowStartService.startWithApprovals(processStartWithApprovalsVO, getUserId());
    return actionResult(processInstance.getId());
  }
  
  @RequestMapping(value={"startWithApprovalsByApplyer"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult startWithApprovalsByApplyer(@RequestBody ProcessStartWithApprovalsByApplyerVO processStartWithApprovalsByApplyerVO)
  {
    ProcessInstance processInstance = this.flowStartService.startWithApprovals(processStartWithApprovalsByApplyerVO, processStartWithApprovalsByApplyerVO.getApplyerId());
    return actionResult(processInstance.getId());
  }
  
  @RequestMapping(value={"getApplyList"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ActionResult getApplyList(String userId)
  {
    return actionResult(this.flowStartService.getListByStarter(userId));
  }
}
