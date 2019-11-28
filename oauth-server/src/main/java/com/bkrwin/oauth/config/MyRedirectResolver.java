package com.bkrwin.oauth.config;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.endpoint.DefaultRedirectResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class MyRedirectResolver
  extends DefaultRedirectResolver
{
  private Collection<String> redirectGrantTypes = Arrays.asList(new String[] { "implicit", "authorization_code" });
  
  public String resolveRedirect(String requestedRedirect, ClientDetails client)
    throws OAuth2Exception
  {
    Set<String> authorizedGrantTypes = client.getAuthorizedGrantTypes();
    if (authorizedGrantTypes.isEmpty()) {
      throw new InvalidGrantException("A client must have at least one authorized grant type.");
    }
    if (!containsRedirectGrantType(authorizedGrantTypes)) {
      throw new InvalidGrantException(
        "A redirect_uri can only be used by implicit or authorization_code grant types.");
    }
    Set<String> registeredRedirectUris = client.getRegisteredRedirectUri();
    if ((registeredRedirectUris == null) || (registeredRedirectUris.isEmpty())) {
      throw new InvalidRequestException("At least one redirect_uri must be registered with the client.");
    }
    return obtainMatchingRedirect(registeredRedirectUris, requestedRedirect);
  }
  
  private boolean containsRedirectGrantType(Set<String> grantTypes)
  {
    for (String type : grantTypes) {
      if (this.redirectGrantTypes.contains(type)) {
        return true;
      }
    }
    return false;
  }
  
  private String obtainMatchingRedirect(Set<String> redirectUris, String requestedRedirect)
  {
    Assert.notEmpty(redirectUris, "Redirect URIs cannot be empty");
    if ((redirectUris.size() == 1) && (requestedRedirect == null)) {
      return (String)redirectUris.iterator().next();
    }
    for (String redirectUri : redirectUris) {
      if (requestedRedirect != null) {
        return redirectUri + "?redirect_uri=" + URLEncoder.encode(requestedRedirect);
      }
    }
    throw new RedirectMismatchException("Invalid redirect: " + requestedRedirect + 
      " does not match one of the registered values: " + redirectUris.toString());
  }
}
