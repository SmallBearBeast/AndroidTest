package com.example.libaspectj.Aspect;

import android.os.SystemClock;
import com.example.libbase.Util.ToastUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class AspectTime {
    private static final String EXE_TIME = "execution(@com.example.libaspectj.Annotation.AsTime * * ..*(..))";

    @Around(EXE_TIME)
    public Object check(ProceedingJoinPoint point) throws Throwable {
        long startTime = SystemClock.elapsedRealtime();
        Object obj = point.proceed();
        long endTime = SystemClock.elapsedRealtime();
        long spendTime = endTime - startTime;
        //use spendTime to do something
        ToastUtil.showToast(spendTime + "");
        return obj;
    }
}
