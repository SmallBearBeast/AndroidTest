package com.example.libaspectj;

public class PC {
    public static final String AND = "&&";
    public static final String NOR = "!";
    public static final String OR = "||";

    public static final String EXE_ONCLICK = "execution(* * ..*.onClick(..))";
    public static final String EXE_TEST = "execution(* * ..*.test(..))";
    public static final String EXE_ONITEMCLICK = "execution(* * ..*.onItemClick(..))";
    public static final String CALL_ONITEMCLICK = "call(* * ..*.onItemClick(..))";
    public static final String CALL_SETBACKGROUNDCOLOR = "call(* * ..*.setBackgroundColor(..))";
    public static final String WITHIN_ASLOG = "within(@com.example.libaspectj.Annotation.AsLog *)";

    public static final String EXE_ASLOG = "execution(@com.example.libaspectj.Annotation.AsLog * * ..*(..))";

    public static final String EXE_ONCREATE = "execution(* * ..*.FragmentActivity.onCreate(..))";
    public static final String EXE_ONSTART = "execution(* * ..*.FragmentActivity.onStart(..))";
    public static final String EXE_ONRESMUE = "execution(* * ..*.FragmentActivity.onResume(..))";
    public static final String EXE_ONPAUSE = "execution(* * ..*.FragmentActivity.onPause(..))";
    public static final String EXE_ONSTOP = "execution(* * ..*.FragmentActivity.onStop(..))";
    public static final String EXE_ONDESTROY = "execution(* * ..*.FragmentActivity.onDestroy(..))";

    // TODO: 2019-07-23 一些生命周期的PointCut

    public static final String ARG_ = "args()";
}
