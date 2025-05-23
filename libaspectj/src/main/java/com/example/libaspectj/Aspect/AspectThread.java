package com.example.libaspectj.Aspect;

import android.util.Log;
import com.example.libaspectj.Annotation.Thread.AsThread;
import com.example.libaspectj.Annotation.Thread.ThreadMode;
import com.example.libcommon.util.ExecutorUtil;
import com.example.libcommon.util.MainHandlerUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectThread {
    private static final String TAG = "AspectThread";
    private static final String ANN_THREAD = "@annotation(ann)";
    private static final String EXE_THREAD = "execution(@com.example.libaspectj.Annotation.Thread.AsThread * * ..*(..))";

    @Pointcut(EXE_THREAD + "&&" + ANN_THREAD)
    public void checkThread(AsThread ann){

    }

    @Around("checkThread(ann)")
    public void check(final ProceedingJoinPoint point, AsThread ann) {
        Log.i(TAG, "check: point = " + point + " ann = " + ann);
        if(ann != null){
            int mode = ann.mode();
            if(mode == ThreadMode.UI){
                MainHandlerUtil.post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            point.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
            }else if(mode == ThreadMode.WORK){
                ExecutorUtil.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            point.proceed();
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}
