package com.example.administrator.androidtest.Test.RvActTest;

import android.support.annotation.NonNull;
import android.view.View;
import com.example.administrator.androidtest.Base.Adapter.VHBridge;
import com.example.administrator.androidtest.Base.Adapter.VHolder;
import com.example.administrator.androidtest.R;

public class MsgVHBinder extends VHBridge<MsgVHBinder.MsgVHolder> {
    @NonNull
    @Override
    protected MsgVHolder onCreateViewHolder(@NonNull View itemView) {
        return new MsgVHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_rv_msg;
    }

    class MsgVHolder extends VHolder<Msg>{
        public MsgVHolder(View itemView) {
            super(itemView);
        }
    }
}
