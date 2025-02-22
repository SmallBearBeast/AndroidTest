package com.example.administrator.androidtest.demo.WidgetDemo.CaseViewTest.Case;

import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;

import com.example.libbase.Util.DensityUtil;

import java.util.HashMap;
import java.util.Map;

public class CaseHelper {
    public static final int CASE_TYPE_NONE = -1;
    public static final int CASE_TYPE_LOADING = 1;
    public static final int CASE_TYPE_NETWORK_ERROR = CASE_TYPE_LOADING + 1;
    public static final int CASE_TYPE_FRIEND_EMPTY = CASE_TYPE_NETWORK_ERROR + 1;
    public static final int CASE_TYPE_ROOM_EMPTY = CASE_TYPE_FRIEND_EMPTY + 1;
    public static final int CASE_TYPE_TALK_EMPTY = CASE_TYPE_ROOM_EMPTY + 1;
    public static final int CASE_TYPE_REC_EMPTY = CASE_TYPE_TALK_EMPTY + 1;

    @IntDef({CASE_TYPE_LOADING, CASE_TYPE_NETWORK_ERROR, CASE_TYPE_FRIEND_EMPTY,
            CASE_TYPE_ROOM_EMPTY, CASE_TYPE_TALK_EMPTY, CASE_TYPE_REC_EMPTY, CASE_TYPE_NONE})
    @interface Type {

    }

    private static final Map<Integer, CaseInfo> caseInfoMap = new HashMap<>();

    static {
        init();
    }

    private static void init() {
        register(CASE_TYPE_LOADING, new LoadingCaseInfo());
    }

    private static void register(@CaseHelper.Type int type, @NonNull CaseInfo caseInfo) {
        if (caseInfoMap.containsKey(type)) {
            throw new RepeatedTypeException();
        }
        caseInfoMap.put(type, caseInfo);
    }

    static CaseInfo get(int type) {
        return caseInfoMap.get(type);
    }

    public static void showTestCaseView(@NonNull CaseView caseView, View.OnClickListener listener) {
        caseView.show(CASE_TYPE_LOADING, view -> {
            ProgressBar progressBar = view.getCaseProgressBar();
            if (progressBar != null) {
                progressBar.getLayoutParams().width = DensityUtil.dp2Px(60);
                progressBar.getLayoutParams().height = DensityUtil.dp2Px(60);
            }
            caseView.getCaseClickBt().setOnClickListener(listener);
        });
    }

    public static void hide(@NonNull CaseView caseView) {
        caseView.hide();
    }

    private static class RepeatedTypeException extends RuntimeException {
        public RepeatedTypeException() {
            super("Repeated type");
        }
    }
}
