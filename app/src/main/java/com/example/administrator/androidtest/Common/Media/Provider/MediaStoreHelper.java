package com.example.administrator.androidtest.Common.Media.Provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Pair;
import com.example.administrator.androidtest.Common.Media.Info.ApkInfo;
import com.example.administrator.androidtest.Common.Media.Info.BaseInfo;
import com.example.administrator.androidtest.Common.Media.Info.DirInfo;

import java.io.File;
import java.util.*;

/**
 * The origin date is from Cursor, do not consider other dir
 */
public class MediaStoreHelper {
    // TODO: 2019-10-22 监听更新媒体action
    protected static final Uri QUERY_URI = MediaStore.Files.getContentUri("external");
    protected Context mContext;

    public MediaStoreHelper(Context context) {
        mContext = context;
    }

    //cursor should be closed
    public void fetchCursor(MediaConfig config, CursorCallback callback) {
        if (config == null) {
            return;
        }
        String[] projection = getProjection(config);
        Pair<String, String[]> pair = getSelectionAndArgs(config);
        String selection = pair.first;
        String[] selectionArgs = pair.second;
        String sortOrder = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        Cursor cursor = mContext.getContentResolver().query(QUERY_URI, projection, selection, selectionArgs, sortOrder);
        if (cursor != null && !cursor.isClosed() && callback != null) {
            cursor.moveToFirst();
            callback.onCursor(cursor);
        }
    }

    public void fetchBaseInfo(MediaConfig config, BaseInfoCallback callback) {
        if (config == null) {
            return;
        }
        String[] projection = getProjection(config);
        Pair<String, String[]> pair = getSelectionAndArgs(config);
        String selection = pair.first;
        String[] selectionArgs = pair.second;
        String sortOrder = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        Cursor cursor = mContext.getContentResolver().query(QUERY_URI, projection, selection, selectionArgs, sortOrder);
        if (cursor != null && !cursor.isClosed() && callback != null) {
            handleBaseInfo(cursor, callback);
            cursor.close();
        }
    }

    public void fetchDirInfo(MediaConfig config, DirInfoCallback callback) {
        if (config == null) {
            return;
        }
        Pair<String, String[]> pair = getSelectionAndArgs(config);
        String selection = pair.first;
        String[] selectionArgs = pair.second;
        String sortOrder = MediaStore.MediaColumns.DATE_ADDED + " DESC";
        Cursor cursor = mContext.getContentResolver().query(QUERY_URI, DIR_PROJECTION.toArray(new String[0]), selection, selectionArgs, sortOrder);
        if (cursor != null && !cursor.isClosed() && callback != null) {
            handleDirInfo(cursor, callback);
            cursor.close();
        }
    }

    public interface CursorCallback {
        void onCursor(Cursor cursor);
    }

    public interface DirInfoCallback {
        void onDirInfoSet(Set<DirInfo> dirInfoSet);
    }

    public interface BaseInfoCallback {
        void onBaseInfo(List<BaseInfo> baseInfoList);
    }

    protected static final Set<String> BASE_PROJECTION = new HashSet<>();
    protected static final Set<String> IMAGE_PROJECTION = new HashSet<>();
    protected static final Set<String> VIDEO_PROJECTION = new HashSet<>();
    protected static final Set<String> AUDIO_PROJECTION = new HashSet<>();
    protected static final Set<String> DIR_PROJECTION = new HashSet<>();

    static {
        Collections.addAll(BASE_PROJECTION,
                MediaStore.Files.FileColumns.MEDIA_TYPE,
                MediaStore.MediaColumns._ID,
                MediaStore.MediaColumns.SIZE,
                MediaStore.MediaColumns.DISPLAY_NAME,
                MediaStore.MediaColumns.MIME_TYPE,
                MediaStore.MediaColumns.DATA,
                MediaStore.MediaColumns.DATE_ADDED);
        Collections.addAll(IMAGE_PROJECTION,
                MediaStore.Images.ImageColumns.WIDTH,
                MediaStore.Images.ImageColumns.HEIGHT,
                MediaStore.Images.ImageColumns.ORIENTATION);
        Collections.addAll(VIDEO_PROJECTION,
                MediaStore.Video.VideoColumns.WIDTH,
                MediaStore.Video.VideoColumns.HEIGHT,
                MediaStore.Video.VideoColumns.DURATION);
        Collections.addAll(AUDIO_PROJECTION, MediaStore.Audio.AudioColumns.DURATION);
        Collections.addAll(DIR_PROJECTION,
                MediaStore.Audio.AudioColumns.DATA,
                MediaStore.Audio.AudioColumns.SIZE);
    }

