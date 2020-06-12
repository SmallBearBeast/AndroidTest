package com.example.administrator.androidtest.Common.CaseHelper;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CaseView extends FrameLayout {
//    private ImageView caseViewCoverIv;
//    private TextView caseViewTitleTv;
//    private UIButton caseViewBt;
//    private ProgressBar caseViewLoadingPb;
    private View contentView;
    private @CaseInfo.Type int type;

    public CaseView(@NonNull Context context) {
        this(context, null);
    }

    public CaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
//        contentView = LayoutInflater.from(context).inflate(R.layout.layout_case_view, null);
//        caseViewCoverIv = contentView.findViewById(R.id.iv_case_view_cover);
//        caseViewTitleTv = contentView.findViewById(R.id.tv_case_view_title);
//        caseViewBt = contentView.findViewById(R.id.bt_case_view_button);
//        caseViewLoadingPb = contentView.findViewById(R.id.pb_case_view_loading);
        setVisibility(GONE);
    }

    public void show(@CaseInfo.Type int type) {
        this.type = type;
        setVisibility(VISIBLE);
        contentView.setVisibility(VISIBLE);
        if (contentView.getParent() == null) {
            addView(contentView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        setUpView(type);
    }

    private void setUpView(int type) {
//        caseViewLoadingPb.setVisibility(View.GONE);
//        caseViewCoverIv.setVisibility(View.GONE);
//        caseViewBt.setVisibility(View.GONE);
//        caseViewTitleTv.setVisibility(View.GONE);
        List<CaseInfo> caseInfoList = CaseInfo.sCaseInfoList;
        for (CaseInfo caseInfo : caseInfoList) {
            if (caseInfo.type() == type) {
//                if (type == CaseInfo.CASE_TYPE_LOADING) {
//                    caseViewLoadingPb.setVisibility(View.VISIBLE);
//                } else {
//                    if (caseInfo.cover() != -1) {
//                        SafeUtil.loadImageForView(getContext(), caseInfo.cover(), caseViewCoverIv);
//                        caseViewCoverIv.setVisibility(View.VISIBLE);
//                    }
//                    if (caseInfo.title() != -1) {
//                        caseViewTitleTv.setText(caseInfo.title());
//                        caseViewTitleTv.setVisibility(View.VISIBLE);
//                    }
//                    if (caseInfo.buttonText() != -1) {
//                        caseViewBt.setText(caseInfo.buttonText());
//                        caseViewBt.setVisibility(View.VISIBLE);
//                    }
//                }
                break;
            }
        }
    }

    public void hide() {
        if (contentView.getParent() == this) {
            removeView(contentView);
        }
        setVisibility(GONE);
        contentView.setVisibility(GONE);
    }

    public void setListener(final View.OnClickListener listener, int... types) {
        for (int i = 0; i < types.length; i++) {
            if (types[i] == CaseInfo.CASE_TYPE_NETWORK_ERROR) {
                setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (type == CaseInfo.CASE_TYPE_NETWORK_ERROR) {
                            listener.onClick(CaseView.this);
                        }
                    }
                });
            } else {
//                caseViewBt.clickListener(new OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (type != CaseInfo.CASE_TYPE_NETWORK_ERROR) {
//                            listener.onClick(caseViewBt);
//                        }
//                    }
//                });
            }
        }
    }

    public int getType() {
        return type;
    }
}
