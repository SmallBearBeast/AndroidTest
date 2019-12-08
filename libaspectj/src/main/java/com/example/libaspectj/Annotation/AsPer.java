package com.example.libaspectj.Annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD, CONSTRUCTOR})
@Retention(RUNTIME)
public @interface AsPer {
    //权限允许
    String[] permissions();
}
