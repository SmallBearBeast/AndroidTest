package com.example.libaspectj.Aspect;

import android.util.Log;
import com.example.libaspectj.Annotation.Env.AsEnv;
import com.example.libaspectj.Annotation.Env.EnvMode;
import com.example.libcommon.util.EnvUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectEnv {
    private static final String TAG = "AspectEnv";
    private static final String ANN_ENV = "@annotation(ann)";
    private static final String EXE_ENV = "execution(@com.example.libaspectj.Annotation.Env.AsEnv * * ..*(..))";

    @Pointcut(EXE_ENV + "&&" + ANN_ENV)
    public void checkEnv(AsEnv ann){

    }

    @Around("checkEnv(ann)")
    public Object check(final ProceedingJoinPoint point, AsEnv ann) throws Throwable {
        Log.i(TAG, "check: point = " + point + " ann = " + ann);
        if(ann != null){
            int env = ann.env();
            if(env == EnvMode.DEBUG && EnvUtil.isAppEnv(EnvUtil.DEBUG)){
                return point.proceed();
            }else if(env == EnvMode.RELEASE && EnvUtil.isAppEnv(EnvUtil.RELEASE)){
                return point.proceed();
            }else if(env == EnvMode.ALPHA && EnvUtil.isAppEnv(EnvUtil.ALPHA)){
                return point.proceed();
            }
        }
        return null;
    }
}
