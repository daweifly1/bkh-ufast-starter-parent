package yb.ecp.fast.flow.web;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import yb.ecp.fast.dto.UserCacheDTO;
import yb.ecp.fast.feign.AuthClient;
import yb.ecp.fast.flow.inframe.ErrorCode;
import yb.ecp.fast.infra.infra.ActionResult;

import javax.servlet.http.HttpServletRequest;

public class BasicController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private AuthClient authClient;

    public <T> ActionResult<T> actionResult(ErrorCode code, T value) {
        return new ActionResult(code.getCode(), code.getDesc(), value);
    }

    public <T> ActionResult<T> actionResult(T value) {
        ErrorCode code = ErrorCode.Success;
        return actionResult(code, value);
    }

    public ActionResult actionResult(ErrorCode code) {
        return actionResult(code, null);
    }

    public String getUserId() {
        if (null == this.request) {
            return "test";
        }
        String userId = this.request.getHeader("x-user-id");
        if (StringUtils.isNotBlank(userId)) {
            return userId;
        }
        return "test";
    }

    public String getUserName() {
        String userId = this.getUserId();

        String userName;
        try {
            ActionResult e = this.authClient.getUserDetail(userId);
            UserCacheDTO userInfo = (UserCacheDTO) e.getValue();
            userName = userInfo.getName();
        } catch (Exception var5) {
            userName = userId;
        }

        return userName;
    }

}
