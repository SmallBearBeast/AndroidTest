package com.example.administrator.androidtest.demo.ViewDemo.RecyclerViewDemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bear.libcommon.executor.MainThreadExecutor;
import com.bear.libcommon.util.CollectionUtil;
import com.bear.libcommon.util.ToastUtil;
import com.bear.libcomponent.component.ComponentActivity;
import com.bear.liblog.SLog;
import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.librv.Payload;
import com.bear.librv.RvDivider;
import com.bear.librv.RvListener;
import com.bear.librv.RvUtil;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.databinding.ActRvDemoBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDemoActivity extends ComponentActivity<ActRvDemoBinding> implements View.OnClickListener {
    private static final String TAG = "RvAct";
    private static final int DEFAULT_REMOVE_DELAY_TIME = 0;
    private static final int DEFAULT_ADD_DELAY_TIME = 0;
    private static final int DEFAULT_IMAGE_COUNT = 27;
    private static final int DEFAULT_INFO_COUNT = 9;
    private static final int DEFAULT_MSG_COUNT = 9;
    private static final int APPEND_ITEM_ID = -10;
    static final int PARTIAL_UPDATE_TYPE = 111;
    private String currentTypeClassName;
    private RecyclerView recyclerView;
    private MultiItemChanger itemChanger;
    private MultiTypeAdapter adapter;

    private final MsgDelegate msgDelegate = new MsgDelegate();

    public final static String[] urls = new String[]{
            "http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg",
            "http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c1badd5a685d6277f9e2ff81e.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c87a3add4d52a6059252da61e.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee5080c8142ff5e0fe99257e19.jpg",
            "http://f.hiphotos.baidu.com/image/pic/item/4034970a304e251f503521f5a586c9177e3e53f9.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fbb3586c0168224f4a20a4dd7e.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg",
            "http://c.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de1e296fa390eef01f3b29795a.jpg",
            "http://d.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f119945cbe2fe9925bc317d2a.jpg",
            "http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg",
            "http://a.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a292d2472199d25bc315d607c7c.jpg",
            "http://b.hiphotos.baidu.com/image/pic/item/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg",
            "http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg",
    };

    @Override
    protected ActRvDemoBinding inflateViewBinding(@NonNull LayoutInflater inflater) {
        return ActRvDemoBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSpinner();
        initRecyclerView();
    }

    private void initSpinner() {
        AppCompatSpinner spinner = findViewById(R.id.spinner);
        List<String> itemTypeArray = CollectionUtil.asListNotNull(Image.class.getSimpleName(), Msg.class.getSimpleName(), Info.class.getSimpleName());
        SpinnerAdapter spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemTypeArray);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentTypeClassName = itemTypeArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initRecyclerView() {
        recyclerView = findViewById(R.id.testRecyclerView);
        //解决notifychange刷新问题
//        ((SimpleItemAnimator) mRvTest.getItemAnimator()).setSupportsChangeAnimations(false);
//        recyclerView.setItemAnimator(null);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        mRvTest.setLayoutManager(linearLayoutManager);
//        mRvTest.setHasFixedSize(true);

        adapter = new MultiTypeAdapter(getLifecycle());
        adapter.register(Image.class, new ImageDelegate());
        adapter.register(Info.class, new InfoDelegate());
        adapter.register(Msg.class, msgDelegate);
        recyclerView.setAdapter(adapter);

//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3, RecyclerView.VERTICAL, false);
//        recyclerView.setLayoutManager(gridLayoutManager);
//        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
//            @Override
//            public int getSpanSize(int position) {
//                return mAdapter.getItemViewType(position) ==  msgVHBinder.getType() ? 3 : 1;
//            }
//        });

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.addItemDecoration(new RvDivider(staggeredGridLayoutManager, 20, 40));
        recyclerView.addOnItemTouchListener(new RvListener(this, recyclerView, new RvListener.OnItemClickListener() {
            int color = Color.BLACK;

            @Override
            public boolean onItemClick(View view, int position) {
                SLog.d(TAG, "onItemClick: view = " + view + " position = " + position);
                switch (view.getId()) {
                    case R.id.textView:
                        if (color == Color.BLACK) {
                            color = Color.RED;
                        } else {
                            color = Color.BLACK;
                        }
                        view.setBackgroundColor(color);
                        SLog.d(TAG, "onItemClick: textview = " + view + " position = " + position);
                        break;

                    case R.id.imageView:
                        SLog.d(TAG, "onItemClick: frescoView = " + view + " position = " + position);
                        break;
                }
                return true;
            }

            @Override
            public boolean onItemLongClick(View view, int position) {
                SLog.d(TAG, "onItemLongClick: view = " + view + " position = " + position);
                switch (view.getId()) {
                    case R.id.textView:
                        SLog.d(TAG, "onItemLongClick: textview = " + view + " position = " + position);
                        ToastUtil.showToast("onItemLongClick: textview = " + view + " position = " + position);
                        break;

                    case R.id.imageView:
                        SLog.d(TAG, "onItemLongClick: frescoView = " + view + " position = " + position);
                        ToastUtil.showToast("onItemLongClick: imageView = " + view + " position = " + position);
                        break;
                }
                return true;
            }
        }));
        itemChanger = adapter.getChanger();
        initItemList();
    }

    private void initItemList() {
        List<Object> itemList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_IMAGE_COUNT; i++) {
            Image image = new Image(i);
            image.url = urls[i % urls.length];
            itemList.add(image);
        }
        for (int i = 0; i < DEFAULT_MSG_COUNT; i++) {
            Msg msg = new Msg();
            itemList.add(msg);
        }
        for (int i = 0; i < DEFAULT_INFO_COUNT; i++) {
            Info info = new Info(i);
            info.url_1 = urls[1];
            info.url_2 = urls[2];
            info.url_3 = urls[3];
            itemList.add(info);
        }
        itemChanger.setItems(itemList);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_first:
                itemChanger.addFirst(generateNewItemByType(currentTypeClassName));
                MainThreadExecutor.postDelayed(() -> {
                    RvUtil.scrollToTop(recyclerView, 4, 0);
                }, DEFAULT_ADD_DELAY_TIME);
                break;

            case R.id.bt_remove_first:
                RvUtil.scrollToTop(recyclerView, 4, 0);
                MainThreadExecutor.postDelayed(() -> {
                    itemChanger.removeFirst(1);
                }, DEFAULT_REMOVE_DELAY_TIME);
                break;

            case R.id.bt_add_last:
                itemChanger.addLast(generateNewItemByType(currentTypeClassName));
                MainThreadExecutor.postDelayed(() -> {
                    RvUtil.scrollToBottom(recyclerView, 4, 0);
                }, DEFAULT_ADD_DELAY_TIME);
                break;

            case R.id.bt_remove_last:
                RvUtil.scrollToBottom(recyclerView, 4, 0);
                MainThreadExecutor.postDelayed(() -> {
                    itemChanger.removeLast(1);
                }, DEFAULT_REMOVE_DELAY_TIME);
                break;

            case R.id.bt_update:
                Image image = new Image(APPEND_ITEM_ID);
                image.url = urls[urls.length - 1];
                itemChanger.update(image);
                break;

            case R.id.bt_partial_update:
                Info partInfo = new Info(APPEND_ITEM_ID);
                partInfo.url_1 = urls[urls.length - 1];
                partInfo.url_2 = urls[urls.length - 1];
                partInfo.url_3 = urls[urls.length - 1];
                itemChanger.update(partInfo, new Payload(PARTIAL_UPDATE_TYPE));
                break;

            //  Move效果有点问题
            case R.id.bt_move:
                itemChanger.move(0, 2);
                break;

            case R.id.testButton:
                RvUtil.test(recyclerView, recyclerView.getLayoutManager());
                break;
        }
    }

    private Object generateNewItemByType(String className) {
        if (Msg.class.getSimpleName().equals(className)) {
            return new Msg();
        } else if (Image.class.getSimpleName().equals(className)) {
            return new Image(APPEND_ITEM_ID);
        } else if (Info.class.getSimpleName().equals(className)) {
            Info info = new Info(APPEND_ITEM_ID);
            info.url_1 = urls[1];
            info.url_2 = urls[2];
            info.url_3 = urls[3];
            return info;
        }
        return new Object();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, RecyclerViewDemoActivity.class));
    }
}
