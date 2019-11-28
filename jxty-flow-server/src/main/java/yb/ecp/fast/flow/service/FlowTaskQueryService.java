package yb.ecp.fast.flow.service;

import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.*;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.feign.AuthClient;
import yb.ecp.fast.flow.feign.dto.UserDetailDTO;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.FlowException;
import yb.ecp.fast.flow.service.vo.*;
import yb.ecp.fast.infra.infra.ActionResult;
import yb.ecp.fast.infra.infra.SearchCommonVO;

import java.util.*;

@Service
public class FlowTaskQueryService {
    @Value("${activiti.role.key}")
    private String roleKey;
    @Autowired
    private TaskService taskService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private AuthClient authClient;

    private Map<String, List<FlowElement>> getElementInfos(String prcessInstanceId) {
        String processDefinitionId = ((HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(prcessInstanceId).singleResult()).getProcessDefinitionId();

        BpmnModel model = this.repositoryService.getBpmnModel(processDefinitionId);
        Map<String, List<FlowElement>> flowElementMap = new HashMap();
        if (model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            List<FlowElement> sequenceList = new ArrayList();
            List<FlowElement> startList = new ArrayList();
            List<FlowElement> endList = new ArrayList();
            List<FlowElement> otherList = new ArrayList();
            flowElementMap.put("start", startList);
            flowElementMap.put("end", endList);
            flowElementMap.put("sequence", sequenceList);
            flowElementMap.put("otherList", otherList);
            for (FlowElement flowElement : flowElements) {
                if ((flowElement instanceof SequenceFlow)) {
                    sequenceList.add(flowElement);
                } else if ((flowElement instanceof StartEvent)) {
                    startList.add(flowElement);
                } else if ((flowElement instanceof EndEvent)) {
                    endList.add(flowElement);
                } else {
                    otherList.add(flowElement);
                }
            }
            return flowElementMap;
        }
        return null;
    }

    private List<UserTask> getUserTasks(String processInstanceId) {
        String processDefinitionId = ((HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult()).getProcessDefinitionId();

        BpmnModel model = this.repositoryService.getBpmnModel(processDefinitionId);

        Map<String, List<FlowElement>> flowElementMap = new HashMap();
        List<UserTask> userTasks = new ArrayList();
        if (model != null) {
            Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
            for (FlowElement flowElement : flowElements) {
                if ((flowElement instanceof UserTask)) {
                    userTasks.add((UserTask) flowElement);
                }
            }
        }
        return userTasks;
    }

    private List<String> getRoleIdsByUserId(String userId)
            throws Exception {
        ActionResult<Object> result = this.authClient.userRolesList(userId);

        List<String> roles = new ArrayList();
        if (ErrorCode.Success.getCode() == result.getCode()) {
            List<Map> rolesValue = (List) result.getValue();
            for (Map role : rolesValue) {
                roles.add(String.valueOf(role.get(this.roleKey)));
            }
        }
        return roles;
    }

    public Integer getApproveStatus(String processInstanceId) {
        ProcessInstance processInstance = (ProcessInstance) this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (processInstance == null) {
            return Integer.valueOf(0);
        }
        return Integer.valueOf(1);
    }

    public List<HistoryRecordVO> getApproveInfos(String processInstanceId, boolean isAllNodesFlag)
            throws Exception {
        List<HistoryRecordVO> historyCommnets = new ArrayList();

        List<HistoricVariableInstance> list = this.historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        Map<String, Object> variables = new HashMap();
        for (HistoricVariableInstance instance : list) {
            variables.put(instance.getVariableName(), instance.getValue());
        }
        List<HistoricActivityInstance> historicActivityInstances = ((HistoricActivityInstanceQuery) this.historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").orderByHistoricActivityInstanceEndTime().asc()).list();

        HistoryRecordVO starterInfo = new HistoryRecordVO();
        HistoricProcessInstance hi = (HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();

        Task task = (Task) ((TaskQuery) this.taskService.createTaskQuery().processInstanceId(processInstanceId)).singleResult();

        starterInfo.setUserId(hi.getStartUserId());
        if (variables.containsKey("supplierName")) {
            starterInfo.setUserName(variables.get("supplierName") == null ? null : String.valueOf(variables.get("supplierName")));
        } else {
            starterInfo.setUserName(getUserName(hi.getStartUserId()));
        }
        starterInfo.setNodeName("申请人");
        CommRecordVO startRecord = new CommRecordVO();
        startRecord.setTime(hi.getStartTime());
        starterInfo.setCommentsRecord(startRecord);
        historyCommnets.add(starterInfo);

        String lastHisId = null;
        for (HistoricActivityInstance historicActivityInstance : historicActivityInstances) {
            String historytaskId = historicActivityInstance.getTaskId();

            List<HistoricVariableInstance> variableInstances = this.historyService.createHistoricVariableInstanceQuery().taskId(historytaskId).list();

            Map<String, Object> paramMap = new HashMap();
            for (HistoricVariableInstance historicVariableInstance : variableInstances) {
                paramMap.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
            }
            Object comments = this.taskService.getTaskComments(historytaskId);

            HistoryRecordVO historyRecordVO = new HistoryRecordVO();
            historyRecordVO.setId(historicActivityInstance.getId());
            historyRecordVO.setNodeName(historicActivityInstance.getActivityName());
            if ((task != null) && (historicActivityInstance.getActivityName().equals(task.getName()))) {
                convertToProcessInfo(task, historyRecordVO);
            } else {
                historyRecordVO.setUserId(historicActivityInstance.getAssignee());
                historyRecordVO.setUserName(getUserName(historicActivityInstance.getAssignee()));
            }
            if ((comments != null) && (((List) comments).size() > 0)) {
                CommRecordVO commRecordVO = convertComment(paramMap, (List) comments);
                historyRecordVO.setCommentsRecord(commRecordVO);
            }
            historyCommnets.add(historyRecordVO);
            lastHisId = historicActivityInstance.getActivityId();
        }
        if ((!isEnd(processInstanceId)) && (isAllNodesFlag)) {
            Object userTasks = getUserTasks(processInstanceId);
            sequenceUserTask((List) userTasks);
            List<UserTask> emptyUserTasks;
            int i;
            if (lastHisId == null) {
                emptyUserTasks = (List<UserTask>) userTasks;
            } else {
                int index = 0;
                for (i = 0; i < ((List) userTasks).size(); i++) {
                    if (((UserTask) ((List) userTasks).get(i)).getId().equals(lastHisId)) {
                        index = i;
                        break;
                    }
                }
                emptyUserTasks = ((List) userTasks).subList(index + 1, ((List) userTasks).size());
            }
            List<HistoryRecordVO> historyRecordVOs = new ArrayList();
            for (UserTask userTask : emptyUserTasks) {
                historyRecordVOs.add(convertHistoryRecord(userTask));
            }
            historyCommnets.addAll(historyRecordVOs);
        }
        return historyCommnets;
    }

    private CommRecordVO convertComment(Map<String, Object> paramMap, List<Comment> comments) {
        CommRecordVO commRecordVO = new CommRecordVO();
        commRecordVO.setComments(((Comment) comments.get(0)).getFullMessage());
        commRecordVO.setTime(((Comment) comments.get(0)).getTime());
        commRecordVO.setParams(paramMap);
        return commRecordVO;
    }

    private void convertToProcessInfo(Task task, HistoryRecordVO historyRecordVO)
            throws Exception {
        StringBuffer userIdBuffer = new StringBuffer();
        StringBuffer userNameBuffer = new StringBuffer();
        List<IdentityLink> identityLinks = this.taskService.getIdentityLinksForTask(task.getId());
        for (IdentityLink identityLink : identityLinks) {
            if ("assignee".equals(identityLink.getType())) {
                userIdBuffer.append(identityLink.getUserId()).append(',');
                userNameBuffer.append(getUserName(identityLink.getUserId())).append(',');
            } else if ("candidate".equals(identityLink.getType())) {
                String userId = identityLink.getUserId();
                if (!StringUtils.isEmpty(userId)) {
                    userIdBuffer.append(identityLink.getUserId()).append(',');
                    ActionResult<UserDetailDTO> userDetailResult = this.authClient.getUserDetail(userId);
                    if (ErrorCode.Success.getCode() != userDetailResult.getCode()) {
                        throw new RuntimeException(userDetailResult.getMessage());
                    }
                    UserDetailDTO userDetailDTO = (UserDetailDTO) userDetailResult.getValue();
                    userNameBuffer.append(userDetailDTO.getName()).append(',');
                }
            } else if (!StringUtils.isEmpty(identityLink.getGroupId())) {
                String spaceId = (String) this.taskService.getVariable(task.getId(), "spaceId");
                ActionResult<String> roleIdResult = this.authClient.getRoleIdByName(identityLink.getGroupId(), spaceId);
                if (!StringUtils.isEmpty(roleIdResult.getValue())) {
                    ActionResult<List<String>> result = null;

                    String roleId = (String) roleIdResult.getValue();
                    if (roleId != null) {
                        result = this.authClient.roleUserIds(roleId);
                    }
                    List<String> userIds = (List) result.getValue();
                    if (!CollectionUtils.isEmpty(userIds)) {
                        for (String userId : userIds) {
                            userIdBuffer.append(userId).append(',');
                            ActionResult<UserDetailDTO> userDetailResult = this.authClient.getUserDetail(userId);
                            if (ErrorCode.Success.getCode() != userDetailResult.getCode()) {
                                throw new RuntimeException(userDetailResult.getMessage());
                            }
                            UserDetailDTO userDetailDTO = (UserDetailDTO) userDetailResult.getValue();
                            userNameBuffer.append(userDetailDTO.getName()).append(',');
                        }
                    }
                }
            }
        }
        if ((userIdBuffer.length() >= 1) && (userNameBuffer.length() >= 1)) {
            historyRecordVO.setUserId(userIdBuffer.substring(0, userIdBuffer.length() - 1));
            historyRecordVO.setUserName(userNameBuffer.substring(0, userNameBuffer.length() - 1));
        }
    }

    private HistoryRecordVO convertHistoryRecord(UserTask userTask)
            throws Exception {
        HistoryRecordVO historyRecordVO = new HistoryRecordVO();
        historyRecordVO.setId(userTask.getId());
        historyRecordVO.setNodeName(userTask.getName());
        historyRecordVO.setUserId(userTask.getAssignee());
        historyRecordVO.setUserName(getUserName(userTask.getAssignee()));
        return historyRecordVO;
    }

    private void sequenceUserTask(List userTasks) {
        Collections.sort(userTasks, new Comparator<UserTask>() {
            @Override
            public int compare(UserTask t1, UserTask t2) {
                return StringUtils.isEmpty(t1.getPriority()) ? 1 : (StringUtils.isEmpty(t2.getPriority()) ? -1 : t1.getPriority().compareTo(t2.getPriority()));
            }
        });
    }


    private String getUserName(String userId)
            throws Exception {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        ActionResult result = this.authClient.getUserDetail(userId);
        if (ErrorCode.Success.getCode() != result.getCode()) {
            throw new FlowException(ErrorCode.FlowUserNotExist);
        }
        UserDetailDTO userDetailDTO = (UserDetailDTO) result.getValue();
        if (userDetailDTO == null) {
            return null;
        }
        return userDetailDTO.getName();
    }

    public boolean isEnd(String processInstanceId) {
        ProcessInstance singleResult = (ProcessInstance) this.runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        if (singleResult == null) {
            return true;
        }
        return false;
    }

    public ErrorCode completeSingleTask(String taskId, String processInstanceId, Map<String, Object> params, String reviewer, String comments) {
        if (!exist(taskId)) {
            return ErrorCode.FlowTaskNotExist;
        }
        this.taskService.addUserIdentityLink(taskId, reviewer, "assignee");
        this.taskService.addComment(taskId, processInstanceId, comments == null ? "" : comments);
        params.put("lastComments", comments == null ? "" : comments);
        this.taskService.setVariablesLocal(taskId, params);
        this.taskService.complete(taskId, params);
        return ErrorCode.Success;
    }

    private boolean exist(String taskId) {
        Task task = (Task) ((TaskQuery) this.taskService.createTaskQuery().taskId(taskId)).singleResult();
        if (task != null) {
            return true;
        }
        return false;
    }

    public PageTaskVO getCurrentWorkList(SearchCommonVO<TaskQueryVO> condition, boolean sortFlag)
            throws Exception {
        PageTaskVO pageTaskVO = new PageTaskVO();
        List<Task> tasks = new ArrayList();
        if (sortFlag) {
            tasks = getSortedTask(pageTaskVO, condition, sortFlag);
        } else {
            tasks = getNotSortTask(pageTaskVO, condition);
        }
        List<TaskVO> resultList = convertTaskVOs(tasks, (TaskQueryVO) condition.getFilters());
        pageTaskVO.setTaskList(resultList);
        return pageTaskVO;
    }

    private List<Task> getSortedTask(PageTaskVO pageTaskVO, SearchCommonVO<TaskQueryVO> condition, boolean sortFlag)
            throws Exception {
        int start = (condition.getPageNum().intValue() - 1) * condition.getPageSize().intValue();
        int limit = condition.getPageSize().intValue();
        NativeTaskQuery taskQuery = this.taskService.createNativeTaskQuery();
        NativeTaskQuery countQuery = this.taskService.createNativeTaskQuery();
        TaskQueryVO taskQueryVO = (TaskQueryVO) condition.getFilters();
        String startUserName = taskQueryVO.getStartUserName() == null ? null : taskQueryVO.getStartUserName().trim();
        if (StringUtils.isEmpty(startUserName)) {
            taskQueryVO.setStartUserName(null);
        }
        if (!StringUtils.isEmpty(taskQueryVO.getStartUserName())) {
            ActionResult<List<String>> userIdsResult = this.authClient.getUserIdsByName(taskQueryVO.getStartUserName());
            if (ErrorCode.Success.getCode() == userIdsResult.getCode()) {
                taskQueryVO.setUserIds((List) userIdsResult.getValue());
            }
        }
        convertQuerySql(countQuery, taskQuery, (TaskQueryVO) condition.getFilters(), sortFlag);
        long count = countQuery.count();
        pageTaskVO.setTotalCount(count);
        if (count == 0L) {
            return new ArrayList();
        }
        if (count < condition.getPageNum().intValue() * condition.getPageSize().intValue()) {
            start = (int) (count / condition.getPageSize().intValue() * condition.getPageSize().intValue());
            return taskQuery.listPage(start, limit);
        }
        return taskQuery.listPage(start, limit);
    }

    private void convertQuerySql(NativeTaskQuery countQuery, NativeTaskQuery taskQuery, TaskQueryVO taskQueryVO, boolean sortFlag)
            throws Exception {
        StringBuffer commonSql = new StringBuffer();
        StringBuffer conditionSql = new StringBuffer();
        StringBuffer startUserConditionSql = new StringBuffer();
        Map<String, Object> paramaterMap = new LinkedHashMap();
        commonSql.append(" ACT_RU_TASK RES ");
        commonSql.append(" inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ ");
        String userId;
        if (!CollectionUtils.isEmpty(taskQueryVO.getUserIds())) {
            commonSql.append(" left join ACT_HI_PROCINST P on RES.PROC_INST_ID_ = P.PROC_INST_ID_ ");
            StringBuffer buffer = new StringBuffer();
            for (Iterator localIterator = taskQueryVO.getUserIds().iterator(); localIterator.hasNext(); ) {
                userId = (String) localIterator.next();
                buffer.append("'").append(userId).append("',");
            }
            startUserConditionSql.append(" P.START_USER_ID_ IN (").append(buffer.substring(0, buffer.length() - 1)).append(")");
        }
        if (!StringUtils.isEmpty(taskQueryVO.getAssignee())) {
            commonSql.append(" left join ACT_RU_IDENTITYLINK I on I.TASK_ID_ = RES.ID_");
            if (conditionSql.length() != 0) {
                conditionSql.append(" and ");
            }
            conditionSql.append(" (RES.ASSIGNEE_ = #{assigneeId} or (RES.ASSIGNEE_ is null and I.TYPE_ = 'candidate' and (I.USER_ID_ = #{userId} or I.GROUP_ID_ IN ( #{roles} ) ))) ");
            paramaterMap.put("assigneeId", taskQueryVO.getAssignee());
            paramaterMap.put("userId", taskQueryVO.getAssignee());
            List<String> roleIds = getRoleIdsByUserId(taskQueryVO.getAssignee());
            StringBuffer idsBuffer = new StringBuffer();
            for (String roleId : roleIds) {
                idsBuffer.append(roleId).append(',');
            }
            paramaterMap.put("roles", idsBuffer.substring(0, idsBuffer.length() - 1));
            if (!CollectionUtils.isEmpty(taskQueryVO.getRoleIds())) {
                paramaterMap.put("roles", taskQueryVO.getRoleIds());
            }
        }
        if (!StringUtils.isEmpty(taskQueryVO.getDefineKey())) {
            if (conditionSql.length() != 0) {
                conditionSql.append(" and ");
            }
            conditionSql.append("D.KEY_ = #{key} ");
            paramaterMap.put("key", taskQueryVO.getDefineKey());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getBusinessKey())) {
            commonSql.append(" inner join ACT_RU_EXECUTION E on RES.PROC_INST_ID_ = E.ID_");
            if (conditionSql.length() != 0) {
                conditionSql.append(" and ");
            }
            conditionSql.append(" E.BUSINESS_KEY_ = #{businessKey} ");
            paramaterMap.put("businessKey", taskQueryVO.getBusinessKey());
        }
        Map<String, ConditionValueVO> args = taskQueryVO.getArgs();
        String startUserName = taskQueryVO.getStartUserName() == null ? null : taskQueryVO.getStartUserName().trim();
        if ((!CollectionUtils.isEmpty(args)) || (!StringUtils.isEmpty(startUserName))) {
            commonSql.append(" inner join ACT_RU_VARIABLE A0 on RES.PROC_INST_ID_ = A0.PROC_INST_ID_ ");
            if (!StringUtils.isEmpty(startUserName)) {
                if (startUserConditionSql.length() != 0) {
                    startUserConditionSql.append(" or ");
                }
                startUserConditionSql.append(" (A0.NAME_='supplierName' AND A0.TEXT_ LIKE '%").append(startUserName).append("%')");
            }
            if (!CollectionUtils.isEmpty(args)) {
                for (Map.Entry<String, ConditionValueVO> entry : args.entrySet()) {
                    ConditionValueVO conditionValueVO = (ConditionValueVO) entry.getValue();
                    if (!StringUtils.isEmpty(conditionValueVO.getValue())) {
                        if (conditionSql.length() != 0) {
                            conditionSql.append(" and ");
                        }
                        if (conditionValueVO.getAccurateFlag().booleanValue()) {
                            conditionSql.append(" A0.TASK_ID_ is null and A0.NAME_= '").append((String) entry.getKey()).append("' and A0.TYPE_ = ").append("'string'").append(" and lower(A0.TEXT_) = '").append(conditionValueVO.getValue().toLowerCase().trim()).append("' ");
                        } else {
                            conditionSql.append(" A0.TASK_ID_ is null and A0.NAME_= '").append((String) entry.getKey()).append("' and A0.TYPE_ = ").append("'string'").append(" and lower(A0.TEXT_) like '%").append(conditionValueVO.getValue().toLowerCase().trim()).append("%'");
                        }
                    }
                }
            }
        }
        ConditionValueVO conditionValueVO;
        if (startUserConditionSql.length() > 0) {
            conditionSql.append(" and (").append(startUserConditionSql.toString()).append(") ");
        }
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select distinct RES.*, D.KEY_ from ").append(commonSql).append(" where ").append(conditionSql);
        StringBuffer querySqlBuffer = new StringBuffer();
        querySqlBuffer.append(sqlBuffer.toString()).append(" order by D.KEY_ desc, RES.CREATE_TIME_ desc");
        taskQuery.sql(querySqlBuffer.toString());
        sqlBuffer.insert(0, "select count(*) from (").append(')');
        countQuery.sql(sqlBuffer.toString());
        for (Map.Entry<String, Object> entry : paramaterMap.entrySet()) {
            taskQuery.parameter((String) entry.getKey(), entry.getValue());
            countQuery.parameter((String) entry.getKey(), entry.getValue());
        }
    }

    private List<Task> getNotSortTask(PageTaskVO pageTaskVO, SearchCommonVO<TaskQueryVO> condition)
            throws Exception {
        int start = (condition.getPageNum().intValue() - 1) * condition.getPageSize().intValue();
        int limit = condition.getPageSize().intValue();

        List<Task> tasks = new ArrayList();
        TaskQuery taskQuery = this.taskService.createTaskQuery();
        TaskQueryVO taskQueryVO = (TaskQueryVO) condition.getFilters();
        convertTaskQueryCondition(taskQuery, taskQueryVO);

        long totalCount = taskQuery.count();

        List<Task> todoList = ((TaskQuery) ((TaskQuery) ((TaskQuery) ((TaskQuery) taskQuery.orderByTaskDefinitionKey()).desc()).orderByTaskCreateTime()).desc()).listPage(start, limit);

        tasks.addAll(todoList);
        pageTaskVO.setTotalCount(totalCount);

        return tasks;
    }

    private List<HistoricTaskInstance> getApprovedSortedTask(PageTaskVO pageTaskVO, SearchCommonVO<TaskQueryVO> condition, boolean sortFlag) {
        int start = (condition.getPageNum().intValue() - 1) * condition.getPageSize().intValue();
        int limit = condition.getPageSize().intValue();

        NativeHistoricTaskInstanceQuery taskQuery = this.historyService.createNativeHistoricTaskInstanceQuery();
        NativeHistoricTaskInstanceQuery countQuery = this.historyService.createNativeHistoricTaskInstanceQuery();
        TaskQueryVO taskQueryVO = (TaskQueryVO) condition.getFilters();
        String startUserName = taskQueryVO.getStartUserName() == null ? null : taskQueryVO.getStartUserName().trim();
        if (StringUtils.isEmpty(startUserName)) {
            taskQueryVO.setStartUserName(null);
        }
        if (!StringUtils.isEmpty(taskQueryVO.getStartUserName())) {
            ActionResult<List<String>> userIdsResult = this.authClient.getUserIdsByName(taskQueryVO.getStartUserName());
            if (ErrorCode.Success.getCode() == userIdsResult.getCode()) {
                taskQueryVO.setUserIds((List) userIdsResult.getValue());
            }
        }
        convertHistoryQuerySql(countQuery, taskQuery, taskQueryVO, true);
        long count = countQuery.count();
        pageTaskVO.setTotalCount(count);
        if (count == 0L) {
            return new ArrayList();
        }
        if (count < condition.getPageNum().intValue() * condition.getPageSize().intValue()) {
            start = (int) (count / condition.getPageSize().intValue() * condition.getPageSize().intValue());
            return taskQuery.listPage(start, limit);
        }
        return taskQuery.listPage(start, limit);
    }

    private void convertHistoryQuerySql(NativeHistoricTaskInstanceQuery countQuery, NativeHistoricTaskInstanceQuery taskQuery, TaskQueryVO taskQueryVO, boolean sortFlag) {
        StringBuffer commonSql = new StringBuffer();
        StringBuffer conditionSql = new StringBuffer();
        StringBuffer startUserConditionSql = new StringBuffer();
        Map<String, Object> paramMap = new LinkedHashMap();
        commonSql.append(" ACT_HI_TASKINST RES ");
        commonSql.append(" inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ ");
        conditionSql.append(" RES.END_TIME_ IS NOT NULL ");
        String userId;
        if (!CollectionUtils.isEmpty(taskQueryVO.getUserIds())) {
            commonSql.append(" left join ACT_HI_PROCINST P on RES.PROC_INST_ID_ = P.PROC_INST_ID_ ");
            StringBuffer buffer = new StringBuffer();
            for (Iterator localIterator = taskQueryVO.getUserIds().iterator(); localIterator.hasNext(); ) {
                userId = (String) localIterator.next();
                buffer.append("'").append(userId).append("',");
            }
            startUserConditionSql.append(" P.START_USER_ID_ IN (").append(buffer.substring(0, buffer.length() - 1)).append(")");
        }
        if (!StringUtils.isEmpty(taskQueryVO.getAssignee())) {
            if (conditionSql.length() != 0) {
                conditionSql.append(" and ");
            }
            conditionSql.append(" RES.ASSIGNEE_ = #{assigneeId} ");
            paramMap.put("assigneeId", taskQueryVO.getAssignee());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getDefineKey())) {
            commonSql.append(" inner join ACT_RE_PROCDEF D on RES.PROC_DEF_ID_ = D.ID_ ");
            if (conditionSql.length() != 0) {
                conditionSql.append(" and ");
            }
            conditionSql.append(" D.KEY_ = #{key} ");
            paramMap.put("key", taskQueryVO.getDefineKey());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getBusinessKey())) {
            commonSql.append(" inner join ACT_HI_PROCINST HPI ON RES.PROC_INST_ID_ = HPI.ID_ ");
            if (conditionSql.length() != 0) {
                conditionSql.append(" and ");
            }
            conditionSql.append(" HPI.BUSINESS_KEY_ = #{businessKey} ");
            paramMap.put("businessKey", taskQueryVO.getBusinessKey());
        }
        Map<String, ConditionValueVO> args = taskQueryVO.getArgs();
        String startUserName = taskQueryVO.getStartUserName() == null ? null : taskQueryVO.getStartUserName().trim();
        if ((!CollectionUtils.isEmpty(args)) || (!StringUtils.isEmpty(startUserName))) {
            commonSql.append(" inner join ACT_HI_VARINST A0 on RES.PROC_INST_ID_ = A0.PROC_INST_ID_ ");
            if (!StringUtils.isEmpty(startUserName)) {
                if (startUserConditionSql.length() != 0) {
                    startUserConditionSql.append(" or ");
                }
                startUserConditionSql.append(" (A0.NAME_='supplierName' AND A0.TEXT_ LIKE '%").append(startUserName).append("%')");
            }
            for (Map.Entry<String, ConditionValueVO> entry : args.entrySet()) {
                ConditionValueVO conditionValueVO = (ConditionValueVO) entry.getValue();
                if (!StringUtils.isEmpty(conditionValueVO.getValue())) {
                    if (conditionSql.length() != 0) {
                        conditionSql.append(" and ");
                    }
                    if (conditionValueVO.getAccurateFlag().booleanValue()) {
                        conditionSql.append(" A0.NAME_= '").append((String) entry.getKey()).append("' and A0.VAR_TYPE_ = 'string' and lower(A0.TEXT_) = '").append(conditionValueVO.getValue().toLowerCase().trim()).append("' ");
                    } else {
                        conditionSql.append(" A0.NAME_= '").append((String) entry.getKey()).append("' and A0.VAR_TYPE_ = 'string' and lower(A0.TEXT_) LIKE '%").append(conditionValueVO.getValue().toLowerCase().trim()).append("%' ");
                    }
                }
            }
        }
        ConditionValueVO conditionValueVO;
        if (startUserConditionSql.length() > 0) {
            conditionSql.append(" and (").append(startUserConditionSql).append(") ");
        }
        StringBuffer querySql = new StringBuffer();
        StringBuffer countSql = new StringBuffer();
        querySql.append("select distinct RES.*, D.KEY_ from ").append(commonSql.toString()).append(" where ").append(conditionSql.toString()).append(" order by D.KEY_ desc, RES.START_TIME_ desc");
        countSql.append("select count(*) from (").append(querySql).append(')');
        countQuery.sql(countSql.toString());
        taskQuery.sql(querySql.toString());
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            taskQuery.parameter((String) entry.getKey(), entry.getValue());
            countQuery.parameter((String) entry.getKey(), entry.getValue());
        }
    }

