package com.example.administrator.androidtest.DataBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.administrator.androidtest.Base.ActAndFrag.BaseAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActDatabindingBinding;

/*
    ViewStub需要再inflate回调这边重新绑定databinding
    对于adapter，可以在ViewHolder持有databinding
    当binding设置变量需要马上刷新时候应该调用executePendingBindings
 */
public class DataBindingAct extends BaseAct {
    private ActDatabindingBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ObservableUser observableUser = new ObservableUser("Wuyisong", "18059692660");
        binding = DataBindingUtil.setContentView(this, R.layout.act_databinding);
        binding.setUser(new User("吴艺松", 22));
        binding.tvAge.setText("24");
        binding.setDataBindingClick(new DataBindingClick(binding).setContext(this).setObservableUser(observableUser));
        binding.setText("I am good man");
        binding.setObservableUser(observableUser);
        binding.setObservableFieldUser(new ObservableFieldUser("LiYao", "18860819656"));
    }

    @Override
    protected int layoutId() {
        return 0;
    }
}
