package yb.ecp.fast.flow.service;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.TreeValueExpression;
import de.odysseus.el.util.SimpleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.FlowException;
import yb.ecp.fast.flow.inframe.StatusEnum;
import yb.ecp.fast.flow.service.vo.*;
import yb.ecp.fast.infra.infra.SearchCommonVO;

import java.util.*;
import java.util.Map.Entry;

@Service
public class FlowApproveService {
    @Autowired
    private FlowTaskQueryService flowTaskQueryService;

    public PageTaskVO getCurrentWorkList(SearchCommonVO<TaskQueryVO> condition, String userId)
            throws Exception {
        if (StringUtils.isEmpty(((TaskQueryVO) condition.getFilters()).getAssignee())) {
            ((TaskQueryVO) condition.getFilters()).setAssignee(userId);
        }
        return this.flowTaskQueryService.getCurrentWorkList(condition, true);
    }

    public PageTaskVO getApprovedList(SearchCommonVO<TaskQueryVO> condition, String userId)
            throws Exception {
        if (StringUtils.isEmpty(((TaskQueryVO) condition.getFilters()).getAssignee())) {
            ((TaskQueryVO) condition.getFilters()).setAssignee(userId);
        }
        return this.flowTaskQueryService.getApprovedList(condition, true);
    }

    public boolean isEnd(String processInstanceId) {
        return this.flowTaskQueryService.isEnd(processInstanceId);
    }

    @Transactional(rollbackFor = {Exception.class})
    public List<TaskProcessVO> completeTask(CompleteTaskVO completeTaskVO, String userId) {
        List<TaskProcessVO> taskProcessVOs = new ArrayList();
        List<ToCompleteTaskVO> toCompleteTaskVOs = completeTaskVO.getToCompleteTasks();
        if (StringUtils.isEmpty(completeTaskVO.getReviewer())) {
            completeTaskVO.setReviewer(userId);
        }
        for (ToCompleteTaskVO toCompleteTaskVO : toCompleteTaskVOs) {
            ErrorCode errorCode = this.flowTaskQueryService.completeSingleTask(toCompleteTaskVO.getTaskId(), toCompleteTaskVO.getProcessInstanceId(), completeTaskVO.getParams(), completeTaskVO.getReviewer(), completeTaskVO.getComments());
            if (ErrorCode.Success != errorCode) {
                throw new FlowException(ErrorCode.FlowTaskNotExist);
            }
            boolean isEnd = this.flowTaskQueryService.isEnd(toCompleteTaskVO.getProcessInstanceId());
            TaskProcessVO taskProcessVO = new TaskProcessVO();
            taskProcessVO.setEnd(isEnd);
            taskProcessVOs.add(taskProcessVO);
        }
        return taskProcessVOs;
    }

    public List<HistoryRecordVO> getHistoryInfos(HistoryTaskVO historyTaskVO)
            throws Exception {
        return this.flowTaskQueryService.getApproveInfos(historyTaskVO.getProcessInstanceId(), historyTaskVO.isAllNodesFlag());
    }

    public ApprovedInfoVO getAllCommentsByPkId(CommentConditionVO commentConditionVO)
            throws Exception {
        List<String> processIds = this.flowTaskQueryService.getProcessInstanceIdsByPkId(commentConditionVO.getPkId(), commentConditionVO.getProcessId());

        ApprovedInfoVO approvedInfoVO = new ApprovedInfoVO();
        if (CollectionUtils.isEmpty(processIds)) {
            return approvedInfoVO;
        }
        List<List<HistoryRecordVO>> hisInfos = new ArrayList();
        int maxLength = 0;
        String processId = commentConditionVO.getProcessId();
        if (StringUtils.isEmpty(processId)) {
            for (int i = 0; i < processIds.size(); i++) {
                List<HistoryRecordVO> comments = this.flowTaskQueryService.getApproveInfos((String) processIds.get(i), true);
                if (i < processIds.size() - 1) {
                    maxLength = comments.size() > maxLength ? comments.size() : maxLength;
                    hisInfos.add(comments);
                } else {
                    approvedInfoVO.setCurrInfo(comments);
                }
            }
        } else {
            for (int i = 0; i < processIds.size(); i++) {
                List<HistoryRecordVO> comments = this.flowTaskQueryService.getApproveInfos((String) processIds.get(i), true);
                if (processId.equals(processIds.get(i))) {
                    approvedInfoVO.setCurrInfo(comments);
                } else {
                    maxLength = comments.size() > maxLength ? comments.size() : maxLength;
                    hisInfos.add(comments);
                }
            }
        }
        approvedInfoVO.setHisMaxLength(Integer.valueOf(maxLength));
        approvedInfoVO.setHisInfos(hisInfos);
        return approvedInfoVO;
    }

