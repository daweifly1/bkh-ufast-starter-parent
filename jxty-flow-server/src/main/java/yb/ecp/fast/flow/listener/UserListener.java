package yb.ecp.fast.flow.listener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.task.IdentityLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.ClientConfiguration;
import yb.ecp.fast.flow.feign.ServerClient;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.FlowException;
import yb.ecp.fast.flow.service.vo.ToParseInfo;
import yb.ecp.fast.infra.infra.ActionResult;

@Service("userListener")
public class UserListener
  implements TaskListener
{
  private static final long serialVersionUID = 3524221273992176078L;
  @Value("${userTask.convert.server-name}")
  private String userTaskServerName;
  @Value("${userTask.convert.api-url")
  private String apiUrl;
  @Autowired
  private ClientConfiguration clientConfig;
  
  @Transactional(rollbackFor={Exception.class})
  public void notify(DelegateTask delegateTask)
  {
    String url = "http://" + this.userTaskServerName + "/parse/userList";
    try
    {
      convertAssignee(delegateTask, url);
    }
    catch (Exception e)
    {
      throw new FlowException(e.getMessage());
    }
    try
    {
      convertCandidates(delegateTask, url);
    }
    catch (Exception e)
    {
      throw new FlowException(e.getMessage());
    }
  }
  
  private void convertCandidates(DelegateTask delegateTask, String url)
    throws Exception
  {
    Set<IdentityLink> identityLinks = delegateTask.getCandidates();
    List<String> roles = new ArrayList();
    List<String> users = new ArrayList();
    for (IdentityLink identityLink : identityLinks) {
      if (!StringUtils.isEmpty(identityLink.getGroupId())) {
        roles.add(identityLink.getGroupId());
      } else if (!StringUtils.isEmpty(identityLink.getUserId())) {
        users.add(identityLink.getUserId());
      }
    }
    Object candidates = new ArrayList();
    if (!roles.isEmpty())
    {
      List<String> results = parseUsers(roles, "role", url);
      if (!CollectionUtils.isEmpty(results)) {
        ((List)candidates).addAll(results);
      }
    }
    if (!users.isEmpty())
    {
      List<String> results = parseUsers(users, "candidate", url);
      if (!CollectionUtils.isEmpty(results)) {
        ((List)candidates).addAll(results);
      }
    }
    delegateTask.addCandidateUsers((Collection)candidates);
  }
  
  private void convertAssignee(DelegateTask delegateTask, String url)
    throws Exception
  {
    String assignee = delegateTask.getAssignee();
    List<String> users = new ArrayList();
    if (!StringUtils.isEmpty(assignee))
    {
      users.add(assignee);
      List<String> assignees = parseUsers(users, "assignee", url);
      if (!CollectionUtils.isEmpty(assignees)) {
        delegateTask.setAssignee((String)assignees.get(0));
      }
    }
  }
  
  private List<String> parseUsers(List<String> parseBefores, String type, String url)
    throws Exception
  {
    ServerClient serverClient = this.clientConfig.newInstanceByName(url);
    ToParseInfo toParseInfo = new ToParseInfo();
    toParseInfo.setType(type);
    toParseInfo.setBefores(parseBefores);
    ActionResult result = serverClient.parseUsers(toParseInfo);
    if (ErrorCode.Success.getCode() != result.getCode()) {
      throw new FlowException(result.getMessage());
    }
    return (List)result.getValue();
  }
}
