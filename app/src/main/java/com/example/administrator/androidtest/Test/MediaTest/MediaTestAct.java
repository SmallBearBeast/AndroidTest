package com.example.administrator.androidtest.Test.MediaTest;

import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.libcomponent.component.ComponentAct;
import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.librv.RvDivider;
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
        MultiTypeAdapter adapter = new MultiTypeAdapter(getLifecycle());
//        adapter.register(Cursor.class, new ImageVideoDelegate(divider));
        TextDelegate textDelegate = new TextDelegate();
        adapter.register(String.class, textDelegate);
        final MediaConfig config_1 = MediaConfig.from(MediaConfig.IMAGE).minAddTime(System.currentTimeMillis() / 1000 - 20 * 60);
        final MediaConfig config_2 = MediaConfig.from(MediaConfig.IMAGE);
        final MediaConfig config_3 = MediaConfig.from(MediaConfig.AUDIO);
        ImageVideoDelegate imageVideoDelegate_1 = new ImageVideoDelegate(divider);
//        adapter.register(imageVideoDelegate_1, CONFIG_1);
//        ImageVideoDelegate imageVideoDelegate_2 = new ImageVideoDelegate(divider);
//        adapter.register(imageVideoDelegate_2, CONFIG_2);
//        AudioDelegate audioDelegate = new AudioDelegate();
//        adapter.register(audioDelegate, CONFIG_3);
//        adapter.setOnDataTypeCreator(new VHAdapter.OnDataTypeCreator() {
//            @Override
//            public int createDataType(Object obj, int pos) {
//                if (obj instanceof Cursor) {
//                    Cursor cursor = (Cursor) obj;
//                    if (config_1.isCursor(cursor)) {
//                        SLog.i(TAG, "getType: obj = cursor" + " pos = " + pos + " config_1");
//                        return CONFIG_1;
//                    }
//                    if (config_2.isCursor(cursor)) {
//                        SLog.i(TAG, "getType: obj = cursor" + " pos = " + pos + " config_2");
//                        return CONFIG_2;
//                    }
//                    if (config_3.isCursor(cursor)) {
//                        SLog.i(TAG, "getType: obj = cursor" + " pos = " + pos + " config_3");
//                        return CONFIG_3;
//                    }
//                }
//                return -1;
//            }
//        });
        rvMedia.setAdapter(adapter);
        final MultiItemChanger changer = adapter.getChanger();
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
                changer.addCursorFirst(cursor);
                changer.addLast("123");
            }
        });

        provider.fetchCursor(config_2, new MediaProvider.CursorCallback() {
            @Override
            public void onCursor(Cursor cursor) {
                TimeUtil.markEnd("fetchCursor");
                SLog.i(TAG, "cursor.getCount = " + cursor.getCount() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchCursor"));
                changer.addCursorLast(cursor);
                changer.addLast("123");
            }
        });

        provider.fetchCursor(config_3, new MediaProvider.CursorCallback() {
            @Override
            public void onCursor(Cursor cursor) {
                SLog.i(TAG, "cursor.getCount = " + cursor.getCount() + " TimeUtil.getDuration = " + TimeUtil.getDuration("fetchBaseInfo"));
                changer.addCursorLast(cursor);
                changer.addLast("123");
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
