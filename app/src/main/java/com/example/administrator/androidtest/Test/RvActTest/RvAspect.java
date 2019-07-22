package com.example.administrator.androidtest.Test.RvActTest;

import com.example.liblog.SLog;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class RvAspect {
    private static final String TAG = "RvAspect";
    @After("within(@com.example.administrator.androidtest.Test.RvActTest.RvAnnotation *)")
    public void onRvAnnotation(JoinPoint point){
        SLog.i(TAG, "onRvAnnotation: point = " + point);
    }

    @After("execution(* * ..*.onClick(..)) && within(@com.example.administrator.androidtest.Test.RvActTest.RvAnnotation *)")
    public void onClick(JoinPoint point){
        SLog.i(TAG, "onClick: point = " + point);
    }

    @After("call(* * ..*.test(..))")
    public void call_Test(JoinPoint point){
        SLog.i(TAG, "call_Test: point = " + point);
    }


    @After("call(* * ..*.testUser(..))")
    public void call_TestUser(JoinPoint point){
        SLog.i(TAG, "call_TestUser: point = " + point);
    }

    // TODO: 2019-07-24 不生效，withincode好像需要显示调用才会生效
    @After("withincode(* * ..*.onClick(..)")
    public void withincode_OnClick(JoinPoint point){
        SLog.i(TAG, "withincode_OnClick: point = " + point);
    }

    @After("withincode(* * ..onCreate(..))")
    public void withincode_OnCreate(JoinPoint point){
        SLog.i(TAG, "withincode_OnCreate: point = " + point);
    }
}
