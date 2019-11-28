package com.bkrwin.oauth.config;

import com.bkrwin.ufast.infra.util.StringUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyLogoutHandler
  implements LogoutSuccessHandler
{
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException
  {
    request.getSession().invalidate();
    String uri = request.getParameter("redirect_uri");
    if (!StringUtil.isNullOrEmpty(uri)) {
      response.sendRedirect(uri);
    }
  }
}
