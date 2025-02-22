package com.example.administrator.androidtest.other.fileMedia.Provider;

import android.database.Cursor;
import android.provider.MediaStore;
import androidx.annotation.IntDef;
import com.example.administrator.androidtest.other.fileMedia.Info.AudioInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.BaseInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.ImageInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.VideoInfo;

import java.util.List;

public class MediaConfig {
    public static final int DEFAULT_VALUE = -1;
    public static final int NONE = 1;
    public static final int IMAGE = 2;
    public static final int VIDEO = 3;
    public static final int AUDIO = 4;
    public static final int IMAGE_VIDEO = 5;
    public static final int FILE = 6;
    public static final int APK = 7;

    private int mType = NONE;
    private List<String> mVideoMimes;
    private List<String> mImageMimes;
    private List<String> mAudioMimes;

    private String mDirPath;
    private long mMinSize = DEFAULT_VALUE;
    private long mMaxSize = DEFAULT_VALUE;
    private long mMinAddTime = DEFAULT_VALUE;
    private long mMaxAddTime = DEFAULT_VALUE;
    private int mMinWidth = DEFAULT_VALUE;
    private int mMaxWidth = DEFAULT_VALUE;
    private int mMinHeight = DEFAULT_VALUE;
    private int mMaxHeight = DEFAULT_VALUE;
    private long mMinVideoDuration = DEFAULT_VALUE;
    private long mMaxVideoDuration = DEFAULT_VALUE;
    private long mMinAudioDuration = DEFAULT_VALUE;
    private long mMaxAudioDuration = DEFAULT_VALUE;

    public boolean isType(int type){
        return mType == type;
    }

    public MediaConfig type(@Type int type) {
        mType = type;
        return this;
    }

    public MediaConfig dirPath(String dirPath) {
        mDirPath = dirPath;
        return this;
    }

    public MediaConfig minSize(long minSize) {
        mMinSize = minSize;
        return this;
    }

    public MediaConfig maxSize(long maxSize) {
        mMaxSize = maxSize;
        return this;
    }

    public MediaConfig minAddTime(long minAddTime) {
        mMinAddTime = minAddTime;
        return this;
    }

    public MediaConfig maxAddTime(long maxAddTime) {
        mMaxAddTime = maxAddTime;
        return this;
    }

    public MediaConfig minWidth(int minWidth) {
        mMinWidth = minWidth;
        return this;
    }

    public MediaConfig maxWidth(int maxWidth) {
        mMaxWidth = maxWidth;
        return this;
    }

    public MediaConfig minHeight(int minHeight) {
        mMinHeight = minHeight;
        return this;
    }

    public MediaConfig maxHeight(int maxHeight) {
        mMaxHeight = maxHeight;
        return this;
    }

    public MediaConfig maxVideoDuration(long maxVideoduration) {
        mMaxVideoDuration = maxVideoduration;
        return this;
    }

    public MediaConfig minVideoDuration(long minVideoDuration) {
        mMinVideoDuration = minVideoDuration;
        return this;
    }

    public MediaConfig maxAudioDuration(long maxAudioduration) {
        mMaxAudioDuration = maxAudioduration;
        return this;
    }

    public MediaConfig minAudioduration(long minAudioduration) {
        mMinAudioDuration = minAudioduration;
        return this;
    }

    public MediaConfig videoMimes(List<String> videoMimes) {
        mVideoMimes = videoMimes;
        return this;
    }

    public MediaConfig imageMimes(List<String> imageMimes) {
        mImageMimes = imageMimes;
        return this;
    }

    public MediaConfig audioMimes(List<String> audioMimes) {
        mAudioMimes = audioMimes;
        return this;
    }

    public List<String> getVideoMimes() {
        return mVideoMimes;
    }

    public List<String> getImageMimes() {
        return mImageMimes;
    }

    public List<String> getAudioMimes() {
        return mAudioMimes;
    }

    public String getDirPath() {
        return mDirPath;
    }

    public long getMinSize() {
        return mMinSize;
    }

    public long getMaxSize() {
        return mMaxSize;
    }

    public long getMinAddTime() {
        return mMinAddTime;
    }

    public long getMaxAddTime() {
        return mMaxAddTime;
    }

    public int getMinWidth() {
        return mMinWidth;
    }

    public int getMaxWidth() {
        return mMaxWidth;
    }

    public int getMinHeight() {
        return mMinHeight;
    }

    public int getMaxHeight() {
        return mMaxHeight;
    }

    public long getMinVideoDuration() {
        return mMinVideoDuration;
    }

    public long getMaxVideoDuration() {
        return mMaxVideoDuration;
    }

