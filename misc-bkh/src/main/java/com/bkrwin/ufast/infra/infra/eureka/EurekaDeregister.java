package com.bkrwin.ufast.infra.infra.eureka;

import com.bkrwin.ufast.infra.infra.log.LogHelper;
import java.util.Map;
import org.springframework.cloud.netflix.eureka.CloudEurekaInstanceConfig;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;

public class EurekaDeregister
{
  private EurekaRegistration registration;
  private EurekaServiceRegistry serviceRegistry;
  private EurekaClientConfigBean eurekaClientConfigBean;
  
  public EurekaDeregister(EurekaRegistration registration, EurekaServiceRegistry serviceRegistry, EurekaClientConfigBean eurekaClientConfigBean)
  {
    this.registration = registration;
    this.serviceRegistry = serviceRegistry;
    this.eurekaClientConfigBean = eurekaClientConfigBean;
  }
  
  public void showDeregisterInfo()
    throws Exception
  {
    if ((this.registration != null) && (this.serviceRegistry != null) && (this.eurekaClientConfigBean != null))
    {
      CloudEurekaInstanceConfig instanceConfig = this.registration.getInstanceConfig();
      String eurekaDefaultZone = (String)this.eurekaClientConfigBean.getServiceUrl().get("defaultZone");
      String registerInfo = eurekaDefaultZone + "apps/" + instanceConfig.getAppname() + "/" + instanceConfig.getInstanceId();
      LogHelper.monitor("you can deregister service by: \"curl -X DELETE " + registerInfo + "\"");
    }
  }
  
  public void deregister()
    throws Exception
  {
    if ((this.registration != null) && (this.serviceRegistry != null))
    {
      this.serviceRegistry.deregister(this.registration);
      LogHelper.monitor("deregister service info on Eureka: " + this.registration.getServiceId());
    }
  }
}
