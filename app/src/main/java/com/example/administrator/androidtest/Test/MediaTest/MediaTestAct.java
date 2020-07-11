package com.example.administrator.androidtest.Test.MediaTest;

import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.libcomponent.ComponentAct;
import com.bear.librv.DataManager;
import com.bear.librv.RvDivider;
import com.bear.librv.VHAdapter;
import com.example.administrator.androidtest.Common.Media.Info.BaseInfo;
import com.example.administrator.androidtest.Common.Media.Info.DirInfo;
import com.example.administrator.androidtest.Common.Media.Provider.MediaConfig;
import com.example.administrator.androidtest.Common.Media.Provider.MediaProvider;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.DensityUtil;
import com.example.libbase.Util.TimeUtil;
import com.example.liblog.SLog;

import java.io.File;
import java.util.*;

public class MediaTestAct extends ComponentAct {

    private static final int CONFIG_1 = 1;
    private static final int CONFIG_2 = 2;
    private static final int CONFIG_3 = 300;

    @Override
    protected int layoutId() {
        return R.layout.act_media_provider;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int divider = DensityUtil.dp2Px(3);
        RecyclerView rvMedia = findViewById(R.id.rv_media);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rvMedia.setLayoutManager(gridLayoutManager);
        rvMedia.addItemDecoration(new RvDivider(gridLayoutManager, divider));
        VHAdapter adapter = new VHAdapter(getLifecycle());
//        adapter.register(new ImageVideoBridge(divider), Cursor.class);
        TextBridge textBridge = new TextBridge();
        adapter.register(textBridge, String.class);
        final MediaConfig config_1 = MediaConfig.from(MediaConfig.IMAGE).minAddTime(System.currentTimeMillis() / 1000 - 20 * 60);
        final MediaConfig config_2 = MediaConfig.from(MediaConfig.IMAGE);
        final MediaConfig config_3 = MediaConfig.from(MediaConfig.AUDIO);
        ImageVideoBridge imageVideoBridge_1 = new ImageVideoBridge(divider);
        adapter.register(imageVideoBridge_1, CONFIG_1);
        ImageVideoBridge imageVideoBridge_2 = new ImageVideoBridge(divider);
        adapter.register(imageVideoBridge_2, CONFIG_2);
        AudioBridge audioBridge = new AudioBridge();
        adapter.register(audioBridge, CONFIG_3);
        adapter.setOnGetDataType(new VHAdapter.OnGetDataType() {
            @Override
            public int getType(Object obj, int pos) {
                if (obj instanceof Cursor) {
                    Cursor cursor = (Cursor) obj;
                    if (config_1.isCursor(cursor)) {
                        SLog.i(TAG, "getType: obj = cursor" + " pos = " + pos + " config_1");
                        return CONFIG_1;
                    }
                    if (config_2.isCursor(cursor)) {
                        SLog.i(TAG, "getType: obj = cursor" + " pos = " + pos + " config_2");
                        return CONFIG_2;
                    }
                    if (config_3.isCursor(cursor)) {
                        SLog.i(TAG, "getType: obj = cursor" + " pos = " + pos + " config_3");
                        return CONFIG_3;
                    }
                }
                return -1;
            }
        });
        rvMedia.setAdapter(adapter);
        final DataManager manager = adapter.getDataManager();
        MediaProvider provider = new MediaProvider(this);
        TimeUtil.markStart("fetchDirInfo");
        provider.fetchDirInfo(config_1, new MediaProvider.DirInfoCallback() {
            @Override
            public void onDirInfoSet(Set<DirInfo> dirInfoSet) {
                TimeUtil.markEnd("fetchDirInfo");
                SLog.i(TAG, "dirInfoSet.size = " + dirInfoSet.size() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchDirInfo"));
            }
        });

        TimeUtil.markStart("fetchBaseInfo");
        provider.fetchBaseInfo(config_1, new MediaProvider.BaseInfoCallback() {
            @Override
            public void onBaseInfo(List<BaseInfo> baseInfoList) {
                TimeUtil.markEnd("fetchBaseInfo");
                SLog.i(TAG, "baseInfoList.size = " + baseInfoList.size() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchBaseInfo"));
            }
        });

        TimeUtil.markStart("fetchCursor");
        provider.fetchCursor(config_1, new MediaProvider.CursorCallback() {
            @Override
            public void onCursor(Cursor cursor) {
                TimeUtil.markEnd("fetchCursor");
                SLog.i(TAG, "cursor.getCount = " + cursor.getCount() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchCursor"));
                manager.addCursorFirst(cursor);
                manager.addLast("123");
            }
        });

        provider.fetchCursor(config_2, new MediaProvider.CursorCallback() {
            @Override
            public void onCursor(Cursor cursor) {
                TimeUtil.markEnd("fetchCursor");
                SLog.i(TAG, "cursor.getCount = " + cursor.getCount() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchCursor"));
                manager.addCursorLast(cursor);
                manager.addLast("123");
            }
        });

        provider.fetchCursor(config_3, new MediaProvider.CursorCallback() {
            @Override
            public void onCursor(Cursor cursor) {
                SLog.i(TAG, "cursor.getCount = " + cursor.getCount() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchBaseInfo"));
                manager.addCursorLast(cursor);
                manager.addLast("123");
            }
        });

//        FileFinder finder = new FileFinder();
//        finder.find(CollectionUtil.asSet("pdf"), new FileFinder.DirInfoCallback() {
//            @Override
//            public void onDirInfoSet(Set<DirInfo> dirInfoSet) {
//
//            }
//        });
        BaseInfo info = new BaseInfo();
        info.mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "wuyisong.jpg";
        info.mMime = "image/jpeg";
        provider.scanFile(this, info, new MediaScannerConnection.OnScanCompletedListener() {
            @Override
            public void onScanCompleted(String path, Uri uri) {
                String filePath = MediaProvider.uriToPath(MediaTestAct.this, uri);
                Uri fileUri = MediaProvider.pathToUri(MediaTestAct.this, path);
                SLog.d(TAG, "onScanCompleted() called with: path = [" + path + "], uri = [" + uri + "]");
            }
        });

        provider.getApk();
    }
}
