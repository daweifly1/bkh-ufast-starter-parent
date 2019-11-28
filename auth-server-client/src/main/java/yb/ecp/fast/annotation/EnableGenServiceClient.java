package yb.ecp.fast.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import yb.ecp.fast.feign.GenClient;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.TYPE})
@Import({GenClient.class})
public @interface EnableGenServiceClient {}


/* Location:              D:\maven-snapshots\gen-service-client-0.0.3-20180920.181352-256.jar!\yb\ecp\fast\annotation\EnableGenServiceClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */