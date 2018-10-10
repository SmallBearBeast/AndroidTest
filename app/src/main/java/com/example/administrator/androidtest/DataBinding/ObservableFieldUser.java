package com.example.administrator.androidtest.DataBinding;

import android.databinding.ObservableField;

public class ObservableFieldUser {
    public ObservableField<String> mName = new ObservableField<>();
    public ObservableField<String> mPhone = new ObservableField<>();

    public ObservableFieldUser(String name, String phone) {
        mName.set(name);
        mPhone.set(phone);
    }
}
