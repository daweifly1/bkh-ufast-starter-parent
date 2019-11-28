package com.bkrwin.oauth.config;

import com.bkrwin.oauth.inframe.PermitAllUrl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class ResourceServerConfig
  extends ResourceServerConfigurerAdapter
{
  @Value("${source.id:system}")
  private String source;
  
  public void configure(HttpSecurity http)
    throws Exception
  {
    ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)http.requestMatcher(new ResourceServerConfig.OAuth2RequestedMatcher(null)).authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl(new String[0]))).permitAll().anyRequest()).authenticated();
  }
  
  public void configure(ResourceServerSecurityConfigurer resourceserversecurityconfigurer)
    throws Exception
  {
    resourceserversecurityconfigurer.resourceId(this.source);
  }
}