    private List<HistoricTaskInstance> getApprovedNotSortTask(PageTaskVO pageTaskVO, SearchCommonVO<TaskQueryVO> condition) {
        int start = (condition.getPageNum().intValue() - 1) * condition.getPageSize().intValue();
        int limit = condition.getPageSize().intValue();
        List<HistoricTaskInstance> tasks = new ArrayList();

        HistoricTaskInstanceQuery historicTaskInstanceQuery = this.historyService.createHistoricTaskInstanceQuery();
        TaskQueryVO taskQueryVO = (TaskQueryVO) condition.getFilters();
        convertHistoryTaskQueryCondition(historicTaskInstanceQuery, taskQueryVO);

        long totalCount = historicTaskInstanceQuery.count();

        List<HistoricTaskInstance> doneList = ((HistoricTaskInstanceQuery) ((HistoricTaskInstanceQuery) historicTaskInstanceQuery.orderByTaskCreateTime()).desc()).listPage(start, limit);
        tasks.addAll(doneList);
        pageTaskVO.setTotalCount(totalCount);
        return tasks;
    }

    public PageTaskVO getApprovedList(SearchCommonVO<TaskQueryVO> condition, boolean sortFlag)
            throws Exception {
        PageTaskVO pageTaskVO = new PageTaskVO();

        List<HistoricTaskInstance> tasks = new ArrayList();
        if (sortFlag) {
            tasks = getApprovedSortedTask(pageTaskVO, condition, sortFlag);
        } else {
            tasks = getApprovedNotSortTask(pageTaskVO, condition);
        }
        List<TaskVO> resultList = convertHistoryTaskVOs(tasks, (TaskQueryVO) condition.getFilters());

        pageTaskVO.setTaskList(resultList);
        return pageTaskVO;
    }

