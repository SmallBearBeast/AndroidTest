package com.example.administrator.androidtest.demo.optdemo.bootoptdemo;

import android.util.Log;

import androidx.annotation.NonNull;

import com.effective.android.anchors.AnchorsManager;
import com.effective.android.anchors.task.Task;
import com.effective.android.anchors.task.listener.TaskListener;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.AnchorDPOptTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.AnchorDPTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.BaseBootTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.BasicBgThreadTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.BootConstant;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.ImmediateTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.MainThreadTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.NoAnchorDPOptTask;
import com.example.administrator.androidtest.demo.optdemo.bootoptdemo.boottask.NoAnchorDPTask;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BootTaskManager {

    private static final String TAG = "BootTaskManager";
    private CountDownLatch countDownLatch;

    public static BootTaskManager getInstance() {
        return SingleTon.instance;
    }

    private BootTaskManager() {

    }

    public void init() {
        countDownLatch = new CountDownLatch(2);
        AnchorsManager anchorsManager = AnchorsManager.getInstance(createExecutor())
                .debuggable(true)
                .addAnchor(BootConstant.TASK_TYPE_MAINTHREAD);
        anchorsManager.start(createRootTask());
//        LockableAnchor lockableAnchor = anchorsManager.requestBlockWhenFinish(new BlockTask());
//        lockableAnchor.unlock();
    }

    private ExecutorService createExecutor() {
        int cpuCount = Runtime.getRuntime().availableProcessors();
        int corePoolSize = Math.max(4, Math.min(cpuCount - 1, 8));
        int maximumPoolSize = corePoolSize * 2 + 1;
        long keepLivesSecond = 30L;
        BlockingQueue<Runnable> blockingQueue = new PriorityBlockingQueue<>(128);
        ThreadFactory threadFactory = new ThreadFactory() {
            private final AtomicInteger count = new AtomicInteger(1);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r, "Boot Thread #" + count.getAndIncrement());
                // 由于存在后台Task运行的情况，后台线程优先级比较低，不容易获取到cpu资源，因此后台线程的优先级设置到最大(Thead.MAX_PRIORITY)，从而加快启动任务的处理。
                thread.setPriority(Thread.MAX_PRIORITY);
                return thread;
            }
        };
        return new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepLivesSecond, TimeUnit.SECONDS, blockingQueue, threadFactory);
    }

    private Task createRootTask() {
        BaseBootTask rootBootTask = new ImmediateTask();
        MainThreadTask mainThreadTask = new MainThreadTask();
        BasicBgThreadTask basicBgThreadTask = new BasicBgThreadTask();

        AnchorDPTask anchorDPTask = new AnchorDPTask();
        anchorDPTask.addTaskListener(new TaskListenerWrapper() {
            @Override
            public void onRelease(@NonNull Task task) {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                Log.d(TAG, "onRelease: AnchorDPTask");
            }
        });
        AnchorDPOptTask anchorDPOptTask = new AnchorDPOptTask();
        anchorDPOptTask.addTaskListener(new TaskListenerWrapper() {
            @Override
            public void onRelease(@NonNull Task task) {
                if (countDownLatch != null) {
                    countDownLatch.countDown();
                }
                Log.d(TAG, "onRelease: AnchorDPOptTask");
            }
        });

        NoAnchorDPTask noAnchorDPTask = new NoAnchorDPTask();
        NoAnchorDPOptTask noAnchorDPOptTask = new NoAnchorDPOptTask();

        rootBootTask.behind(mainThreadTask);
        rootBootTask.behind(basicBgThreadTask);

        basicBgThreadTask.behind(anchorDPTask);
        basicBgThreadTask.behind(anchorDPOptTask);
        basicBgThreadTask.behind(noAnchorDPTask);
        basicBgThreadTask.behind(noAnchorDPOptTask);

        return rootBootTask;
    }

    public void waitCountDown() {
        try {
            if (countDownLatch != null) {
                countDownLatch.await();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static class SingleTon {
        private static final BootTaskManager instance = new BootTaskManager();
    }

    private static class TaskListenerWrapper implements TaskListener {

        @Override
        public void onFinish(@NonNull Task task) {

        }

        @Override
        public void onRelease(@NonNull Task task) {

        }

        @Override
        public void onRunning(@NonNull Task task) {

        }

        @Override
        public void onStart(@NonNull Task task) {

        }
    }
}
