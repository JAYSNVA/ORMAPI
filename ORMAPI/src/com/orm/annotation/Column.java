package com.orm.annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({java.lang.annotation.ElementType.FIELD})
public @interface Column
{
  String name() default "";
  
  boolean required() default false;
  
  Class clazz() default Object.class;
}