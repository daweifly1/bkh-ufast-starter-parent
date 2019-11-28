package com.bkrwin.oauth.inframe;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class PermitAllUrl
{
  private static final String[] ENDPOINTS = { "/actuator/health", "/actuator/env", "/actuator/metrics/**", "/actuator/trace", "/actuator/dump", 
    "/actuator/jolokia", "/actuator/info", "/actuator/logfile", "/actuator/refresh", "/actuator/flyway", "/actuator/liquibase", 
    "/actuator/heapdump", "/actuator/loggers", "/actuator/auditevents", "/actuator/env/PID", "/actuator/jolokia/**", 
    "/v2/api-docs/**", "/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/oauth/authLogin", "/login", "/loginSuccess", "/oauth/remove_token" };
  
  public static String[] permitAllUrl(String... urls)
  {
    if ((urls == null) || (urls.length == 0)) {
      return ENDPOINTS;
    }
    Set<String> set = new HashSet();
    Collections.addAll(set, ENDPOINTS);
    Collections.addAll(set, urls);
    
    return (String[])set.toArray(new String[set.size()]);
  }
}
