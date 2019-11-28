package yb.ecp.fast.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import yb.ecp.fast.feign.AuthClient;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Import({AuthClient.class})
public @interface EnableAuthServiceClient {}


/* Location:              D:\maven-snapshots\auth-server-client-0.0.3-20180920.181922-306.jar!\yb\ecp\fast\annotation\EnableAuthServiceClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */