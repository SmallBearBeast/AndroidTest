package com.example.administrator.androidtest;

import android.view.View;

import java.util.HashMap;
import java.util.Map;

public class ViewSet {
    private View contentView;
    private Map<Integer, Runnable> viewIdWithClickMap = new HashMap<>();

    public ViewSet(View contentView) {
        this.contentView = contentView;
        initView(contentView);
    }

    protected void initView(View contentView) {
        //findViewById()操作
    }

    public void setViewClickListener(View view, Runnable click){
        if(view.getId() != View.NO_ID){
            viewIdWithClickMap.put(view.getId(), click);
            view.setOnClickListener(onClickListener);
        }
    }

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Runnable click = viewIdWithClickMap.get(v.getId());
            if(click != null){
                click.run();
            }
        }
    };
}
