package com.example.administrator.androidtest.Test.AspectTest;

import com.example.libaspectj.Annotation.AsLog;

public class AspectDo {
    public static void doLog(Object t){
        if(t instanceof AspectTestAct.User){
            AspectTestAct.User user = (AspectTestAct.User) t;
            String log = "user.name = " + user.name + " user.age = " + user.age;
            doWork(log);
        }
    }

    @AsLog
    public static void doWork(String str){

    }
}
