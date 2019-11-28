package com.bkrwin.oauth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

@Service
public class RedisAuthorizationCodeServices
  extends RandomValueAuthorizationCodeServices
{
  @Autowired
  private RedisTemplate<Object, Object> redisTemplate;
  
  protected void store(String code, OAuth2Authentication authentication)
  {
    this.redisTemplate.execute(new RedisAuthorizationCodeServices.1(this, code, authentication));
  }
  
  protected OAuth2Authentication remove(String code)
  {
    OAuth2Authentication oAuth2Authentication = (OAuth2Authentication)this.redisTemplate.execute(new RedisAuthorizationCodeServices.2(this, code));
    
    return oAuth2Authentication;
  }
  
  private String codeKey(String code)
  {
    return "oauth2:codes:" + code;
  }
}
