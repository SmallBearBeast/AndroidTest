package com.example.libbase;

public class OnceAction {
    private boolean mIsDone = false;
    private Runnable mAction;

    public OnceAction(Runnable action) {
        mAction = action;
    }

    public void doOnceAction() {
        if (!mIsDone) {
            mIsDone = true;
            if (mAction != null) {
                mAction.run();
            }
        }
    }
}
