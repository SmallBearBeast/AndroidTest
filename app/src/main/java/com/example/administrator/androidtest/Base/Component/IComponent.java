package com.example.administrator.androidtest.Base.Component;

import android.content.Intent;

public interface IComponent {
    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onDestory();
}
