package com.example.administrator.androidtest.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// TODO: 2019-06-09 使用mmfUtil实现本地存储
public class FileLog implements ILog {
    private LogConfig mConfig = new LogConfig();
    private List<String> mLogList = new LinkedList<>();

    @Override
    public void i(String tag, String msg) {

    }

    @Override
    public void d(String tag, String msg) {

    }

    @Override
    public void e(String tag, String msg) {

    }

    @Override
    public void v(String tag, String msg) {

    }

    @Override
    public void w(String tag, String msg) {

    }

    @Override
    public void i(String tag, String msg, Throwable tr) {

    }

    @Override
    public void d(String tag, String msg, Throwable tr) {

    }

    @Override
    public void e(String tag, String msg, Throwable tr) {

    }

    @Override
    public void v(String tag, String msg, Throwable tr) {

    }

    @Override
    public void w(String tag, String msg, Throwable tr) {

    }

    private void logToFile(){

    }

    private void logToNet(){

    }

}
