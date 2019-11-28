package com.bkrwin.oauth.service;

import java.util.UUID;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;

public class RandomAuthenticationKeyGenerator
  implements AuthenticationKeyGenerator
{
  public String extractKey(OAuth2Authentication authentication)
  {
    return UUID.randomUUID().toString();
  }
}
