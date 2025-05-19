package com.example.libaspectj.Aspect;

import android.util.Log;
import com.example.libaspectj.PC;
import com.example.libcommon.Util.CollectionUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Objects;

@Aspect
public class AspectLog {
    private static final String TAG = "AspectLog";

    //args参数和执行方法的参数顺序和类型要一致，否则运行报错。
//    @Before(PC.EXE_TEST + PC.AND + "args(name, age, parent)")
//    public void onTestBefore(JoinPoint point, String name, int age, String parent){
//        Log.i(TAG, "onClickAspectAfter: point.getThis() = " + point.getThis());
//    }


//    @After(PC.CALL_ONITEMCLICK)
//    public void onItemClick(JoinPoint point){
//        Log.i(TAG, "onItemClick: point.getThis() = " + point.getThis());
//    }

    //call结合within或者withincode确定具体切入位置
//    @After("withincode(* * ..*.onItemClick(..))" + PC.AND + PC.CALL_SETBACKGROUNDCOLOR)
//    public void onClickAfterAndLog(JoinPoint point){
//        Log.i(TAG, "onClickAspectAfter: point.getThis() = " + point.getThis());
//    }

    //使用within或者withincode打印所有pointcut
//    @After("withincode(* * ..*.testUser(..))")
//    public void onTestUser(JoinPoint point){
//        Log.i(TAG, "onTestUser: point.getSignature() = " + point.getSignature());
//    }

//    @After("call(* * ..*.SLog.d(..))")
//    public void onLogD(JoinPoint point){
//        Log.i(TAG, "onLogD: point.getThis() = " + point.getThis());
//    }

    //*..AA 和..AA是一样表示任意包名任意后缀是AA的类，..*.AA表示任意包名下面AA的类，前者范围大于后者。
    @Before("set(* * ..RvAct.mDataManager)")
    public void setDataManagerBefore(JoinPoint point){
        Log.i(TAG, "setDataManager: point");
    }

    //和上面同时启动会崩溃。
//    @After("set(* * ..*.RvAct.mDataManager)")
//    public void setDataManagerAfter(JoinPoint point){
//        Log.i(TAG, "setDataManager: point");
//    }

//    @After(PC.WITHIN_ASLOG)
//    public void withInAsLog(JoinPoint point){
//        Log.i(TAG, "withInAsLog: point = " + point);
//    }

    @After(PC.EXE_ASLOG)
    public void exe_AsLog(JoinPoint point){
        Object[] objs = point.getArgs();
        if(!CollectionUtil.isEmpty(objs)){
            Log.i(TAG, "exe_AsLog: " + objs[0]);
        }
    }


    @After(PC.EXE_ONCREATE)
    public void EXE_ONCREATE(JoinPoint point){
        Log.i(TAG, "onCreate");
    }

    @After(PC.EXE_ONSTART)
    public void EXE_ONSTART(JoinPoint point){
        Log.i(TAG, "onStart");
    }

    @After(PC.EXE_ONRESMUE)
    public void EXE_ONRESMUE(JoinPoint point){
        Log.i(TAG, "onResume");
    }

    @After(PC.EXE_ONPAUSE)
    public void EXE_ONPAUSE(JoinPoint point){
        Log.i(TAG, "onPause");
    }

    @After(PC.EXE_ONSTOP)
    public void EXE_ONSTOP(JoinPoint point){
        Log.i(TAG, "onStop");
    }

    @After(PC.EXE_ONDESTROY)
    public void EXE_ONDESTROY(JoinPoint point){
        Log.i(TAG, "onDestroy");
    }
}
