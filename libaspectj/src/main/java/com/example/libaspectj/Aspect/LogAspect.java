package com.example.libaspectj.Aspect;

import android.util.Log;
import com.example.libaspectj.PC;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class LogAspect {
    private static final String TAG = "LogAspect";

    //args参数和执行方法的参数顺序和类型要一致，否则运行报错。
    @Before(PC.EXE_TEST + PC.AND + "args(name, age, parent)")
    public void onTestBefore(JoinPoint point, String name, int age, String parent){
        Log.i(TAG, "onClickAspectAfter: point.getThis() = " + point.getThis());
    }

    @After(PC.EXE_ONITEMCLICK)
    public void onClickAfter(JoinPoint point){
        Log.i(TAG, "onClickAspectAfter: point.getThis() = " + point.getThis());
    }

    @After(PC.CALL_ONITEMCLICK)
    public void onItemClick(JoinPoint point){
        Log.i(TAG, "onItemClick: point.getThis() = " + point.getThis());
    }

    //call结合within或者withincode确定具体切入位置
    @After("withincode(* * ..*.onItemClick(..))" + PC.AND + PC.CALL_SETBACKGROUNDCOLOR)
    public void onClickAfterAndLog(JoinPoint point){
        Log.i(TAG, "onClickAspectAfter: point.getThis() = " + point.getThis());
    }

    //使用within或者withincode打印所有pointcut
//    @After("withincode(* * ..*.testUser(..))")
//    public void onTestUser(JoinPoint point){
//        Log.i(TAG, "onTestUser: point.getSignature() = " + point.getSignature());
//    }

    @After("call(* * ..*.SLog.d(..))")
    public void onLogD(JoinPoint point){
        Log.i(TAG, "onLogD: point.getThis() = " + point.getThis());
    }

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

    // TODO: 2019-07-23 within带有注解方法不起作用
    @After(PC.WITHIN_ASLOG)
    public void withInAsLog(JoinPoint point){
        Log.i(TAG, "withInAsLog: point = " + point);
    }
}
