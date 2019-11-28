package com.bkrwin.oauth.service;

import com.bkrwin.oauth.feign.AuthServer;
import com.bkrwin.oauth.service.vo.LoginAppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailServiceImpl
  implements UserDetailsService
{
  @Autowired
  private AuthServer authServer;
  protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
  
  public UserDetails loadUserByUsername(String username)
    throws UsernameNotFoundException
  {
    try
    {
      LoginAppUser loginAppUser = this.authServer.getUserInfoByName(username);
      if (loginAppUser == null) {
        throw new BadCredentialsException(this.messages.getMessage(
          "AbstractUserDetailsAuthenticationProvider.badCredentials", 
          "Bad credentials"));
      }
      return loginAppUser;
    }
    catch (Exception e)
    {
      throw new BadCredentialsException(this.messages.getMessage(
        "AbstractUserDetailsAuthenticationProvider.badCredentials", 
        "Bad credentials"));
    }
  }
}
