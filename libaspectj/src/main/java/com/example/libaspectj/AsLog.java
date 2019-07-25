package com.example.libaspectj;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.CLASS;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, METHOD, CONSTRUCTOR})
@Retention(CLASS)
public @interface AsLog {

}
