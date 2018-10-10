package com.example.administrator.androidtest.DataBinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.Observable;
import android.util.Log;

import com.example.administrator.androidtest.BR;

public class ObservableUser extends BaseObservable {
    private String mName;
    private String mPhone;
    private static final String TAG = "ObservableUser";
    public ObservableUser(String name, String phone) {
        mName = name;
        mPhone = phone;
        //BaseObservable属性改变时候回调
        addOnPropertyChangedCallback(new OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                Log.d(TAG, "onPropertyChanged() called with: sender = [" + sender + "], propertyId = [" + propertyId + "]");
            }
        });
    }

    /*
        设置@Bindable标识变量，会在BR类生成一个类似于R文件的index文件
        xml通过索引BR文件获取变量值而不是直接通过BaseObservable获取
     */
    @Bindable
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
        notifyPropertyChanged(BR.phone);
    }
}
