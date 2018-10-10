package com.example.administrator.androidtest.DataBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.administrator.androidtest.BaseAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActDatabindingBinding;

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
}
