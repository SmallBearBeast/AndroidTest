package com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.list;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.libcommon.executor.BgThreadExecutor;
import com.bear.libcommon.executor.MainThreadExecutor;
import com.bear.libcommon.util.IOUtil;
import com.bear.libcomponent.component.ui.ActivityComponent;
import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.libstorage.FileStorage;
import com.example.administrator.androidtest.databinding.ActTiktokDemoBinding;
import com.example.administrator.androidtest.demo.bizdemo.tiktokdemo.TiktokItemInfo;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;

public class TiktokListComponent extends ActivityComponent<ActTiktokDemoBinding> {

    private RecyclerView tiktokRecyclerView;

    private MultiItemChanger changer;

    public TiktokListComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tiktokRecyclerView = getViewBinding().tiktokRecyclerView;
        tiktokRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        MultiTypeAdapter adapter = new MultiTypeAdapter(getActivity().getLifecycle());
        changer = adapter.getChanger();
        adapter.register(TiktokItemInfo.class, new TikTokListDelegate());
        tiktokRecyclerView.setAdapter(adapter);

        loadTikTokListData();
    }

    private void loadTikTokListData() {
        BgThreadExecutor.execute(() -> {
            InputStream inputStream = null;
            try {
                inputStream = getContext().getAssets().open("tiktok_refresh_video_item_info.json");
                TypeToken<List<TiktokItemInfo>> typeToken = new TypeToken<List<TiktokItemInfo>>() {};
                List<TiktokItemInfo> tiktokItemInfos = FileStorage.readObjFromJson(inputStream, typeToken);
                MainThreadExecutor.post(() -> changer.setItems(tiktokItemInfos));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(inputStream);
            }
        });
    }
}
