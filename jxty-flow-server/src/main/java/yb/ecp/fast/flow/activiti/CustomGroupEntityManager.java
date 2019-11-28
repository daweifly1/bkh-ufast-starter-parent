package yb.ecp.fast.flow.activiti;

import java.util.List;
import java.util.Map;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.GroupQuery;
import org.activiti.engine.impl.GroupQueryImpl;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntityManager;

public class CustomGroupEntityManager
  implements GroupEntityManager
{
  public GroupEntity create()
  {
    return null;
  }
  
  public GroupEntity findById(String entityId)
  {
    return null;
  }
  
  public void insert(GroupEntity entity) {}
  
  public void insert(GroupEntity entity, boolean fireCreateEvent) {}
  
  public GroupEntity update(GroupEntity entity)
  {
    return null;
  }
  
  public GroupEntity update(GroupEntity entity, boolean fireUpdateEvent)
  {
    return null;
  }
  
  public void delete(String id) {}
  
  public void delete(GroupEntity entity) {}
  
  public void delete(GroupEntity entity, boolean fireDeleteEvent) {}
  
  public Group createNewGroup(String groupId)
  {
    return null;
  }
  
  public GroupQuery createNewGroupQuery()
  {
    return null;
  }
  
  public List<Group> findGroupByQueryCriteria(GroupQueryImpl query, Page page)
  {
    return null;
  }
  
  public long findGroupCountByQueryCriteria(GroupQueryImpl query)
  {
    return 0L;
  }
  
  public List<Group> findGroupsByUser(String userId)
  {
    return null;
  }
  
  public List<Group> findGroupsByNativeQuery(Map<String, Object> parameterMap, int firstResult, int maxResults)
  {
    return null;
  }
  
  public long findGroupCountByNativeQuery(Map<String, Object> parameterMap)
  {
    return 0L;
  }
  
  public boolean isNewGroup(Group group)
  {
    return false;
  }
}
