package com.example.administrator.androidtest.Common.CaseHelper;

import androidx.annotation.DrawableRes;
import androidx.annotation.IntDef;
import androidx.annotation.StringRes;

import java.util.ArrayList;
import java.util.List;

public abstract class CaseInfo {
    public static final int CASE_TYPE_LOADING = 1;
    public static final int CASE_TYPE_NETWORK_ERROR = CASE_TYPE_LOADING + 1;
    public static final int CASE_TYPE_FRIEND_EMPTY = CASE_TYPE_NETWORK_ERROR + 1;
    public static final int CASE_TYPE_ROOM_EMPTY = CASE_TYPE_FRIEND_EMPTY + 1;
    public static final int CASE_TYPE_TALK_EMPTY = CASE_TYPE_ROOM_EMPTY + 1;
    public static final int CASE_TYPE_REC_EMPTY = CASE_TYPE_TALK_EMPTY + 1;

    @IntDef({CASE_TYPE_LOADING, CASE_TYPE_NETWORK_ERROR, CASE_TYPE_FRIEND_EMPTY,
            CASE_TYPE_ROOM_EMPTY, CASE_TYPE_TALK_EMPTY, CASE_TYPE_REC_EMPTY})
    @interface Type {

    }

    static List<CaseInfo> sCaseInfoList = new ArrayList<>();
    static {
//        sCaseInfoList.add(new NetErrorInfo());
//        sCaseInfoList.add(new FriendEmptyInfo());
//        sCaseInfoList.add(new LoadingInfo());
//        sCaseInfoList.add(new RoomEmptyInfo());
//        sCaseInfoList.add(new TalkEmptyInfo());
//        sCaseInfoList.add(new RecEmptyInfo());
    }

    @DrawableRes
    int cover() {
        return -1;
    }

    @StringRes
    int title() {
        return -1;
    }

    @StringRes
    int buttonText() {
        return -1;
    }

    @Type
    abstract int type();
}
