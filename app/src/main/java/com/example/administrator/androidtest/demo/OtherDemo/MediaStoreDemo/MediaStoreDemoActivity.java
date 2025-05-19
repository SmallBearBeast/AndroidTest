package com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.os.Environment;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bear.libcomponent.component.ComponentActivity;
import com.bear.librv.MultiItemChanger;
import com.bear.librv.MultiTypeAdapter;
import com.bear.librv.RvDivider;
import com.example.administrator.androidtest.other.fileMedia.Info.BaseInfo;
import com.example.administrator.androidtest.other.fileMedia.Provider.MediaConfig;
import com.example.administrator.androidtest.other.fileMedia.Provider.MediaStoreHelper;
import com.example.administrator.androidtest.R;
import com.example.libcommon.util.DensityUtil;
import com.example.libcommon.util.TimeRecordUtil;
import com.example.liblog.SLog;

import java.io.File;

public class MediaStoreDemoActivity extends ComponentActivity {

    private final MediaConfig config_1 = MediaConfig.from(MediaConfig.IMAGE).minAddTime(System.currentTimeMillis() / 1000 - 20 * 60);
    private final MediaConfig config_2 = MediaConfig.from(MediaConfig.IMAGE);
    private final MediaConfig config_3 = MediaConfig.from(MediaConfig.AUDIO);

    private MultiItemChanger itemChanger;

    @Override
    protected int layoutId() {
        return R.layout.act_media_provider;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRecyclerView();
        initData();
    }

    private void initRecyclerView() {
        int divider = DensityUtil.dp2Px(3);
        RecyclerView rvMedia = findViewById(R.id.rv_media);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 4);
        rvMedia.setLayoutManager(gridLayoutManager);
        rvMedia.addItemDecoration(new RvDivider(gridLayoutManager, divider));
        MultiTypeAdapter adapter = new MultiTypeAdapter(getLifecycle());
        TextDelegate textDelegate = new TextDelegate();
        adapter.register(String.class, textDelegate);

        ImageDelegate imageDelegate = new ImageDelegate(divider);
        VideoDelegate videoDelegate = new VideoDelegate(divider);
        adapter.register(Cursor.class).to(imageDelegate, videoDelegate, new AudioDelegate()).withClassLinker((position, cursor) -> {
            if (config_1.isCursor(cursor)) {
                SLog.i(TAG, "getType: obj = cursor" + " pos = " + position + " config_1");
                return ImageDelegate.class;
            }
            if (config_2.isCursor(cursor)) {
                SLog.i(TAG, "getType: obj = cursor" + " pos = " + position + " config_2");
                return ImageDelegate.class;
            }
            if (config_3.isCursor(cursor)) {
                SLog.i(TAG, "getType: obj = cursor" + " pos = " + position + " config_3");
                return AudioDelegate.class;
            }
            return null;
        });

        rvMedia.setAdapter(adapter);
        itemChanger = adapter.getChanger();
    }

    private void initData() {
        MediaStoreHelper provider = new MediaStoreHelper(this);
        TimeRecordUtil.markStart("fetchDirInfo");
        provider.fetchDirInfo(config_1, dirInfoSet -> {
            SLog.i(TAG, "initData: fetchDirInfo with config_1, dirInfoSet.size = " + dirInfoSet.size() + " TimeRecordUtil.getDuration = " + TimeRecordUtil.markAndGetDuration("fetchDirInfo"));
        });

        TimeRecordUtil.markStart("fetchBaseInfo");
        provider.fetchBaseInfo(config_1, baseInfoList -> {
            SLog.i(TAG, "initData: fetchBaseInfo with config_1, baseInfoList.size = " + baseInfoList.size() + " TimeRecordUtil.getDuration = " + TimeRecordUtil.markAndGetDuration("fetchBaseInfo"));
        });

        TimeRecordUtil.markStart("fetchCursor");
        provider.fetchCursor(config_1, cursor -> {
            SLog.i(TAG, "initData: fetchCursor with config_1, cursor.getCount = " + cursor.getCount() + " TimeRecordUtil.getDuration = " + TimeRecordUtil.markAndGetDuration("fetchCursor"));
            itemChanger.addCursorFirst(cursor);
            itemChanger.addLast("123");
        });

        provider.fetchCursor(config_2, cursor -> {
            SLog.i(TAG, "initData: fetchCursor with config_2, cursor.getCount = " + cursor.getCount() + " TimeRecordUtil.getDuration = " + TimeRecordUtil.markAndGetDuration("fetchCursor"));
            itemChanger.addCursorLast(cursor);
            itemChanger.addLast("123");
        });

        provider.fetchCursor(config_3, cursor -> {
            SLog.i(TAG, "initData: fetchCursor with config_3, cursor.getCount = " + cursor.getCount() + " TimeRecordUtil.getDuration = " + TimeRecordUtil.markAndGetDuration("fetchBaseInfo"));
            itemChanger.addCursorLast(cursor);
            itemChanger.addLast("123");
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
//        provider.scanFile(this, info, new MediaScannerConnection.OnScanCompletedListener() {
//            @Override
//            public void onScanCompleted(String path, Uri uri) {
//                String filePath = MediaStoreHelper.uriToPath(MediaStoreDemoAct.this, uri);
//                Uri fileUri = MediaStoreHelper.pathToUri(MediaStoreDemoAct.this, path);
//                SLog.d(TAG, "onScanCompleted() called with: path = [" + path + "], uri = [" + uri + "]");
//            }
//        });
//
//        provider.getApk();
    }

    public static void start(Context context) {
        context.startActivity(new Intent(context, MediaStoreDemoActivity.class));
    }
}
