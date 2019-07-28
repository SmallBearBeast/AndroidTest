package com.example.libaspectj.Aspect;

import android.os.SystemClock;
import com.example.libaspectj.Annotation.AsClick;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

//使用注解时候必须要有属性值，不然ann = null
@Aspect
public class AspectClick {
    private static long mLastClickTime = 0;
    private static final String ASCLICK = "@annotation(ann)";
    private static final String EXE_ONCLICK = "execution(@com.example.libaspectj.Annotation.AsClick * * ..*.onClick(..))";

    @Pointcut(EXE_ONCLICK + "&&" + ASCLICK)
    public void checkClick(AsClick ann){
        
    }

    @Around("checkClick(ann)")
    public Object check(ProceedingJoinPoint point, AsClick ann) throws Throwable {
        if(ann != null){
            int interval = ann.interval();
            if(SystemClock.elapsedRealtime() - mLastClickTime < interval){
                return null;
            }
            mLastClickTime = SystemClock.elapsedRealtime();
        }
        return point.proceed();
    }
}
