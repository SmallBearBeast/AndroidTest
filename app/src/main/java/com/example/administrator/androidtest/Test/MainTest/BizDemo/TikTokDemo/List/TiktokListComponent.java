package com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.List;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.libstorage.FileStorage;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Test.MainTest.BizDemo.TikTokDemo.TiktokBean;
import com.example.administrator.androidtest.Test.MainTest.TestActivityComponent;
import com.example.libbase.Executor.BgThreadExecutor;
import com.example.libbase.Executor.MainThreadExecutor;
import com.example.libbase.Util.IOUtil;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.List;

public class TiktokListComponent extends TestActivityComponent {

    private RecyclerView tiktokRecyclerView;

    private MultiItemChanger multiItemChanger;

    public TiktokListComponent(Lifecycle lifecycle) {
        super(lifecycle);
    }

    @Override
    protected void onCreate() {
        super.onCreate();
        tiktokRecyclerView = findViewById(R.id.tiktokRecyclerView);
        tiktokRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        MultiTypeAdapter adapter = new MultiTypeAdapter(getActivity().getLifecycle());
        multiItemChanger = adapter.getChanger();
        adapter.register(TiktokBean.class, new TikTokListDelegate());
        tiktokRecyclerView.setAdapter(adapter);

        loadTikTokListData();
    }

    private void loadTikTokListData() {
        BgThreadExecutor.execute(() -> {
            InputStream inputStream = null;
            try {
                inputStream = getContext().getAssets().open("tiktok_data.json");
                TypeToken<List<TiktokBean>> typeToken = new TypeToken<List<TiktokBean>>() {};
                List<TiktokBean> tiktokBeans = FileStorage.readObjFromJson(inputStream, typeToken);
                MainThreadExecutor.post(() -> multiItemChanger.setItems(tiktokBeans));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtil.close(inputStream);
            }
        });
    }
}
