package com.example.administrator.androidtest.Test.FrescoTest;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.Fresco.FrescoUriTransfer;
import com.example.administrator.androidtest.Fresco.FrescoUtil;
import com.example.administrator.androidtest.Fresco.FrescoView;
import com.example.administrator.androidtest.Fresco.Listener.TimeRequestListener;
import com.example.administrator.androidtest.R;
import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.request.ImageRequest;

import java.io.File;

public class FrescoTestAct extends ComponentAct {
    private static final String TAG = "FrescoTestAct";
    private static final String TEST_URL_1 = "http://www.badcookie.com/ku-xlarge.gif";
    private static final String TEST_URL_2 = "https://images.pexels.com/photos/390658/pexels-photo-390658.jpeg";
    private static final String TEST_URL_3 = "https://images.unsplash.com/photo-1555538995-7181cc10e079?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=633&q=80";
    private static final String TEST_URL_4 = "https://p.upyun.com/demo/webp/webp/animated-gif-0.webp";
    private static final String TEST_URL_5 = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "hello.jpg";
    private static final String TEST_URL_6 = "https://p.upyun.com/demo/webp/webp/jpg-0.webp";
    private FrescoView mFvImage;

    @Override
    protected int layoutId() {
        return R.layout.act_fresco;
    }

    private long mStartTime = 0L;
    @Override
    protected void init(Bundle savedInstanceState) {
        mFvImage = findViewById(R.id.fv_image);
        ImageRequest request = FrescoUtil.defaultRequestBuilder(Uri.parse(TEST_URL_3), -1, -1, null).build();
        FrescoUtil.prefetchToDiskCache(request, Priority.HIGH);
        findViewById(R.id.bt_load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FrescoUtil.download("https://images.pexels.com/photos/390658/pexels-photo-390658.jpeg", Environment.getExternalStorageDirectory().getAbsolutePath(), "zhangqing");
                mFvImage.getHierarchy().setProgressBarImage(new ProgressBarDrawable());
                mFvImage.getHierarchy().setPlaceholderImage(R.drawable.ic_launcher_background);
                mFvImage.setImageUri(FrescoUriTransfer.urlUri(TEST_URL_1), new TimeRequestListener());
//                FrescoUtil.fetchDecodedImage(TEST_URL_3, new FrescoUtil.BitmapGetCallback() {
//                    @Override
//                    public void bitmap(Bitmap bitmap) {
//                        mFvImage.setImageBitmap(bitmap);
//                    }
//                });
//                mFvImage.setImageUri(FrescoUriTransfer.urlUri(TEST_URL_2), null, null, new RequestListener() {
//                    @Override
//                    public void onRequestStart(ImageRequest request, Object callerContext, String requestId, boolean isPrefetch) {
//                        Log.i(TAG, "onRequestStart: ");
//                    }
//
//                    @Override
//                    public void onRequestSuccess(ImageRequest request, String requestId, boolean isPrefetch) {
//                        Log.i(TAG, "onRequestSuccess: ");
//                    }
//
//                    @Override
//                    public void onRequestFailure(ImageRequest request, String requestId, Throwable throwable, boolean isPrefetch) {
//                        Log.i(TAG, "onRequestFailure: ");
//                    }
//
//                    @Override
//                    public void onRequestCancellation(String requestId) {
//                        Log.i(TAG, "onRequestCancellation: ");
//                    }
//
//                    @Override
//                    public void onProducerStart(String requestId, String producerName) {
//                        Log.i(TAG, "onProducerStart: ");
//                    }
//
//                    @Override
//                    public void onProducerEvent(String requestId, String producerName, String eventName) {
//                        Log.i(TAG, "onProducerEvent: ");
//                    }
//
//                    @Override
//                    public void onProducerFinishWithSuccess(String requestId, String producerName,  Map<String, String> extraMap) {
//                        Log.i(TAG, "onProducerFinishWithSuccess: ");
//                    }
//
//                    @Override
//                    public void onProducerFinishWithFailure(String requestId, String producerName, Throwable t,  Map<String, String> extraMap) {
//                        Log.i(TAG, "onProducerFinishWithFailure: ");
//                    }
//
//                    @Override
//                    public void onProducerFinishWithCancellation(String requestId, String producerName,  Map<String, String> extraMap) {
//                        Log.i(TAG, "onProducerFinishWithCancellation: ");
//                    }
//
//                    @Override
//                    public void onUltimateProducerReached(String requestId, String producerName, boolean successful) {
//                        Log.i(TAG, "onUltimateProducerReached: ");
//                    }
//
//                    @Override
//                    public boolean requiresExtraMap(String requestId) {
//                        return true;
//                    }
//                });
            }
        });
    }
}
