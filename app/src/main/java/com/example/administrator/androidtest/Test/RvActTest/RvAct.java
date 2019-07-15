package com.example.administrator.androidtest.Test.RvActTest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.*;
import android.view.View;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Base.Adapter.VHAdapter;
import com.example.administrator.androidtest.Base.Adapter.DataManager;
import com.example.administrator.androidtest.Base.Adapter.Notify;
import com.example.administrator.androidtest.Common.Rv.RvListener;
import com.example.administrator.androidtest.Log.SLog;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RvAct extends ComponentAct {
    private static final String TAG = "RvAct";
    private RecyclerView mRvTest;
    private DataManager mDataManager;
    private VHAdapter mAdapter;

    private MsgVHBinder msgVHBinder = new MsgVHBinder();

    @Override
    protected int layoutId() {
        return R.layout.act_rv_test;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mRvTest = findViewById(R.id.rv_test);
        //解决notifychange刷新问题
        ((SimpleItemAnimator) mRvTest.getItemAnimator()).setSupportsChangeAnimations(false);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRvTest.setLayoutManager(linearLayoutManager);
//        mRvTest.setHasFixedSize(true);

//        mRvTest.addItemDecoration(new RvDivider(gridLayoutManager, 20, Color.RED));
        mAdapter = new VHAdapter();
        mAdapter.register(Image.class, new ImageVHBinder());
        mAdapter.register(Info.class, new InfoVHBinder());
        mAdapter.register(Msg.class, msgVHBinder);
        mRvTest.setAdapter(mAdapter);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
//        mRvTest.setLayoutManager(gridLayoutManager);
//        mRvTest.setItemAnimator(null);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return mAdapter.getItemViewType(position) ==  msgVHBinder.getType() ? 3 : 1;
            }
        });

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);
        mRvTest.setLayoutManager(staggeredGridLayoutManager);

        getLifecycle().addObserver(mAdapter);
        mRvTest.addOnItemTouchListener(new RvListener(this, mRvTest, new RvListener.OnItemClickListener() {

            int color = Color.BLACK;
            @Override
            public boolean onItemClick(View view, int position) {
                SLog.d(TAG, "onItemClick: view = " + view + " position = " + position);
//                ToastUtil.showToast("onItemClick position = " + position);
                switch (view.getId()){
                    case R.id.tv_1:
                        if(color == Color.BLACK){
                            color = Color.RED;
                        }else {
                            color = Color.BLACK;
                        }
                        view.setBackgroundColor(color);
                        SLog.d(TAG, "onItemClick: textview = " + view + " position = " + position);
                        break;

                    case R.id.fv_1:
                        SLog.d(TAG, "onItemClick: frescoView = " + view + " position = " + position);
                        break;
                }
                return true;
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                SLog.d(TAG, "onItemLongClick: view = " + view + " position = " + position);
//                ToastUtil.showToast("onItemClick position = " + position);
                switch (view.getId()){
                    case R.id.tv_1:
                        SLog.d(TAG, "onItemLongClick: textview = " + view + " position = " + position);
                        break;

                    case R.id.fv_1:
                        SLog.d(TAG, "onItemLongClick: frescoView = " + view + " position = " + position);
                        break;
                }
                return true;
            }
        }));

        mDataManager = mAdapter.getDataProvider();
        mDataManager.setData(initImage());
//        mDataManager.addFirst(new Msg());
//        mDataManager.addFirst(new Msg());
    }

    private List<Image> initImage() {
        List<Image> images = new ArrayList<>();
        for (int i = 0; i < 28; i++) {
            images.add(new Image(i));
        }
        return images;
    }

    private List<Info> initData() {
        List<Info> infos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Info info = new Info(i);
            info.mText_1 = "Text_1: " + i;
            info.mText_2 = "Text_2: " + i;
            info.mText_3 = "Text_3: " + i;
            infos.add(info);
        }
        return infos;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_first:
                mDataManager.add(1, new Image(-1));
//                if(mAdapter.isRegister(Msg.class)){
//                    mDataManager.addFirst(new Msg());
//                }else {
//                    mAdapter.register(Msg.class, new MsgVHBinder());
//                    mDataManager.addFirst(new Msg());
//                }
//                RvUtil.scrollToTop(mRvTest, 3, 500);
                break;

            case R.id.bt_remove_first:
                mDataManager.removeFirst(1);
//                RvUtil.scrollToBottom(mRvTest,  4, 0);
                break;

            case R.id.bt_add_last:
                mDataManager.addLast(new Msg());
                break;

            case R.id.bt_remove_last:
//                RvUtil.test(mRvTest, mRvTest.getLayoutManager());
                mDataManager.removeLast(1);
                break;

            case R.id.bt_add_two:
                mDataManager.add(1, Arrays.asList(new Image(-1), new Image(-1)));
                break;

            case R.id.bt_remove_two:
                mDataManager.remove(1, 2);
                break;

            case R.id.bt_update:
                Image image = new Image(2);
                image.mUrl_1 = "http://www.badcookie.com/ku-xlarge.gif";
                mDataManager.update(image);
                break;

            case R.id.bt_partial_update:
                Info partInfo = new Info(2);
                partInfo.mUrl_1 = "http://www.badcookie.com/ku-xlarge.gif";
                partInfo.mUrl_2 = "http://www.badcookie.com/ku-xlarge.gif";
                partInfo.mUrl_3 = "http://www.badcookie.com/ku-xlarge.gif";
                mDataManager.update(partInfo, new Notify(111));
                break;

        }
    }
}
