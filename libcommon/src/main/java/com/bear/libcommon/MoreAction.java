package com.bear.libcommon;

public class MoreAction {
    private Runnable mAction;
    private int mCount;

    public MoreAction(int count, Runnable action) {
        if (count <= 0) {
            throw new RuntimeException("count must > 0");
        }
        mCount = count;
        mAction = action;
    }

    public void doAction() {
        if (mCount > 0) {
            mCount --;
            if (mAction != null) {
                mAction.run();
            }
        } else {
            mAction = null;
        }
    }
}
