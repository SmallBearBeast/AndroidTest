package com.example.administrator.androidtest.demo.OptTest.ThreadOptTest;

import android.util.Log;

import com.bear.libcommon.util.ExecutorUtil;

import java.util.Arrays;

public class ShadowThread extends Thread {
    private static final String TAG = "ShadowThread";

    @Override
    public synchronized void start() {
        // 耗时吗？获取线程堆栈
        String stackTraceStr = Arrays.toString(new Throwable().getStackTrace());
        Log.i(TAG, "start: thread name = " + getName() + ", stackTraceStr = " + stackTraceStr);
        ExecutorUtil.execute(new ShadowRunnable(getName()));
    }

    /*
        由于将任务放到线程池去执行，假如线程奔溃了，我们不知道是哪个线程出问题。
        所以自定义ShadowThread中的内部类MyRunnable 的作用是：
        在线程出现异常的时候，将异常捕获，还原它的名字，重新抛出一个信息更全的异常。
     */
    private class ShadowRunnable implements Runnable {

        private String threadName;

        public ShadowRunnable(String name) {
            threadName = name;
        }

        @Override
        public void run() {
            try {
                ShadowThread.this.run();
                Log.i(TAG, "run: thread name = " + threadName);
            } catch (Exception e) {
                Log.w(TAG, "threadName = " + threadName + " ,exception = " + e.getMessage());
                RuntimeException exception = new RuntimeException("threadName = " + threadName + " ,exception = " + e.getMessage());
                exception.setStackTrace(e.getStackTrace());
                throw exception;
            }
        }
    }
}
