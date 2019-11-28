package yb.ecp.fast;

import javax.sql.DataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EurekaClientConfigBean;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaRegistration;
import org.springframework.cloud.netflix.eureka.serviceregistry.EurekaServiceRegistry;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import yb.ecp.fast.annotation.EnableGenServiceClient;
import yb.ecp.fast.infra.annotation.EnableFastAccessGrant;
import yb.ecp.fast.infra.infra.eureka.EurekaDeregister;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableFastAccessGrant
@EnableGenServiceClient
public class FlowServerApplication
{
  public static void main(String[] args)
  {
    SpringApplication.run(FlowServerApplication.class, args);
  }
  
  @Bean(initMethod="showDeregisterInfo", destroyMethod="deregister")
  public EurekaDeregister eurekaDeregister(EurekaRegistration registration, EurekaServiceRegistry serviceRegistry, EurekaClientConfigBean eurekaClientConfigBean)
  {
    return new EurekaDeregister(registration, serviceRegistry, eurekaClientConfigBean);
  }
  
  @Bean
  public DataSourceTransactionManager platformTransactionManager(DataSource dataSource)
  {
    return new DataSourceTransactionManager(dataSource);
  }
}