    private void convertHistoryTaskQueryCondition(HistoricTaskInstanceQuery historicTaskInstanceQuery, TaskQueryVO taskQueryVO) {
        if (!StringUtils.isEmpty(taskQueryVO.getAssignee())) {
            historicTaskInstanceQuery.taskAssignee(taskQueryVO.getAssignee());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getDefineKey())) {
            historicTaskInstanceQuery.processDefinitionKey(taskQueryVO.getDefineKey());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getBusinessKey())) {
            historicTaskInstanceQuery.processInstanceBusinessKey(taskQueryVO.getBusinessKey());
        }
        Map<String, ConditionValueVO> args = taskQueryVO.getArgs();
        if (!CollectionUtils.isEmpty(args)) {
            for (Map.Entry<String, ConditionValueVO> entry : args.entrySet()) {
                ConditionValueVO conditionValueVO = (ConditionValueVO) entry.getValue();
                if (!StringUtils.isEmpty(conditionValueVO.getValue())) {
                    if (conditionValueVO.getAccurateFlag().booleanValue()) {
                        historicTaskInstanceQuery.processVariableValueEqualsIgnoreCase((String) entry.getKey(), conditionValueVO.getValue());
                    } else {
                        StringBuffer value = new StringBuffer();
                        value.append('%').append(conditionValueVO.getValue()).append('%');
                        historicTaskInstanceQuery.processVariableValueLikeIgnoreCase((String) entry.getKey(), value.toString());
                    }
                }
            }
        }
    }

    private void convertTaskQueryCondition(TaskQuery taskQuery, TaskQueryVO taskQueryVO)
            throws Exception {
        if (!StringUtils.isEmpty(taskQueryVO.getAssignee())) {
            List<String> roleIds = getRoleIdsByUserId(taskQueryVO.getAssignee());
            taskQuery.taskCandidateGroupIn(roleIds);
            taskQuery.taskCandidateOrAssigned(taskQueryVO.getAssignee());
        }
        if (!CollectionUtils.isEmpty(taskQueryVO.getRoleIds())) {
            taskQuery.taskCandidateGroupIn(taskQueryVO.getRoleIds());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getDefineKey())) {
            taskQuery.processDefinitionKey(taskQueryVO.getDefineKey());
        }
        if (!StringUtils.isEmpty(taskQueryVO.getBusinessKey())) {
            taskQuery.processInstanceBusinessKey(taskQueryVO.getBusinessKey());
        }
        Map<String, ConditionValueVO> args = taskQueryVO.getArgs();
        if (!CollectionUtils.isEmpty(args)) {
            for (Map.Entry<String, ConditionValueVO> entry : args.entrySet()) {
                ConditionValueVO conditionValueVO = (ConditionValueVO) entry.getValue();
                if (!StringUtils.isEmpty(conditionValueVO.getValue())) {
                    if (conditionValueVO.getAccurateFlag().booleanValue()) {
                        taskQuery.processVariableValueEqualsIgnoreCase((String) entry.getKey(), conditionValueVO.getValue());
                    } else {
                        StringBuffer value = new StringBuffer();
                        value.append('%').append(conditionValueVO.getValue()).append('%');
                        taskQuery.processVariableValueLikeIgnoreCase((String) entry.getKey(), value.toString());
                    }
                }
            }
        }
    }

    private List<TaskVO> convertTaskVOs(List<Task> tasks, TaskQueryVO taskQueryVO)
            throws Exception {
        List<TaskVO> resultList = new ArrayList();

        Map<String, String> userMap = new HashMap();
        TaskVO taskVO;
        for (Iterator iterator = tasks.iterator(); iterator.hasNext(); resultList.add(taskVO)) {
            Task task = (Task) iterator.next();
            taskQueryVO.getTitles().add("sourceFlag");
            taskQueryVO.getTitles().add("supplierName");
            Map<String, Object> titleParams = getVariables(task.getExecutionId(), taskQueryVO.getTitles());
            taskVO = getTaskDetail(userMap, task.getId());
            taskVO.setTitleParams(titleParams);
            if ((titleParams.get("sourceFlag") != null) && (String.valueOf(titleParams.get("sourceFlag")).equals("1"))) {
                taskVO.setStartUserName(titleParams.get("supplierName") == null ? null : String.valueOf(titleParams.get("supplierName")));
            }
        }
        return resultList;
    }

    private List<TaskVO> convertHistoryTaskVOs(List<HistoricTaskInstance> tasks, TaskQueryVO taskQueryVO)
            throws Exception {
        List<TaskVO> resultList = new ArrayList();

        Map<String, String> userMap = new HashMap();
        TaskVO taskVO;
        for (Iterator iterator = tasks.iterator(); iterator.hasNext(); resultList.add(taskVO)) {
            HistoricTaskInstance task = (HistoricTaskInstance) iterator.next();

            taskVO = getHistoryDetail(userMap, task);
            taskQueryVO.getTitles().add("sourceFlag");
            taskQueryVO.getTitles().add("supplierName");
            Map<String, Object> titleParams = getHistoryVariables(task.getId(), task.getProcessInstanceId(), taskQueryVO.getTitles());
            if ((titleParams.get("sourceFlag") != null) && (String.valueOf(titleParams.get("sourceFlag")).equals("1"))) {
                taskVO.setStartUserName(titleParams.get("supplierName") == null ? null : String.valueOf(titleParams.get("supplierName")));
            }
            taskVO.setTitleParams(titleParams);
        }
        return resultList;
    }

    private Map<String, Object> getHistoryVariables(String taskId, String processceId, List<String> titles) {
        Map<String, Object> titleParams = new HashMap();
        if (CollectionUtils.isEmpty(titles)) {
            return titleParams;
        }
        List<HistoricVariableInstance> list = this.historyService.createHistoricVariableInstanceQuery().processInstanceId(processceId).list();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        for (Iterator localIterator1 = titles.iterator(); localIterator1.hasNext(); ) {
            String title = (String) localIterator1.next();
            for (HistoricVariableInstance hisInstance : list) {
                if (title.equals(hisInstance.getVariableName())) {
                    titleParams.put(title, hisInstance.getValue());
                }
            }
        }
        String title;
        List<HistoricVariableInstance> exeInfos = this.historyService.createHistoricVariableInstanceQuery().taskId(taskId).list();
        for (HistoricVariableInstance historicVariableInstance : exeInfos) {
            titleParams.put(historicVariableInstance.getVariableName(), historicVariableInstance.getValue());
        }
        if (titleParams.isEmpty()) {
            return null;
        }
        return titleParams;
    }

    private Map<String, Object> getVariables(String executionId, List<String> titles) {
        Map<String, Object> titleParams = new HashMap();
        if (CollectionUtils.isEmpty(titles)) {
            return titleParams;
        }
        for (String title : titles) {
            Object obj = this.runtimeService.getVariable(executionId, title);
            titleParams.put(title, obj);
        }
        return titleParams;
    }

    public TaskVO getTaskDetail(Map<String, String> userMap, String taskId)
            throws Exception {
        HistoricTaskInstance historicTaskInstance = (HistoricTaskInstance) ((HistoricTaskInstanceQuery) this.historyService.createHistoricTaskInstanceQuery().taskId(taskId)).singleResult();
        TaskVO taskVO = null;
        if (historicTaskInstance != null) {
            taskVO = setTaskVO(userMap, historicTaskInstance);
        }
        return taskVO;
    }

    public TaskVO getHistoryDetail(Map<String, String> userMap, HistoricTaskInstance historicTaskInstance)
            throws Exception {
        TaskVO taskVO = null;
        if (historicTaskInstance != null) {
            taskVO = setTaskVO(userMap, historicTaskInstance);
        }
        return taskVO;
    }

    private TaskVO setTaskVO(Map<String, String> userMap, HistoricTaskInstance task)
            throws Exception {
        TaskVO taskVO = new TaskVO();
        taskVO.setTaskId(task.getId());
        taskVO.setTaskName(task.getName());
        taskVO.setApproveUserName(task.getAssignee() != null ? task.getAssignee() : "");
        taskVO.setStartTime(task.getStartTime());
        taskVO.setEndTime(task.getEndTime());

        taskVO.setDueTime(task.getDueDate());
        taskVO.setProcessInstanceId(task.getProcessInstanceId());

        ProcessDefinition processDefinition = (ProcessDefinition) this.repositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
        taskVO.setBusinessName(processDefinition.getName());
        taskVO.setWorkflowName(processDefinition.getKey());

        taskVO.setStatus(processDefinition.isSuspended() ? "已挂起" : "正常");
        taskVO.setVersion(String.valueOf(processDefinition.getVersion()));

        HistoricProcessInstance historicProcessInstance = (HistoricProcessInstance) this.historyService.createHistoricProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();

        taskVO.setBusinessEventId(historicProcessInstance.getBusinessKey());
        if (userMap.containsKey(historicProcessInstance.getStartUserId())) {
            taskVO.setStartUserName((String) userMap.get(historicProcessInstance.getStartUserId()));
        } else {
            String userName = getUserName(historicProcessInstance.getStartUserId());
            userMap.put(historicProcessInstance.getStartUserId(), userName);
            taskVO.setStartUserName(userName);
        }
        return taskVO;
    }

    public ToApproveInfoVO getToApproveInfo(String processInstanceId) {
        ToApproveInfoVO toApproveInfoVO = new ToApproveInfoVO();
        Task task = (Task) ((TaskQuery) this.taskService.createTaskQuery().processInstanceId(processInstanceId)).singleResult();
        if (task == null) {
            return toApproveInfoVO;
        }
        String currentId = task.getTaskDefinitionKey();
        Map<String, List<FlowElement>> flowElementMap = getElementInfos(task.getProcessInstanceId());
        List<FlowElement> sequenceList = (List) flowElementMap.get("sequence");
        List<FlowElement> otherList = (List) flowElementMap.get("otherList");
        List<FlowElement> endList = (List) flowElementMap.get("end");
        List<FlowElement> nodeList = new ArrayList();
        nodeList.addAll(otherList);
        nodeList.addAll(endList);
        List<List<String>> endConditionList = new ArrayList();
        List<List<String>> userConditionList = new ArrayList();
        Map<String, FlowElement> nodeMap = convertToNodeMap(nodeList);
        for (FlowElement flowElement : sequenceList) {
            SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
            if (currentId.equals(sequenceFlow.getSourceRef())) {
                List<String> condition = new ArrayList();
                collectCondition(sequenceFlow, flowElementMap, nodeMap, condition, userConditionList, endConditionList);
            }
        }
        SequenceFlow sequenceFlow;
        Object userConditions = new ArrayList();
        List<List<RelationConditionVO>> endConditions = new ArrayList();
        for (List<String> condition : userConditionList) {
            ((List) userConditions).add(convertELToObj(condition));
        }
        for (List<String> condition : endConditionList) {
            endConditions.add(convertELToObj(condition));
        }
        toApproveInfoVO.setUserConditions((List) userConditions);
        toApproveInfoVO.setEndConditions(endConditions);
        return toApproveInfoVO;
    }

    private Map<String, FlowElement> convertToNodeMap(List<FlowElement> nodeList) {
        Map<String, FlowElement> nodeMap = new HashMap();
        for (FlowElement flowElement : nodeList) {
            nodeMap.put(flowElement.getId(), flowElement);
        }
        return nodeMap;
    }

    private void collectCondition(SequenceFlow sequenceFlow, Map<String, List<FlowElement>> flowElementMap, Map<String, FlowElement> nodeMap, List<String> condition, List<List<String>> userConditionList, List<List<String>> endConditionList) {
        String targetId = sequenceFlow.getTargetRef();
        if (!nodeMap.isEmpty()) {
            condition.add(sequenceFlow.getConditionExpression());
            FlowElement flowElement = (FlowElement) nodeMap.get(targetId);
            if ((!(flowElement instanceof UserTask)) && (!(flowElement instanceof EndEvent))) {
                List<FlowElement> sequenceList = (List) flowElementMap.get("sequence");
                for (FlowElement element : sequenceList) {
                    SequenceFlow sequence = (SequenceFlow) element;
                    if (targetId.equals(sequence.getSourceRef())) {
                        collectCondition(sequence, flowElementMap, nodeMap, new ArrayList(condition), userConditionList, endConditionList);
                    }
                }
            } else if ((flowElement instanceof UserTask)) {
                userConditionList.add(condition);
            } else {
                endConditionList.add(condition);
            }
        }
    }

    private List<RelationConditionVO> convertELToObj(List<String> strList) {
        List<RelationConditionVO> relationConditionVOs = new ArrayList();
        for (String temp : strList) {
            if (!StringUtils.isEmpty(temp)) {
                String t = temp.replaceAll(" ", "");
                String[] strs = t.split("\\$\\{|\\&\\&|\\|\\||\\}");
                for (String s : strs) {
                    if (!StringUtils.isEmpty(s)) {
                        if (s.contains("==")) {
                            String[] values = s.split("==");
                            if (values.length == 2) {
                                KeyValue keyValue = new KeyValue();
                                keyValue.setKey(values[0]);
                                keyValue.setValue(values[1]);
                                RelationConditionVO relationConditionVO = new RelationConditionVO();
                                relationConditionVO.setKeyValue(keyValue);
                                relationConditionVO.setCondition("eq");
                                relationConditionVOs.add(relationConditionVO);
                            }
                        } else if (s.contains(">=")) {
                            String[] values = s.split(">=");
                            if (values.length == 2) {
                                KeyValue keyValue = new KeyValue();
                                keyValue.setKey(values[0]);
                                keyValue.setValue(values[1]);
                                RelationConditionVO relationConditionVO = new RelationConditionVO();
                                relationConditionVO.setKeyValue(keyValue);
                                relationConditionVO.setCondition("ge");
                                relationConditionVOs.add(relationConditionVO);
                            }
                        } else if (s.contains("<=")) {
                            String[] values = s.split("<=");
                            if (values.length == 2) {
                                KeyValue keyValue = new KeyValue();
                                keyValue.setKey(values[0]);
                                keyValue.setValue(values[1]);
                                RelationConditionVO relationConditionVO = new RelationConditionVO();
                                relationConditionVO.setKeyValue(keyValue);
                                relationConditionVO.setCondition("le");
                                relationConditionVOs.add(relationConditionVO);
                            }
                        } else if (s.contains("<>")) {
                            String[] values = s.split("<>");
                            if (values.length == 2) {
                                KeyValue keyValue = new KeyValue();
                                keyValue.setKey(values[0]);
                                keyValue.setValue(values[1]);
                                RelationConditionVO relationConditionVO = new RelationConditionVO();
                                relationConditionVO.setKeyValue(keyValue);
                                relationConditionVO.setCondition("ne");
                                relationConditionVOs.add(relationConditionVO);
                            }
                        } else if (s.contains(">")) {
                            String[] values = s.split(">");
                            if (values.length == 2) {
                                KeyValue keyValue = new KeyValue();
                                keyValue.setKey(values[0]);
                                keyValue.setValue(values[1]);
                                RelationConditionVO relationConditionVO = new RelationConditionVO();
                                relationConditionVO.setKeyValue(keyValue);
                                relationConditionVO.setCondition("gt");
                                relationConditionVOs.add(relationConditionVO);
                            }
                        } else if (s.contains("<")) {
                            String[] values = s.split("<");
                            if (values.length == 2) {
                                KeyValue keyValue = new KeyValue();
                                keyValue.setKey(values[0]);
                                keyValue.setValue(values[1]);
                                RelationConditionVO relationConditionVO = new RelationConditionVO();
                                relationConditionVO.setKeyValue(keyValue);
                                relationConditionVO.setCondition("lt");
                                relationConditionVOs.add(relationConditionVO);
                            }
                        }
                    }
                }
            }
        }
        return relationConditionVOs;
    }

    public List<List<String>> getEndExpressions(String processInstanceId) {
        Task task = (Task) ((TaskQuery) this.taskService.createTaskQuery().processInstanceId(processInstanceId)).singleResult();
        if (task == null) {
            return null;
        }
        String currentId = task.getTaskDefinitionKey();
        Map<String, List<FlowElement>> flowElementMap = getElementInfos(task.getProcessInstanceId());
        List<FlowElement> sequenceList = (List) flowElementMap.get("sequence");
        List<FlowElement> otherList = (List) flowElementMap.get("otherList");
        List<FlowElement> endList = (List) flowElementMap.get("end");
        List<FlowElement> nodeList = new ArrayList();
        nodeList.addAll(otherList);
        nodeList.addAll(endList);
        List<List<String>> endConditionList = new ArrayList();
        List<List<String>> userConditionList = new ArrayList();
        Map<String, FlowElement> nodeMap = convertToNodeMap(nodeList);
        for (FlowElement flowElement : sequenceList) {
            SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
            if (currentId.equals(sequenceFlow.getSourceRef())) {
                List<String> condition = new ArrayList();
                collectCondition(sequenceFlow, flowElementMap, nodeMap, condition, userConditionList, endConditionList);
            }
        }
        return endConditionList;
    }

    public Map<String, Object> getHistoryVariables(String processInstanceId) {
        List<HistoricVariableInstance> list = this.historyService.createHistoricVariableInstanceQuery().processInstanceId(processInstanceId).list();
        Map<String, Object> variables = new HashMap();
        for (HistoricVariableInstance instance : list) {
            variables.put(instance.getVariableName(), instance.getValue());
        }
        return variables;
    }

    public TaskVO getToApproveInfoByProcessInstanceId(String processInstanceId, List<String> titles) {
        Task task = (Task) ((TaskQuery) this.taskService.createTaskQuery().processInstanceId(processInstanceId)).singleResult();
        if (task == null) {
            return null;
        }
        List<IdentityLink> identityLinks = this.taskService.getIdentityLinksForTask(task.getId());
        Set<String> userIds = new HashSet();
        for (IdentityLink identityLink : identityLinks) {
            if (identityLink.getUserId() != null) {
                userIds.add(identityLink.getUserId());
            }
        }
        if (task.getAssignee() != null) {
            userIds.add(task.getAssignee());
        }
        Object resultMap = getVariables(task.getExecutionId(), titles);
        if (resultMap == null) {
            resultMap = new HashMap();
        }
        TaskVO taskVO = new TaskVO();
        taskVO.setTaskId(task.getId());
        ((Map) resultMap).put("assignId", userIds);
        taskVO.setProcessInstanceId(processInstanceId);
        taskVO.setTitleParams((Map) resultMap);
        return taskVO;
    }

    public List<String> getProcessInstanceIdsByPkId(String pkId, String processId) {
        NativeHistoricTaskInstanceQuery taskQuery = this.historyService.createNativeHistoricTaskInstanceQuery();
        StringBuffer sqlBuffer = new StringBuffer();
        sqlBuffer.append("select distinct RES.* from ACT_HI_TASKINST RES inner join ACT_HI_VARINST A0 on RES.PROC_INST_ID_ = A0.PROC_INST_ID_ WHERE A0.TASK_ID_ is null and A0.NAME_= 'pkId' and A0.VAR_TYPE_ = 'string' and lower(A0.TEXT_) = #{pkId} ");
        taskQuery.parameter("pkId", pkId);
        if (!StringUtils.isEmpty(processId)) {
            sqlBuffer.append(" and RES.PROC_DEF_ID_ in (").append("select id_ from act_re_procdef where key_=(select key_ from act_re_procdef where id_=(select distinct proc_def_id_ from act_hi_taskinst where proc_inst_id_=#{processId}))").append(") ");
            taskQuery.parameter("processId", processId);
        }
        sqlBuffer.append(" order by RES.START_TIME_ desc");
        List<HistoricTaskInstance> tasks = ((NativeHistoricTaskInstanceQuery) taskQuery.sql(sqlBuffer.toString())).list();
        Set<String> processInstanceIds = new TreeSet();
        for (HistoricTaskInstance task : tasks) {
            processInstanceIds.add(task.getProcessInstanceId());
        }
        Object ids = new ArrayList();
        ((List) ids).addAll(processInstanceIds);
        return (List<String>) ids;
    }
}
