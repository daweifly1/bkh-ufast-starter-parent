package yb.ecp.fast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import yb.ecp.fast.feign.GenClient;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Import({GenClient.class})
public @interface EnableGenServiceClient {
}