    // TODO: 2019-10-14 是否可以不带args参数，直接加在selection上面。
    private Pair<String, String[]> getSelectionAndArgs(MediaConfig config) {
        List<String> selectionArgs = new ArrayList<>();
        List<String> selections = new ArrayList<>();
        if (!config.isType(MediaConfig.NONE)) {
            int[] mediaTypes = config.mediaTypes();
            String[] inParams = new String[mediaTypes.length];
            for (int i = 0; i < mediaTypes.length; i++) {
                inParams[i] = MediaStore.Files.FileColumns.MEDIA_TYPE + "=?";
                selectionArgs.add(String.valueOf(mediaTypes[i]));
            }
            selections.add(TextUtils.join(" or ", inParams));
        }
        if (config.getDirPath() != null) {
            selections.add(MediaStore.MediaColumns.DATA + " like ?");
            selectionArgs.add(config.getDirPath());
        }
        if (config.getMinAddTime() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.DATE_ADDED + ">=?");
            selectionArgs.add(String.valueOf(config.getMinAddTime()));
        }
        if (config.getMaxAddTime() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.DATE_ADDED + "<=?");
            selectionArgs.add(String.valueOf(config.getMaxAddTime()));
        }
        if (config.getMinSize() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.SIZE + ">=?");
            selectionArgs.add(String.valueOf(config.getMinSize()));
        }
        if (config.getMaxSize() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.SIZE + "<=?");
            selectionArgs.add(String.valueOf(config.getMaxSize()));
        }
        if (config.getMinWidth() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.WIDTH + ">=?");
            selectionArgs.add(String.valueOf(config.getMinWidth()));
        }
        if (config.getMaxWidth() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.WIDTH + "<=?");
            selectionArgs.add(String.valueOf(config.getMaxWidth()));
        }
        if (config.getMinHeight() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.HEIGHT + ">=?");
            selectionArgs.add(String.valueOf(config.getMinHeight()));
        }
        if (config.getMaxHeight() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.MediaColumns.HEIGHT + "<=?");
            selectionArgs.add(String.valueOf(config.getMaxHeight()));
        }
        if (config.getMinVideoDuration() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.Video.VideoColumns.DURATION + ">=?");
            selectionArgs.add(String.valueOf(config.getMinVideoDuration()));
        }
        if (config.getMaxVideoDuration() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.Video.VideoColumns.DURATION + "<=?");
            selectionArgs.add(String.valueOf(config.getMaxVideoDuration()));
        }
        if (config.getMinAudioDuration() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.Audio.AudioColumns.DURATION + ">=?");
            selectionArgs.add(String.valueOf(config.getMinAudioDuration()));
        }
        if (config.getMaxAudioDuration() != MediaConfig.DEFAULT_VALUE) {
            selections.add(MediaStore.Audio.AudioColumns.DURATION + "<=?");
            selectionArgs.add(String.valueOf(config.getMaxAudioDuration()));
        }
        if (config.getImageMimes() != null) {
            List<String> inParams = new ArrayList<>();
            for (int i = 0, len = config.getImageMimes().size(); i < len; i++) {
                inParams.add("?");
            }
            selections.add(MediaStore.MediaColumns.MIME_TYPE + " in (" + TextUtils.join(",", inParams) + ")");
            selectionArgs.addAll(config.getImageMimes());
        }
        if (config.getVideoMimes() != null) {
            List<String> inParams = new ArrayList<>();
            for (int i = 0, len = config.getVideoMimes().size(); i < len; i++) {
                inParams.add("?");
            }
            selections.add(MediaStore.MediaColumns.MIME_TYPE + " in (" + TextUtils.join(",", inParams) + ")");
            selectionArgs.addAll(config.getVideoMimes());
        }
        if (config.getAudioMimes() != null) {
            List<String> inParams = new ArrayList<>();
            for (int i = 0, len = config.getAudioMimes().size(); i < len; i++) {
                inParams.add("?");
            }
            selections.add(MediaStore.MediaColumns.MIME_TYPE + " in (" + TextUtils.join(",", inParams) + ")");
            selectionArgs.addAll(config.getAudioMimes());
        }
        return new Pair<>(TextUtils.join(" and ", selections), selectionArgs.toArray(new String[0]));
    }

    private String[] getProjection(MediaConfig config) {
        Set<String> projectionSet = new HashSet<>(BASE_PROJECTION);
        if (config.isType(MediaConfig.IMAGE)) {
            projectionSet.addAll(IMAGE_PROJECTION);
        } else if (config.isType(MediaConfig.VIDEO)) {
            projectionSet.addAll(VIDEO_PROJECTION);
        } else if (config.isType(MediaConfig.IMAGE_VIDEO)) {
            projectionSet.addAll(IMAGE_PROJECTION);
            projectionSet.addAll(VIDEO_PROJECTION);
        } else if (config.isType(MediaConfig.AUDIO)) {
            projectionSet.addAll(AUDIO_PROJECTION);
        }
        return projectionSet.toArray(new String[0]);
    }

    private void handleDirInfo(Cursor cursor, DirInfoCallback callback) {
        DirInfo dirInfo;
        Map<String, DirInfo> map = new HashMap<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String path = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            String parentPath = path.substring(0, path.lastIndexOf("/"));
            if (map.containsKey(parentPath)) {
                dirInfo = map.get(parentPath);
                dirInfo.mCount++;
                dirInfo.mDirPath = parentPath;
                dirInfo.mDirName = parentPath.substring(path.lastIndexOf("/"));
            } else {
                dirInfo = new DirInfo(parentPath);
                dirInfo.mCount = 1;
                map.put(parentPath, dirInfo);
            }
        }
        Set<DirInfo> set = new HashSet<>();
        for (Map.Entry<String, DirInfo> entry : map.entrySet()) {
            set.add(entry.getValue());
        }
        if (callback != null) {
            callback.onDirInfoSet(set);
        }
    }

    private void handleBaseInfo(Cursor cursor, BaseInfoCallback callback) {
        List<BaseInfo> baseInfoList = new ArrayList<>();
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            baseInfoList.add(BaseInfo.from(cursor));
        }
        if (callback != null) {
            callback.onBaseInfo(baseInfoList);
        }
    }

    private String[] listToArray(List<String> list) {
        String[] array = new String[list.size()];
        for (int i = 0, size = list.size(); i < size; i++) {
            array[i] = list.get(i);
        }
        return array;
    }

    public void scanFile(Context context, BaseInfo baseInfo, MediaScannerConnection.OnScanCompletedListener listener) {
        if (baseInfo != null) {
            MediaScannerConnection.scanFile(context, new String[]{baseInfo.mPath}, new String[]{baseInfo.mMime}, listener);
        }
    }

    public void scanFile(Context context, List<BaseInfo> baseInfos, MediaScannerConnection.OnScanCompletedListener listener) {
        if (baseInfos != null && !baseInfos.isEmpty()) {
            int size = baseInfos.size();
            List<String> pathList = new ArrayList<>();
            List<String> mimeList = new ArrayList<>();
            BaseInfo baseInfo;
            for (int i = 0; i < size; i++) {
                baseInfo = baseInfos.get(i);
                pathList.add(baseInfo.mPath);
                mimeList.add(baseInfo.mMime);
            }
            MediaScannerConnection.scanFile(context, listToArray(pathList), listToArray(mimeList), listener);
        }
    }

    /**
     * Uri to file path.
     */
    public static String uriToPath(Context context, Uri uri) {
        if (isFileUri(uri)) {
            return uri.getPath();
        }
        String path = null;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            try {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                File file = new File(path);
                if (!file.exists()) {
                    path = null;
                    context.getContentResolver().delete(uri, null, null);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        return path;
    }

    public static Uri pathToUri(Context context, String path) {
        Uri uri = MediaStore.Files.getContentUri("external");
        final String _data = MediaStore.Images.Media.DATA;
        final String _id = MediaStore.MediaColumns._ID;
        Uri resultUri = null;
        Cursor cursor = context.getContentResolver().query(uri, new String[]{_data, _id},
                _data + " like ?", new String[]{path}, null);
        if (cursor != null && cursor.moveToFirst() && cursor.getCount() > 0) {
            try {
                String mediaPath = cursor.getString(cursor.getColumnIndex(_data));
                if (!mediaPath.equals(path)) {
                    context.getContentResolver().delete(uri, _data + " like ?", new String[]{mediaPath});
                } else if (!new File(path).exists()) {
                    context.getContentResolver().delete(uri, _data + " like ?", new String[]{path});
                } else {
                    int id = cursor.getInt(cursor.getColumnIndex(_id));
                    int type = MediaConstant.type(path);
                    if (type == MediaConstant.IMAGE) {
                        resultUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
                    } else if (type == MediaConstant.VIDEO) {
                        resultUri = ContentUris.withAppendedId(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, id);
                    } else if (type == MediaConstant.AUDIO) {
                        resultUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id);
                    } else {
                        resultUri = ContentUris.withAppendedId(uri, id);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (cursor != null) {
            cursor.close();
        }
        if (resultUri == null) {
            return Uri.fromFile(new File(path));
        }
        return resultUri;
    }

    public static boolean isContentUri(Uri uri) {
        if (uri != null) {
            return uri.getScheme().equals("content");
        }
        return false;
    }

    public static boolean isFileUri(Uri uri) {
        if (uri != null) {
            return uri.getScheme().equals("file");
        }
        return false;
    }

    // TODO: 2019-10-24 arrange this and AppUtil
    public List<ApkInfo> getApk() {
        final PackageManager pm = mContext.getPackageManager();
        //get a list of installed apps.
        List<ApplicationInfo> applicationInfos = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<ApkInfo> apkInfos = new ArrayList<>();
        ApkInfo apkInfo;
        for (ApplicationInfo applicationInfo : applicationInfos) {
            apkInfo = from(applicationInfo, pm);
            if (apkInfo != null) {
                apkInfos.add(apkInfo);
            }
        }
        return apkInfos;
    }

    private ApkInfo from(ApplicationInfo applicationInfo, PackageManager pm) {
        if ((applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            return null;
        }
        if ((applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0) {
            return null;
        }
        ApkInfo apkInfo = new ApkInfo();
        apkInfo.mName = applicationInfo.loadLabel(pm).toString();
        apkInfo.mPath = applicationInfo.sourceDir;
        apkInfo.mIconDrawable = applicationInfo.loadIcon(pm);
        if (apkInfo.mPath != null) {
            File file = new File(apkInfo.mPath);
            apkInfo.mSize = file.length();
            apkInfo.mAddDate = file.lastModified();
        }
        return apkInfo;
    }

    public ApkInfo getApk(String apkPath) {
        final PackageManager pm = mContext.getPackageManager();
        final ApplicationInfo applicationInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA).applicationInfo;
        return from(applicationInfo, pm);
    }

    public List<ApkInfo> getApk(String... apkPaths) {
        final PackageManager pm = mContext.getPackageManager();
        ApplicationInfo applicationInfo;
        ApkInfo apkInfo;
        List<ApkInfo> apkInfos = new ArrayList<>();
        for (String apkPath : apkPaths) {
            applicationInfo = pm.getPackageArchiveInfo(apkPath, PackageManager.GET_META_DATA).applicationInfo;
            apkInfo = from(applicationInfo, pm);
            if (apkInfo != null) {
                apkInfos.add(apkInfo);
            }
        }
        return apkInfos;
    }
}
