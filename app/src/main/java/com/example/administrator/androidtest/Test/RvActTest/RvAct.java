package com.example.administrator.androidtest.Test.RvActTest;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bear.libcomponent.component.ComponentAct;
import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.librv.Payload;
import com.bear.librv.RvListener;
import com.bear.librv.RvUtil;
import com.example.administrator.androidtest.R;
import com.example.liblog.SLog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RvAct extends ComponentAct implements View.OnClickListener{
    private static final String TAG = "RvAct";
    private RecyclerView mRvTest;
    private MultiItemChanger mMultiItemChanger;
    private MultiTypeAdapter mAdapter;

    private MsgDelegate msgDelegate = new MsgDelegate();

    @Override
    protected int layoutId() {
        return R.layout.act_rv_test;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRvTest = findViewById(R.id.rv_test);
        //解决notifychange刷新问题
//        ((SimpleItemAnimator) mRvTest.getItemAnimator()).setSupportsChangeAnimations(false);
        mRvTest.setItemAnimator(null);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRvTest.setLayoutManager(linearLayoutManager);
//        mRvTest.setHasFixedSize(true);

        mAdapter = new MultiTypeAdapter(getLifecycle());
        mAdapter.register(Image.class, new ImageDelegate());
        mAdapter.register(Info.class, new InfoDelegate());
        mAdapter.register(Msg.class, msgDelegate);
        mRvTest.setAdapter(mAdapter);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
//        mRvTest.setLayoutManager(gridLayoutManager);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mAdapter.getItemViewType(position) ==  msgVHBinder.getType() ? 3 : 1;
//            }
//        });
//        mRvTest.addItemDecoration(new RvDivider(gridLayoutManager, 20, 40));

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);
        mRvTest.setLayoutManager(staggeredGridLayoutManager);
        mRvTest.addOnItemTouchListener(new RvListener(this, mRvTest, new RvListener.OnItemClickListener() {

            int color = Color.BLACK;
            @Override
            public boolean onItemClick(View view, int position) {
                SLog.d(TAG, "onItemClick: view = " + view + " position = " + position);
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
        mMultiItemChanger = mAdapter.getChanger();
        mMultiItemChanger.setItems(initImage());
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_first:
//                mDataManager.addFirst(new Image(-1));
//                if(mAdapter.isRegister(Msg.class)){
//                    mDataManager.addFirst(new Msg());
//                }else {
//                    mAdapter.register(Msg.class, new MsgVHBinder());
//                    mDataManager.addFirst(new Msg());
//                }
//                RvUtil.scrollToTop(mRvTest, 3, 500);
                RvUtil.scrollToTop(mRvTest, true);
                break;

            case R.id.bt_remove_first:
//                mDataManager.removeFirst(1);
//                RvUtil.scrollToBottom(mRvTest,  4, 0);
                mRvTest.smoothScrollToPosition(mMultiItemChanger.size());
                break;

            case R.id.bt_add_last:
                mMultiItemChanger.addLast(new Msg());
                break;

            case R.id.bt_remove_last:
//                RvUtil.test(mRvTest, mRvTest.getLayoutManager());
                mMultiItemChanger.removeLast(1);
                break;

            case R.id.bt_add_two:
                mMultiItemChanger.addAll(1, Arrays.asList(new Image(-1), new Image(-1)));
                break;

            case R.id.bt_remove_two:
                mMultiItemChanger.remove(1, 2);
                break;

            case R.id.bt_update:
                Image image = new Image(2);
                image.mUrl_1 = "http://www.badcookie.com/ku-xlarge.gif";
                mMultiItemChanger.update(image);
                break;

            case R.id.bt_partial_update:
                Info partInfo = new Info(2);
                partInfo.mUrl_1 = "http://www.badcookie.com/ku-xlarge.gif";
                partInfo.mUrl_2 = "http://www.badcookie.com/ku-xlarge.gif";
                partInfo.mUrl_3 = "http://www.badcookie.com/ku-xlarge.gif";
                mMultiItemChanger.update(partInfo, new Payload(111));
                break;

            case R.id.bt_move:
                mMultiItemChanger.move(1, mMultiItemChanger.size() - 2);
                break;

        }
    }
}
