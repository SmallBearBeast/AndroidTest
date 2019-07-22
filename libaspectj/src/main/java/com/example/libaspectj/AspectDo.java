package com.example.libaspectj;


public class AspectDo {
    @AsLog
    public static <T> void doWork(T t){

    }

    public static <T> void doLog(T t){

    }

    public static <T> void doStat(T t){

    }
}
