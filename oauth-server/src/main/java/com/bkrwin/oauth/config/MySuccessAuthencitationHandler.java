package com.bkrwin.oauth.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class MySuccessAuthencitationHandler
  extends SavedRequestAwareAuthenticationSuccessHandler
{
  protected final Log logger = LogFactory.getLog(getClass());
  private RequestCache requestCache = new HttpSessionRequestCache();
  @Value("${oauth-gateway.uri}")
  private String oauthGatewayUri;
  
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws ServletException, IOException
  {
    SavedRequest savedRequest = this.requestCache.getRequest(request, response);
    if (savedRequest == null)
    {
      super.onAuthenticationSuccess(request, response, authentication);
      
      return;
    }
    String targetUrlParameter = getTargetUrlParameter();
    if ((isAlwaysUseDefaultTargetUrl()) || (
      (targetUrlParameter != null) && 
      (StringUtils.hasText(request.getParameter(targetUrlParameter)))))
    {
      this.requestCache.removeRequest(request, response);
      super.onAuthenticationSuccess(request, response, authentication);
      
      return;
    }
    clearAuthenticationAttributes(request);
    
    String targetUrl = savedRequest.getRedirectUrl();
    int index = targetUrl.indexOf("/oauth/authorize");
    if (index != -1) {
      targetUrl = this.oauthGatewayUri + targetUrl.substring(index);
    }
    this.logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
  
  protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
    throws IOException, ServletException
  {
    String targetUrl = determineTargetUrl(request, response);
    if (response.isCommitted())
    {
      this.logger.debug("Response has already been committed. Unable to redirect to " + 
        targetUrl);
      return;
    }
    targetUrl = this.oauthGatewayUri + "/loginSuccess";
    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }
  
  public void setRequestCache(RequestCache requestCache)
  {
    super.setRequestCache(requestCache);
  }
}
