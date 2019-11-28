package com.bkrwin.ufast.infra.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthPageController
{
  @GetMapping({"/login"})
  public String requireAuthentication()
  {
    return "login";
  }
  
  @GetMapping({"/loginSuccess"})
  public String loginSuccess()
  {
    return "loginSuccess";
  }
}