    public long getMinAudioDuration() {
        return mMinAudioDuration;
    }

    public long getMaxAudioDuration() {
        return mMaxAudioDuration;
    }

    public int getType() {
        return mType;
    }

    public int[] mediaTypes() {
        switch (mType) {
            case IMAGE:
                return new int[]{MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE};

            case VIDEO:
                return new int[]{MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO};

            case AUDIO:
                return new int[]{MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO};

            case IMAGE_VIDEO:
                return new int[]{MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE, MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO};
        }
        return new int[]{DEFAULT_VALUE};
    }

    public static MediaConfig from(@Type int type){
        return new MediaConfig().type(type);
    }

    public boolean isCursor(Cursor cursor) {
        BaseInfo info = BaseInfo.from(cursor);
        boolean result = true;
        if (mVideoMimes != null && info.mMime != null) {
            result = mVideoMimes.contains(info.mMime);
        }
        if (result && mImageMimes != null && info.mMime != null) {
            result = mImageMimes.contains(info.mMime);
        }
        if (result && mAudioMimes != null && info.mMime != null) {
            result = mAudioMimes.contains(info.mMime);
        }
        if (result && mDirPath != null && info.mPath != null) {
            result = info.mPath.contains(mDirPath);
        }
        if (result && mMinAddTime != DEFAULT_VALUE && info.mAddDate != 0) {
            result = info.mAddDate > mMinAddTime;
        }
        if (result && mMaxAddTime != DEFAULT_VALUE && info.mAddDate != 0) {
            result = info.mAddDate < mMaxAddTime;
        }
        if (result && mMinSize != DEFAULT_VALUE && info.mSize != 0) {
            result = info.mSize > mMinSize;
        }
        if (result && mMaxSize != DEFAULT_VALUE && info.mSize != 0) {
            result = info.mSize < mMaxSize;
        }
        if (result && mType != NONE && info.mType != 0) {
            if ((mType == IMAGE || mType == IMAGE_VIDEO) && info.mType == MediaConstant.IMAGE){
                ImageInfo imageInfo = (ImageInfo) info;
                if (mMinWidth != DEFAULT_VALUE && imageInfo.mWidth != 0) {
                    result = imageInfo.mWidth > mMinWidth;
                }
                if (mMaxWidth != DEFAULT_VALUE && imageInfo.mWidth != 0) {
                    result = imageInfo.mWidth < mMaxWidth;
                }
                if (mMinHeight != DEFAULT_VALUE && imageInfo.mHeight != 0) {
                    result = imageInfo.mHeight > mMinHeight;
                }
                if (mMaxHeight != DEFAULT_VALUE && imageInfo.mHeight != 0) {
                    result = imageInfo.mHeight < mMaxHeight;
                }
            } else if ((mType == VIDEO || mType == IMAGE_VIDEO) && info.mType == MediaConstant.VIDEO) {
                VideoInfo videoInfo = (VideoInfo) info;
                if (mMinWidth != DEFAULT_VALUE && videoInfo.mWidth != 0) {
                    result = videoInfo.mWidth > mMinWidth;
                }
                if (mMaxWidth != DEFAULT_VALUE && videoInfo.mWidth != 0) {
                    result = videoInfo.mWidth < mMaxWidth;
                }
                if (mMinHeight != DEFAULT_VALUE && videoInfo.mHeight != 0) {
                    result = videoInfo.mHeight > mMinHeight;
                }
                if (mMaxHeight != DEFAULT_VALUE && videoInfo.mHeight != 0) {
                    result = videoInfo.mHeight < mMaxHeight;
                }
                if (mMinVideoDuration != DEFAULT_VALUE && videoInfo.mDuration != 0) {
                    result = videoInfo.mDuration > mMinVideoDuration;
                }
                if (mMaxVideoDuration != DEFAULT_VALUE && videoInfo.mDuration != 0) {
                    result = videoInfo.mDuration < mMaxVideoDuration;
                }
            } else if (mType == AUDIO && info.mType == MediaConstant.AUDIO) {
                AudioInfo audioInfo = (AudioInfo) info;
                if (mMinAudioDuration != DEFAULT_VALUE && audioInfo.mDuration != 0) {
                    result = audioInfo.mDuration > mMinAudioDuration;
                }
                if (mMaxAudioDuration != DEFAULT_VALUE && audioInfo.mDuration != 0) {
                    result = audioInfo.mDuration < mMaxAudioDuration;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    @IntDef({NONE, IMAGE, VIDEO, AUDIO, IMAGE_VIDEO, FILE, APK})
    @interface Type {

    }
}
