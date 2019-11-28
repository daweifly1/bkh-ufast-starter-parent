package yb.ecp.fast.flow.service;

import java.util.List;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import yb.ecp.fast.flow.service.vo.ProcessStartVO;
import yb.ecp.fast.flow.service.vo.ProcessStartWithApprovalsVO;

@Service
public class FlowStartService
{
  @Autowired
  private RuntimeService runtimeService;
  @Autowired
  private IdentityService identityService;
  @Autowired
  private HistoryService historyService;
  
  public List<HistoricProcessInstance> getListByStarter(String userId)
  {
    List<HistoricProcessInstance> historicProcessInstanceList = this.historyService.createHistoricProcessInstanceQuery().startedBy(userId).list();
    return historicProcessInstanceList;
  }
  
  public ProcessInstance start(ProcessStartVO processStartVO, String userId)
  {
    this.identityService.setAuthenticatedUserId(userId);
    ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(processStartVO.getKey(), processStartVO.getId());
    return processInstance;
  }
  
  public ProcessInstance startWithApprovals(ProcessStartWithApprovalsVO processStartWithApprovalsVO, String userId)
  {
    this.identityService.setAuthenticatedUserId(userId);
    ProcessInstance processInstance = this.runtimeService.startProcessInstanceByKey(processStartWithApprovalsVO.getKey(), processStartWithApprovalsVO.getId(), processStartWithApprovalsVO.getVariables());
    return processInstance;
  }
}
