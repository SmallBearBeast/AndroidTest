package com.example.administrator.androidtest.other.fileMedia.Provider;

import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Environment;
import com.example.administrator.androidtest.other.fileMedia.Info.AudioInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.BaseInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.DirInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.ImageInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.VideoInfo;
import com.example.libcommon.Util.TimeRecordUtil;
import com.example.liblog.SLog;

import java.io.File;
import java.util.*;

public class FileFinder {
    private static final String TAG = "FileFinder";

    public void find(Set<String> suffixSet, DirInfoCallback callback) {
        File sdcardFile = Environment.getExternalStorageDirectory();
        find(suffixSet, sdcardFile, callback);
    }

    public void find(Set<String> suffixSet, File file, DirInfoCallback callback) {
        Map<String, DirInfo> dirInfoMap = new HashMap<>();
        TimeRecordUtil.markStart("find");
        find(suffixSet, file, dirInfoMap);
        TimeRecordUtil.markEnd("find");
        SLog.i(TAG, "find method time: " + TimeRecordUtil.getDuration("find"));
        if (callback != null) {
            callback.onDirInfoSet(new HashSet<>(dirInfoMap.values()));
        }
    }

    private void find(Set<String> suffixSet, File file, Map<String, DirInfo> map) {
        if (isDirectory(file)) {
            File[] files = file.listFiles();
            for (File f : files) {
                find(suffixSet, f, map);
            }
        } else if (isFile(file)) {
            String suffix = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if (suffixSet.contains(suffix)) {
                String parentPath = file.getParentFile().getAbsolutePath();
                DirInfo dirInfo = map.get(parentPath);
                if (dirInfo == null) {
                    map.put(parentPath, dirInfo = new DirInfo(parentPath));
                }
                dirInfo.mCount++;
                dirInfo.addBaseInfo(createInfo(file, suffix));
            }
        }
    }

    // TODO: 2019-10-22 opt
    private BaseInfo createInfo(File file, String suffix) {
        BaseInfo baseInfo = null;
        if (MediaConstant.IMAGE_SUFFIX_SET.contains(suffix)) {
            ImageInfo imageInfo = new ImageInfo();
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(file.getAbsolutePath(), options);
            imageInfo.mWidth = options.outWidth;
            imageInfo.mHeight = options.outHeight;
            baseInfo = imageInfo;
        } else if (MediaConstant.VIDEO_SUFFIX_SET.contains(suffix)) {
            VideoInfo videoInfo = new VideoInfo();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(file.getAbsolutePath());
            videoInfo.mWidth = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
            videoInfo.mHeight = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
            videoInfo.mDuration = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            baseInfo = videoInfo;
        } else if (MediaConstant.AUDIO_SUFFIX_SET.contains(suffix)) {
            AudioInfo audioInfo = new AudioInfo();
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(file.getAbsolutePath());
            audioInfo.mDuration = Integer.parseInt(mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));
            baseInfo = audioInfo;
        }
        if (baseInfo != null) {
            baseInfo.mPath = file.getAbsolutePath();
            baseInfo.mSuffix = suffix;
            baseInfo.mSize = file.length();
            baseInfo.mName = file.getName();
            baseInfo.mAddDate = file.lastModified();
        }
        return baseInfo;
    }

    public interface DirInfoCallback {
        void onDirInfoSet(Set<DirInfo> dirInfoSet);
    }

    private boolean isDirectory (File file) {
        return file.isDirectory() && file.canRead();
    }

    private boolean isFile (File file) {
        return file.isFile() && file.canRead();
    }

}
