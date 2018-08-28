package com.example.administrator.androidtest.AspectJ;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * Created by Administrator on 2018/8/23.
 */

@Aspect
public class AspectJTest {
    private static final String TAG = "AspectJTest";

    @Before("execution(* com.example.administrator.androidtest.AspectJ.AspectJAct.onClickAspectJ_1())")
    public void onClickAspectJ_1Before(JoinPoint joinPoint){
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onActivityMethodBefore: " + key);
    }

    @After("execution(* com.example.administrator.androidtest.AspectJ.AspectJAct.onClickAspectJ_2())")
    public void onClickAspectJ_2After(JoinPoint joinPoint){
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onClickAspectJ_2After: " + key);
    }

    @Around("execution(* com.example.administrator.androidtest.AspectJ.AspectJAct.onClickAspectJ_3())")
    public void onClickAspectJ_3Around(ProceedingJoinPoint joinPoint){
        String key = joinPoint.getSignature().toString();
        try {
            Log.e(TAG, "onClickAspectJ_3Before: " + key);
            joinPoint.proceed();
            Log.e(TAG, "onClickAspectJ_3After: " + key);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    @Pointcut("execution(@com.example.administrator.androidtest.AspectJ.AspectJAct.DebugTool * *(..))")
    public void DebugToolMethod() {
    }

    @Before("DebugToolMethod()")
    public void onDebugToolMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        Log.d(TAG, "onDebugToolMethodBefore: " + key);
    }

}
