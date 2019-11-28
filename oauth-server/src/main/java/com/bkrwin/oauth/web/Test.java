package com.bkrwin.oauth.web;

import java.io.PrintStream;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Test
{
  public static void main(String[] args)
  {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    System.out.println(encoder.encode("client2"));
  }
}
