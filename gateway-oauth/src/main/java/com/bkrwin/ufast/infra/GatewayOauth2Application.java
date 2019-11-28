package com.bkrwin.ufast.infra;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableZuulProxy
public class GatewayOauth2Application
{
  public static void main(String[] args)
  {
    SpringApplication.run(GatewayOauth2Application.class, args);
  }
}
