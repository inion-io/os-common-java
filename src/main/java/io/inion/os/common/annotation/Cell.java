package io.inion.os.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Cell {

  // TODO: Prüfen, ob transient wegfallen kann !!!
  boolean isTransient() default false;

  String name() default "";

  int order() default 0;

  String type() default "";

  String value() default "";

  String prop() default "";
}
