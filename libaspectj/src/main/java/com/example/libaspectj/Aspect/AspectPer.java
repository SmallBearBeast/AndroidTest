package com.example.libaspectj.Aspect;

import android.support.v4.app.ActivityCompat;
import com.example.libaspectj.Annotation.AsPer;
import com.example.libbase.Util.AppInitUtil;
import com.example.libbase.Util.EnvUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class AspectPer {
    private static final String TAG = "AspectPer";
    private static final String ANN_PER = "@annotation(ann)";
    private static final String EXE_PER = "execution(@com.example.libaspectj.Annotation.AsPer * * ..*(..))";

    @Pointcut(EXE_PER + "&&" + ANN_PER)
    public void checkPer(AsPer ann){

    }

    @Around("checkPer(ann)")
    public void check(final ProceedingJoinPoint point, AsPer ann) {
        if(ann != null){
            String[] permissions = ann.permissions();
        }
    }

}
