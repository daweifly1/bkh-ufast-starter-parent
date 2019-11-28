package yb.ecp.fast.flow.activiti;

import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityImpl;
import org.activiti.engine.impl.persistence.entity.data.GroupDataManager;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import yb.ecp.fast.feign.AuthClient;
import yb.ecp.fast.infra.constants.ErrorCode;
import yb.ecp.fast.infra.infra.ActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class CustomGroupDataManager implements GroupDataManager {

    @Autowired
    private AuthClient authClient;


    public List findGroupByQueryCriteria(GroupQueryImpl groupQuery, Page page) {
        return null != groupQuery && !StringUtils.isBlank(groupQuery.getUserId()) ? this.getRolesByUserId(groupQuery.getUserId()) : null;
    }

    public long findGroupCountByQueryCriteria(GroupQueryImpl groupQuery) {
        return 0L;
    }

    public List findGroupsByUser(String s) {
        ArrayList groups = new ArrayList();
        GroupEntityImpl group = new GroupEntityImpl();
        group.setId(s);
        groups.add(group);
        return groups;
    }

    public List findGroupsByNativeQuery(Map map, int i, int i1) {
        return null;
    }

    public long findGroupCountByNativeQuery(Map map) {
        return 0L;
    }

    public GroupEntity create() {
        return null;
    }

    public GroupEntity findById(String s) {
        return null;
    }

    public void insert(GroupEntity entity) {
    }

    public GroupEntity update(GroupEntity entity) {
        return null;
    }

    public void delete(String s) {
    }

    public void delete(GroupEntity entity) {
    }

    private List getRolesByUserId(String userId) {
        ArrayList groups = new ArrayList();

        ActionResult rolesResult;
        try {
            rolesResult = this.authClient.userRolesList(userId);
        } catch (Exception var5) {
            return groups;
        }

        if (ErrorCode.Success.getCode() == rolesResult.getCode()) {
            rolesResult.getValue();
        }

        return groups;
    }
}
