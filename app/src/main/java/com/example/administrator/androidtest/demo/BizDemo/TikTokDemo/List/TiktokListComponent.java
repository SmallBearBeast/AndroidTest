package com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.List;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.libstorage.FileStorage;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.demo.BizDemo.TikTokDemo.TiktokVideoInfo;
import com.example.administrator.androidtest.demo.TestActivityComponent;
import com.example.libcommon.Executor.BgThreadExecutor;
import com.example.libcommon.Executor.MainThreadExecutor;
import com.example.libcommon.Util.IOUtil;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;

public class TiktokListComponent extends TestActivityComponent {

    private RecyclerView tiktokRecyclerView;

    private MultiItemChanger changer;

    public TiktokListComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tiktokRecyclerView = findViewById(R.id.tiktokRecyclerView);
        tiktokRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        MultiTypeAdapter adapter = new MultiTypeAdapter(getActivity().getLifecycle());
        changer = adapter.getChanger();
        adapter.register(TiktokVideoInfo.class, new TikTokListDelegate());
        tiktokRecyclerView.setAdapter(adapter);

        loadTikTokListData();
    }

    private void loadTikTokListData() {
        BgThreadExecutor.execute(() -> {
            InputStream inputStream = null;
            try {
                inputStream = getContext().getAssets().open("tiktok_video_info.json");
                TypeToken<List<TiktokVideoInfo>> typeToken = new TypeToken<List<TiktokVideoInfo>>() {};
                List<TiktokVideoInfo> tiktokVideoInfos = FileStorage.readObjFromJson(inputStream, typeToken);
                MainThreadExecutor.post(() -> changer.setItems(tiktokVideoInfos));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(inputStream);
            }
        });
    }
}
