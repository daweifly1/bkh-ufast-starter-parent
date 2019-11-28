package com.bkrwin.oauth.config;

import com.bkrwin.oauth.inframe.PermitAllUrl;
import com.bkrwin.oauth.service.MyAuthenctitaionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.ExpressionInterceptUrlRegistry;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig
  extends WebSecurityConfigurerAdapter
{
  @Value("${oauth-gateway.uri}")
  private String oauthGatewayUri;
  @Autowired
  public UserDetailsService userDetailsService;
  @Autowired
  private MyLogoutHandler logoutHandler;
  @Autowired
  private MySuccessAuthencitationHandler mySuccessAuthencitationHandler;
  
  @Bean(name={"daoAuhthenticationProvider"})
  public AuthenticationProvider daoAuhthenticationProvider()
  {
    MyAuthenctitaionProvider daoAuthenticationProvider = new MyAuthenctitaionProvider();
    daoAuthenticationProvider.setUserDetailsService(this.userDetailsService);
    daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
    return daoAuthenticationProvider;
  }
  
  @Autowired
  protected void configure(AuthenticationManagerBuilder auth)
    throws Exception
  {
    auth.authenticationProvider(daoAuhthenticationProvider());
  }
  
  @Bean
  public AuthenticationManager authenticationManagerBean()
    throws Exception
  {
    return super.authenticationManagerBean();
  }
  
  protected void configure(HttpSecurity http)
    throws Exception
  {
    ((HttpSecurity)((HttpSecurity)((HttpSecurity)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl)((HttpSecurity)((FormLoginConfigurer)((FormLoginConfigurer)((FormLoginConfigurer)http.formLogin().loginPage(this.oauthGatewayUri + "/login").loginProcessingUrl("/oauth/authLogin")).defaultSuccessUrl(this.oauthGatewayUri + "/loginSuccess")).successHandler(this.mySuccessAuthencitationHandler)).and()).authorizeRequests().antMatchers(PermitAllUrl.permitAllUrl(new String[0]))).permitAll().anyRequest()).authenticated().and()).logout().logoutSuccessHandler(this.logoutHandler).and()).httpBasic().and()).csrf().disable();
  }
}