    private Map<String, List<HistoryRecordVO>> getAllCommentsByPkIdDependInstanceId(String pkId, String processInstanceId)
            throws Exception {
        List<String> processIds = this.flowTaskQueryService.getProcessInstanceIdsByPkId(pkId, processInstanceId);
        Map<String, List<HistoryRecordVO>> historyComments = new LinkedHashMap();
        for (String processId : processIds) {
            List<HistoryRecordVO> comments = this.flowTaskQueryService.getApproveInfos(processId, true);
            historyComments.put(processId, comments);
        }
        return historyComments;
    }

    private Map<String, List<HistoryRecordVO>> getAllCommentsByPkIdDependNode(String pkId, String processInstanceId)
            throws Exception {
        List<String> processIds = this.flowTaskQueryService.getProcessInstanceIdsByPkId(pkId, processInstanceId);
        Map<String, List<HistoryRecordVO>> historyComments = new LinkedHashMap();
        for (String processId : processIds) {
            List<HistoryRecordVO> comments = this.flowTaskQueryService.getApproveInfos(processId, true);
            for (HistoryRecordVO comment : comments) {
                if (historyComments.containsKey(comment.getNodeName())) {
                    List<HistoryRecordVO> records = (List) historyComments.get(comment.getNodeName());
                    records.add(comment);
                } else {
                    List<HistoryRecordVO> records = new ArrayList();
                    records.add(comment);
                    historyComments.put(comment.getNodeName(), records);
                }
            }
        }
        return historyComments;
    }

    public ApproveStatusVO getApproveStatus(String processInstanceId) {
        Integer status = this.flowTaskQueryService.getApproveStatus(processInstanceId);
        ApproveStatusVO approveStatusVO = new ApproveStatusVO();
        String statusName = getStatusName(status);
        approveStatusVO.setStatus(status);
        approveStatusVO.setStatusName(statusName);
        return approveStatusVO;
    }

    private String getStatusName(Integer status) {
        if (status == null) {
            return null;
        }
        if (StatusEnum.Complated.getCode() == status.intValue()) {
            return StatusEnum.Complated.getDesc();
        }
        return StatusEnum.Approving.getDesc();
    }

    public Map<String, StatusEnum> getProcessStatues(List<String> processIds) {
        Map<String, StatusEnum> processStatusMap = new HashMap();
        for (String processId : processIds) {
            if (this.flowTaskQueryService.isEnd(processId)) {
                processStatusMap.put(processId, StatusEnum.Complated);
            } else {
                processStatusMap.put(processId, StatusEnum.Approving);
            }
        }
        return processStatusMap;
    }

    public ToApproveInfoVO getToApproveInfo(String processInstanceId) {
        return this.flowTaskQueryService.getToApproveInfo(processInstanceId);
    }

    public Boolean isEndByCondition(NodeConditionVO nodeConditionVO) {
        String processInstanceId = nodeConditionVO.getProcessInstanceId();
        List<List<String>> endExpressions = this.flowTaskQueryService.getEndExpressions(processInstanceId);
        if (CollectionUtils.isEmpty(endExpressions)) {
            return Boolean.valueOf(false);
        }
        for (List<String> sendExpressions : endExpressions) {
            if (equalConditions(sendExpressions, nodeConditionVO.getParams())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    private boolean equalConditions(List expressions, Map conditions) {
        ExpressionFactoryImpl factory = new ExpressionFactoryImpl();
        SimpleContext context = new SimpleContext();
        Iterator result = conditions.entrySet().iterator();

        while (result.hasNext()) {
            Entry entry = (Entry) result.next();
            context.setVariable((String) entry.getKey(), factory.createValueExpression(entry.getValue(), String.class));
        }

        Boolean result1 = Boolean.valueOf(true);

        Boolean value;
        for (Iterator entry1 = expressions.iterator(); entry1.hasNext(); result1 = Boolean.valueOf(result1.booleanValue() && value.booleanValue())) {
            String expression = (String) entry1.next();
            value = Boolean.valueOf(true);
            if (expression == null) {
                value = Boolean.valueOf(true);
            } else {
                try {
                    TreeValueExpression e = factory.createValueExpression(context, expression, Boolean.TYPE);
                    value = (Boolean) e.getValue(context);
                } catch (Exception var10) {
                    value = Boolean.valueOf(false);
                }
            }
        }

        return result1.booleanValue();
    }


    public Map<String, Object> getHistoryVariables(String processInstanceId) {
        return this.flowTaskQueryService.getHistoryVariables(processInstanceId);
    }

    public TaskVO getToApproveByProcessInstanceId(String processInstanceId, List<String> titles) {
        return this.flowTaskQueryService.getToApproveInfoByProcessInstanceId(processInstanceId, titles);
    }
}
