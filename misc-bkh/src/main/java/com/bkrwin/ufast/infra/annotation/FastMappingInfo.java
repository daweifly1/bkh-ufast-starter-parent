package com.bkrwin.ufast.infra.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.METHOD})
public @interface FastMappingInfo
{
  boolean needLogin() default false;
  
  long code() default 0L;
  
  int actionLevel() default 0;
}
