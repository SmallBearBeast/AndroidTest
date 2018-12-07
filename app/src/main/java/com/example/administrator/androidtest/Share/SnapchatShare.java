package com.example.administrator.androidtest.Share;

import android.content.Context;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.example.administrator.androidtest.Common.Util.FileProviderUtil;
import com.snapchat.kit.sdk.SnapCreative;
import com.snapchat.kit.sdk.creative.api.SnapCreativeKitApi;
import com.snapchat.kit.sdk.creative.media.SnapMediaFactory;
import com.snapchat.kit.sdk.creative.media.SnapPhotoFile;
import com.snapchat.kit.sdk.creative.media.SnapVideoFile;
import com.snapchat.kit.sdk.creative.models.SnapPhotoContent;
import com.snapchat.kit.sdk.creative.models.SnapVideoContent;

import java.io.File;

public class SnapchatShare {
    public static void shareImage(Context context){
        SnapCreativeKitApi api = SnapCreative.getApi(context);
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(context);
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "computer.jpg");
            if(file.exists()) {
                String s = null;
                SnapPhotoFile snapPhotoFile = snapMediaFactory.getSnapPhotoFromFile(file);
                SnapPhotoContent snapPhotoContent = new SnapPhotoContent(snapPhotoFile);
                api.send(snapPhotoContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            api = null;
            snapMediaFactory = null;
        }
    }

    public static void shareText(Context context){

    }

    public static void shareVideo(Context context){
        SnapCreativeKitApi api = SnapCreative.getApi(context);
        SnapMediaFactory snapMediaFactory = SnapCreative.getMediaFactory(context);
        try {
            File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "my.mp4");
            if(file.exists()){
                SnapVideoFile snapVideoFile = snapMediaFactory.getSnapVideoFromFile(file);
                SnapVideoContent snapVideoContent = new SnapVideoContent(snapVideoFile);
                snapVideoContent.setAttachmentUrl("https://www.huya.com/");
                api.send(snapVideoContent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            api = null;
            snapMediaFactory = null;
        }
    }
}
