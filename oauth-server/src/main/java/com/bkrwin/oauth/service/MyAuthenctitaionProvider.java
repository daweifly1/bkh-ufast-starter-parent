package com.bkrwin.oauth.service;

import com.bkrwin.oauth.service.vo.LoginAppUser;
import com.bkrwin.ufast.infra.security.CryptoUtil;
import java.math.BigInteger;
import org.apache.commons.logging.Log;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

public class MyAuthenctitaionProvider
  extends AbstractUserDetailsAuthenticationProvider
{
  private PasswordEncoder passwordEncoder;
  private UserDetailsService userDetailsService;
  
  protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException
  {
    if (authentication.getCredentials() == null)
    {
      this.logger.debug("Authentication failed: no credentials provided");
      
      throw new BadCredentialsException(this.messages.getMessage(
        "AbstractUserDetailsAuthenticationProvider.badCredentials", 
        "Bad credentials"));
    }
    String presentedPassword = authentication.getCredentials().toString();
    if ((userDetails instanceof LoginAppUser))
    {
      LoginAppUser loginAppUser = (LoginAppUser)userDetails;
      try
      {
        String pass = cryptoPassword(presentedPassword, loginAppUser.getId());
        if (!pass.equals(userDetails.getPassword())) {
          throw new BadCredentialsException(this.messages.getMessage(
            "AbstractUserDetailsAuthenticationProvider.badCredentials", 
            "Bad credentials"));
        }
      }
      catch (Exception e)
      {
        throw new BadCredentialsException(this.messages.getMessage(
          "AbstractUserDetailsAuthenticationProvider.badCredentials", 
          "Bad credentials"));
      }
    }
  }
  
  private String cryptoPassword(String text, String salt)
    throws Exception
  {
    String orginalText = text + "_" + salt;
    byte[] cypherBytes = CryptoUtil.encryptMD5(orginalText.getBytes());
    String cypherText = new BigInteger(cypherBytes).toString(16);
    return cypherText;
  }
  
  protected void doAfterPropertiesSet()
    throws Exception
  {
    Assert.notNull(this.userDetailsService, "A UserDetailsService must be set");
  }
  
  protected final UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
    throws AuthenticationException
  {
    try
    {
      loadedUser = getUserDetailsService().loadUserByUsername(username);
    }
    catch (UsernameNotFoundException notFound)
    {
      UserDetails loadedUser;
      throw notFound;
    }
    catch (Exception repositoryProblem)
    {
      throw new InternalAuthenticationServiceException(
        repositoryProblem.getMessage(), repositoryProblem);
    }
    UserDetails loadedUser;
    if (loadedUser == null) {
      throw new InternalAuthenticationServiceException(
        "UserDetailsService returned null, which is an interface contract violation");
    }
    return loadedUser;
  }
  
  public void setPasswordEncoder(Object passwordEncoder) {}
  
  private void setPasswordEncoder(PasswordEncoder passwordEncoder)
  {
    this.passwordEncoder = passwordEncoder;
  }
  
  protected PasswordEncoder getPasswordEncoder()
  {
    return this.passwordEncoder;
  }
  
  public void setUserDetailsService(UserDetailsService userDetailsService)
  {
    this.userDetailsService = userDetailsService;
  }
  
  protected UserDetailsService getUserDetailsService()
  {
    return this.userDetailsService;
  }
}
