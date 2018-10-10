package com.example.administrator.androidtest.DataBinding;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActDatabindingBinding;

/*
    控件的点击或者其他交互事件方法与属性一一对应，在xml可以直接使用lambda 表达式直接获取对应的参数值，
    当然顺序必须一致
    android:onCheckedChanged="@{(cb, isChecked) -> handler.onCompletedChanged(task, isChecked)}"
    android:onClick="@{(theview) -> handler.onTaskClickWithParams(theview, task)}"
    表达式结果有默认值 null、0、false等等
    表达式中可以使用void
    复杂的表达式会使布局难以阅读和维护，这种情况我们最好将业务逻辑写到回调函数中
    android:onClick="@{(v) -> v.isVisible() ? doSomething() : void}"
 */
public class DataBindingClick {
    private ActDatabindingBinding binding;

    private Context context;

    private ObservableUser observableUser = null;

    public DataBindingClick(ActDatabindingBinding binding) {
        this.binding = binding;
    }

    public DataBindingClick(Context context) {
        this.context = context;
    }

    /*
        dataBinding这个监听回调必须添加View参数，不然找不到匹配方法
     */
    public void onClick(View view){
        if (view.getId() == R.id.bt_use_databinding_1) {
            binding.setUser(new User("张晴", 22));
            binding.tvAge.setText("22");
        }else if (view.getId() == R.id.bt_use_databinding_2) {
            binding.setUser(new User("吴艺松", 24));
            binding.tvAge.setText("24");
        }else if (view.getId() == R.id.bt_use_databinding_3) {
            binding.setUser(new User("吴艺松", 24));
            binding.tvAge.setText("24");
        }else if(view.getId() == R.id.bt_use_databinding_4){
            binding.setUser(new User("吴艺松", 10));
            binding.tvAge.setText("10");
        }else if(view.getId() == R.id.bt_use_databinding_5){
            observableUser.setName("ZhangQing");
            observableUser.setPhone("不知道");
        }
    }

    /*
        通过databinding设置变量Text
     */
    public void onTaskClick(String text){
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }

    public DataBindingClick setBinding(ActDatabindingBinding binding) {
        this.binding = binding;
        return this;
    }

    public DataBindingClick setContext(Context context) {
        this.context = context;
        return this;
    }

    /*
        databinding使用静态方法
     */
    public static String showClickText(){
        return "I am a Button";
    }

    public DataBindingClick setObservableUser(ObservableUser observableUser) {
        this.observableUser = observableUser;
        return this;
    }
}
