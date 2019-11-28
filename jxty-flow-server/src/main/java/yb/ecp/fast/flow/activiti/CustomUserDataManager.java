package yb.ecp.fast.flow.activiti;

import java.util.List;
import java.util.Map;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.Page;
import org.activiti.engine.impl.UserQueryImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.persistence.entity.data.UserDataManager;

public class CustomUserDataManager
  implements UserDataManager
{
  public List<User> findUserByQueryCriteria(UserQueryImpl userQuery, Page page)
  {
    return null;
  }
  
  public long findUserCountByQueryCriteria(UserQueryImpl userQuery)
  {
    return 0L;
  }
  
  public List<Group> findGroupsByUser(String s)
  {
    return null;
  }
  
  public List<User> findUsersByNativeQuery(Map<String, Object> map, int i, int i1)
  {
    return null;
  }
  
  public long findUserCountByNativeQuery(Map<String, Object> map)
  {
    return 0L;
  }
  
  public UserEntity create()
  {
    return null;
  }
  
  public UserEntity findById(String s)
  {
    return null;
  }
  
  public void insert(UserEntity entity) {}
  
  public UserEntity update(UserEntity entity)
  {
    return null;
  }
  
  public void delete(String s) {}
  
  public void delete(UserEntity entity) {}
}
