package yb.ecp.fast.flow.web;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import yb.ecp.fast.flow.service.FlowApproveService;
import yb.ecp.fast.flow.service.vo.CommentConditionVO;
import yb.ecp.fast.flow.service.vo.CompleteTaskVO;
import yb.ecp.fast.flow.service.vo.HistoryTaskVO;
import yb.ecp.fast.flow.service.vo.NodeConditionVO;
import yb.ecp.fast.flow.service.vo.PageTaskVO;
import yb.ecp.fast.flow.service.vo.TaskProcessConditionVO;
import yb.ecp.fast.flow.service.vo.TaskQueryVO;
import yb.ecp.fast.infra.infra.ActionResult;
import yb.ecp.fast.infra.infra.SearchCommonVO;

@RestController
@RequestMapping({"approveFlow"})
public class FlowApproveController
  extends BasicController
{
  @Autowired
  private FlowApproveService flowApproveService;
  
  @RequestMapping(value={"getToApproveList"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult getToApproveList(@RequestBody SearchCommonVO<TaskQueryVO> condition)
    throws Exception
  {
    PageTaskVO pageTaskVO = this.flowApproveService.getCurrentWorkList(condition, getUserId());
    return actionResult(pageTaskVO);
  }
  
  @RequestMapping(value={"getApprovedList"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult getApprovedList(@RequestBody SearchCommonVO<TaskQueryVO> condition)
    throws Exception
  {
    PageTaskVO pageTaskVO = this.flowApproveService.getApprovedList(condition, getUserId());
    return actionResult(pageTaskVO);
  }
  
  @RequestMapping(value={"completeTask"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult completeTask(@RequestBody CompleteTaskVO completeTaskVO)
  {
    return actionResult(this.flowApproveService.completeTask(completeTaskVO, getUserId()));
  }
  
  @RequestMapping(value={"getHistoryInfos"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult getHistoryInfos(@RequestBody HistoryTaskVO historyTaskVO)
    throws Exception
  {
    return actionResult(this.flowApproveService.getHistoryInfos(historyTaskVO));
  }
  
  @RequestMapping(value={"getAllCommentsByPkId"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult getAllCommentsByPkId(@RequestBody CommentConditionVO commentConditionVO)
    throws Exception
  {
    return actionResult(this.flowApproveService.getAllCommentsByPkId(commentConditionVO));
  }
  
  @RequestMapping(value={"getApproveStatus"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ActionResult getApproveStatus(String processInstanceId)
  {
    return actionResult(this.flowApproveService.getApproveStatus(processInstanceId));
  }
  
  @RequestMapping(value={"getProcessStatuses"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult getProcessSatatuses(@RequestBody List<String> processIds)
  {
    return actionResult(this.flowApproveService.getProcessStatues(processIds));
  }
  
  @RequestMapping(value={"getToApproveInfo"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ActionResult getToApproveInfo(String processInstanceId)
  {
    return actionResult(this.flowApproveService.getToApproveInfo(processInstanceId));
  }
  
  @RequestMapping(value={"isEndByCondition"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult isEndByCondition(@RequestBody NodeConditionVO nodeConditionVO)
  {
    return actionResult(this.flowApproveService.isEndByCondition(nodeConditionVO));
  }
  
  @RequestMapping(value={"getHistoryVariables"}, method={org.springframework.web.bind.annotation.RequestMethod.GET})
  public ActionResult getHistoryVariables(String processInstanceId)
  {
    return actionResult(this.flowApproveService.getHistoryVariables(processInstanceId));
  }
  
  @RequestMapping(value={"getToApproveByProcessInstanceId"}, method={org.springframework.web.bind.annotation.RequestMethod.POST})
  public ActionResult getToApproveByProcessInstanceId(@RequestBody TaskProcessConditionVO taskProcessConditionVO)
  {
    return actionResult(this.flowApproveService.getToApproveByProcessInstanceId(taskProcessConditionVO.getProcessInstanceId(), taskProcessConditionVO.getTitles()));
  }
}
