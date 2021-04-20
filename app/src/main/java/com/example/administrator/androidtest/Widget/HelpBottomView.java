package com.example.administrator.androidtest.Widget;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

/**
 * 管理BottomView优先级，回退事件，处理与输入法弹出冲突。
 */
public class HelpBottomView extends BottomView {
    /**
     * PRIORITY_CONFIRM > PRIORITY_NOTIFY > PRIORITY_OTHER
     * 同级互斥，高优先级别可覆盖在低优先级别上面。
     * PRIORITY_CONFIRM级别弹出需要当前处理完才可以从队列显示其他弹窗。
     */
    protected static final int PRIORITY_CONFIRM = 1;
    protected static final int PRIORITY_NOTIFY = 2;
    protected static final int PRIORITY_OTHER = 3;

    private static HelpBottomView lastHelpBottomView;
    private static Map<Activity, Stack<HelpBottomView>> showRoomBottomViewStackMap = new HashMap<>(); // 已经展示栈
    private static Map<Activity, Queue<HelpBottomView>> showRoomBottomViewQueueMap = new HashMap<>(); // 即将展示队列

    public HelpBottomView(Activity activity) {
        super(activity);
        setUp();
    }

    /**
     * 处理按下Back键，RoomBottomView行为。
     */
    public static boolean onBackPressed(Activity activity) {
        Stack<HelpBottomView> stack = showRoomBottomViewStackMap.get(activity);
        if (stack != null && stack.size() > 0) {
            stack.peek().hide();
            return true;
        }
        return false;
    }

    private void setUp() {
        if (!showRoomBottomViewStackMap.containsKey(getActivity()) && !showRoomBottomViewQueueMap.containsKey(getActivity()) && getActivity() instanceof AppCompatActivity) {
            showRoomBottomViewQueueMap.put(getActivity(), new LinkedList<HelpBottomView>());
            showRoomBottomViewStackMap.put(getActivity(), new Stack<HelpBottomView>());
            AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
            appCompatActivity.getLifecycle().addObserver(new LifecycleEventObserver() {
                @Override
                public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        source.getLifecycle().removeObserver(this);
                        lastHelpBottomView = null;
                        showRoomBottomViewQueueMap.remove(getActivity());
                        showRoomBottomViewStackMap.remove(getActivity());
                    }
                }
            });
//            SoftKeyBoardTool.observeKeyBoard(getActivity(), (showKeyBoard, bottomOffset) -> {
//                if (showKeyBoard) {
//                    // 输入法弹出时候隐藏所有弹窗
//                    Stack<HelpBottomView> stack = showRoomBottomViewStackMap.get(getActivity());
//                    if (stack != null) {
//                        int size = stack.size();
//                        for (int i = 0; i < size; i++) {
//                            HelpBottomView helpBottomView = stack.get(i);
//                            if (!helpBottomView.supportEdit()) {
//                                helpBottomView.hide();
//                            }
//                        }
//                    }
//                } else {
//                    // 输入法隐藏时候如果还有可弹弹窗继续弹出
//                    HelpBottomView helpBottomView = queuePoll();
//                    if (helpBottomView != null) {
//                        helpBottomView.show();
//                    }
//                }
//            });
        }
    }

    @Override
    public void show() {
        queueAdd();
//        if (SoftKeyBoardTool.isShowKeyBoard(getActivity())) {
//            return;
//        }
        if (lastHelpBottomView == null || !lastHelpBottomView.isShow()) {
            HelpBottomView helpBottomView = queuePoll();
            if (helpBottomView != null) {
                helpBottomView.realShow();
            }
        } else {
            HelpBottomView helpBottomView = queuePoll();
            // 同一个BottomView对象不处理，直接返回。
            if (helpBottomView == lastHelpBottomView) {
                return;
            }
            if (helpBottomView != null) {
                if (lastHelpBottomView.priority() == PRIORITY_CONFIRM) {
                    queueAdd();
                } else if (lastHelpBottomView.priority() == priority()) {
                    lastHelpBottomView.hide();
                    helpBottomView.realShow();
                } else {
                    helpBottomView.realShow();
                }
            }
        }
    }

    private HelpBottomView queuePoll() {
        Queue<HelpBottomView> queue = showRoomBottomViewQueueMap.get(getActivity());
        if (queue != null && queue.size() > 0) {
            return queue.poll();
        }
        return null;
    }

    private void queueAdd() {
        Queue<HelpBottomView> queue = showRoomBottomViewQueueMap.get(getActivity());
        if (queue != null) {
            queue.add(this);
        }
    }

    @Override
    public void hide() {
        super.hide();
        HelpBottomView helpBottomView = queuePoll();
        if (helpBottomView != null) {
            helpBottomView.show();
        }
        Stack<HelpBottomView> stack = showRoomBottomViewStackMap.get(getActivity());
        if (stack != null && stack.size() > 0) {
            stack.pop();
        }
    }

    private void realShow() {
        super.show();
        lastHelpBottomView = this;
        Stack<HelpBottomView> stack = showRoomBottomViewStackMap.get(getActivity());
        if (stack != null) {
            stack.push(this);
        }
    }

    protected int priority() {
        return PRIORITY_OTHER;
    }

    // BottomView需要支持edit弹出输入法的重写为true
    protected boolean supportEdit() {
        return false;
    }
}
