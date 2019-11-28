package yb.ecp.fast.flow.activiti;

import java.io.IOException;
import javax.sql.DataSource;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.activiti.spring.boot.ActivitiProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class ActivitiEngineConfiguration
  extends AbstractProcessEngineAutoConfiguration
{
  @Autowired
  CustomGroupDataManager customGroupDataManager;
  
  @Bean
  public SpringProcessEngineConfiguration springProcessEngineConfiguration(DataSource dataSource, PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor)
    throws IOException
  {
    SpringProcessEngineConfiguration springProcessEngineConfiguration = baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
    this.activitiProperties.getProcessDefinitionLocationPrefix();
    springProcessEngineConfiguration.setGroupDataManager(this.customGroupDataManager);
    springProcessEngineConfiguration.setUserDataManager(new CustomUserDataManager());
    
    return springProcessEngineConfiguration;
  }
}
