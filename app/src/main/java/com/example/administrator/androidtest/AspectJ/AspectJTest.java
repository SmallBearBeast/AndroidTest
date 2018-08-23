package com.example.administrator.androidtest.AspectJ;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * Created by Administrator on 2018/8/23.
 */

@Aspect
public class AspectJTest {
    private static final String TAG = "AspectJTest";

    @Before("execution(* com.example.administrator.androidtest.AspectJ.AspectJAct.init())")
    public void onActivityMethodBefore(JoinPoint joinPoint){
        String key = joinPoint.getSignature().toString();
        Log.e(TAG, "onActivityMethodBefore: " + key);
    }
}
