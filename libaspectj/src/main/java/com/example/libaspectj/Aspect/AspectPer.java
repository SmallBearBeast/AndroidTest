package com.example.libaspectj.Aspect;

import com.example.libaspectj.Annotation.AsPer;
import com.example.libbase.Util.PermissionUtil;
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
    public Object check(final ProceedingJoinPoint point, AsPer ann) throws Throwable {
        if(ann != null){
            String[] permissions = ann.permissions();
            if(permissions.length > 0) {
                if(PermissionUtil.requestPermissions(permissions)){
                    return point.proceed();
                }
            }
        }
        return null;
    }

}
