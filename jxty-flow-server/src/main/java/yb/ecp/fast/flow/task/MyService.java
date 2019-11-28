package yb.ecp.fast.flow.task;

import java.io.Serializable;
import java.util.Map;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yb.ecp.fast.flow.feign.ClientConfiguration;
import yb.ecp.fast.flow.feign.ServerClient;
import yb.ecp.fast.flow.feign.ToUpdateVO;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.flow.inframe.FlowException;
import yb.ecp.fast.infra.infra.ActionResult;

@Service("myService")
public class MyService
  implements Serializable
{
  @Autowired
  private ClientConfiguration clientConfig;
  @Autowired
  private RuntimeService runtimeService;
  
  @Transactional(rollbackFor={Exception.class})
  public void update(Execution exe, String status)
    throws Exception
  {
    String exeId = exe.getId();
    Map<String, Object> params = this.runtimeService.getVariables(exeId);
    String apiUrl = (String)params.get("apiUrl");
    String pkId = (String)params.get("pkId");
    ServerClient serverClient = this.clientConfig.newInstanceByName(apiUrl);
    ToUpdateVO toUpdateInfo = new ToUpdateVO();
    toUpdateInfo.setId(pkId);
    params.put("status", status);
    toUpdateInfo.setParamInfos(params);
    try
    {
      ActionResult result = serverClient.updateInfo(toUpdateInfo);
      if (ErrorCode.Success.getCode() != result.getCode()) {
        throw new FlowException(result.getMessage());
      }
    }
    catch (Exception e)
    {
      throw new FlowException(e.getMessage());
    }
  }
}
