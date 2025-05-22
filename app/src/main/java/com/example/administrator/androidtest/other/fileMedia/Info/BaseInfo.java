package com.example.administrator.androidtest.other.fileMedia.Info;

import android.database.Cursor;
import android.provider.MediaStore;

import com.bear.libcommon.util.TimeRecordUtil;
import com.bear.liblog.SLog;
import com.example.administrator.androidtest.other.fileMedia.Provider.MediaConstant;

public class BaseInfo {
    private static final String TAG = "BaseInfo";

    public String mPath;
    public byte mType;
    public String mName;
    public String mMime;
    public String mSuffix;
    public long mSize;
    public long mAddDate;

    public static BaseInfo from(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            TimeRecordUtil.markStart(TAG);
            String mime = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            BaseInfo info = createBaseInfo(mime, path);
            if (info == null) {
                return null;
            }
            setUpBaseInfo(cursor, info);
            setUpSpInfo(cursor, info);
            TimeRecordUtil.markEnd(TAG);
            SLog.i(TAG, "TimeRecordUtil.getDuration(TAG) = " + TimeRecordUtil.getDuration(TAG));
            return info;
        }
        return null;
    }

    private static void setUpSpInfo(Cursor cursor, BaseInfo info) {
        if (info instanceof VideoInfo) {
            VideoInfo videoInfo = (VideoInfo) info;
            videoInfo.mWidth = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.WIDTH));
            videoInfo.mHeight = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.HEIGHT));
            videoInfo.mDuration = cursor.getInt(cursor.getColumnIndex(MediaStore.Video.VideoColumns.DURATION));
            // TODO: 2019-10-12 is cost time
//            MediaMetadataRetriever m = new MediaMetadataRetriever();
//            m.setDataSource(videoInfo.mPath);
//            videoInfo.mOrientation = Integer.valueOf(m.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION));
        } else if (info instanceof ImageInfo) {
            ImageInfo imageInfo = (ImageInfo) info;
            imageInfo.mWidth = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH));
            imageInfo.mHeight = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT));
            imageInfo.mOrientation = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns.ORIENTATION));
        } else if (info instanceof AudioInfo) {
            AudioInfo audioInfo = (AudioInfo) info;
            audioInfo.mDuration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DURATION));
        }
    }

    private static void setUpBaseInfo(Cursor cursor, BaseInfo info) {
        info.mName = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME));
        info.mSize = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.SIZE));
        info.mAddDate = cursor.getLong(cursor.getColumnIndex(MediaStore.MediaColumns.DATE_ADDED));
    }

    private static BaseInfo createBaseInfo(String mime, String path) {
        BaseInfo info = null;
        String suffix = path.substring(path.indexOf(".") + 1);
        if (MediaConstant.VIDEO_MIME_SET.contains(mime) || MediaConstant.VIDEO_SUFFIX_SET.contains(suffix)) {
            info = new VideoInfo();
        } else if (MediaConstant.IMAGE_MIME_SET.contains(mime) || MediaConstant.IMAGE_SUFFIX_SET.contains(suffix)) {
            info = new ImageInfo();
        } else if (MediaConstant.AUDIO_MIME_SET.contains(mime) || MediaConstant.AUDIO_SUFFIX_SET.contains(suffix)) {
            info = new AudioInfo();
        }
        if (info != null) {
            info.mSuffix = suffix;
            info.mPath = path;
            info.mMime = mime;
        }
        return info;
    }
}
