package com.example.administrator.androidtest.Test.ShareTest;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;

import com.bear.libcomponent.ComponentAct;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Share.IntentShare;
import com.example.administrator.androidtest.Share.ShareUtil;
import com.example.administrator.androidtest.Test.XmlDrawableTest.XmlDrawableAct;
import com.example.libbase.Util.CollectionUtil;

import java.util.ArrayList;

public class SystemShareAct extends ComponentAct {
    private static final String TAG = "SystemShareAct";
    private static final int REQUEST_IMAGE = 1001;
    private static final int REQUEST_VIDEO = 1002;
    private static final int REQUEST_IMAGE_OTHER = 1003;
    private static final int REQUEST_VIDEO_OTHER = 1004;
    private static final int REQUEST_FILE = 1005;
    private static final int REQUEST_FILE_OTHER = 1006;
    private String mSharePackageName = IntentShare.PACKAGE_ANYSHARE;

    RecyclerView mRvSystemShare;

    @Override
    protected int layoutId() {
        return R.layout.act_system_share;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRvSystemShare = findViewById(R.id.rv_system_share);
        mRvSystemShare.setLayoutManager(new LinearLayoutManager(this));
//        mRvSystemShare.setAdapter(new MyAdapter());

        Integer[] aaa = new Integer[]{1, 2, 3};
        Log.i(TAG, "sss = " + CollectionUtil.isEmpty(aaa));
    }


    private void choosePhoto(int requestCode) {
        Intent it = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        it.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(it, requestCode);
    }

    private void chooseVideo(int requestCode) {
        Intent it = new Intent(Intent.ACTION_PICK, null);
        it.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*");
        startActivityForResult(it, requestCode);
    }

    private void chooseFile(int requestCode) {
        Intent it = new Intent(Intent.ACTION_GET_CONTENT, null);
        it.setType("*/*");
        startActivityForResult(it, requestCode);
    }


    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_1:
                IntentShare intentShare = new IntentShare().setText("Hello World");
                ShareUtil.shareTextToOther(this, intentShare);
                break;
            case R.id.tv_2:
                choosePhoto(REQUEST_IMAGE_OTHER);
                break;
            case R.id.tv_3:
//                chooseVideo(REQUEST_VIDEO_OTHER);
                startActivity(new Intent(this, XmlDrawableAct.class));
                break;
            case R.id.tv_4:
//                chooseFile(REQUEST_FILE);
                startActivity(new Intent(this, XmlDrawableAct.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_IMAGE_OTHER:
            case REQUEST_IMAGE:
                if (data != null) {
                    Uri imageUri = data.getData();
                    if (imageUri != null) {
                        IntentShare intentShare = new IntentShare("Hello World", imageUri, null);
                        if (requestCode == REQUEST_IMAGE) {
                            ShareUtil.shareImageTextToApp(this, intentShare, mSharePackageName, null);
                        } else {
                            ShareUtil.shareImageTextToOther(this, intentShare);
                        }
                    }
                }
                break;

            case REQUEST_VIDEO_OTHER:
            case REQUEST_VIDEO:
                if (data != null) {
                    Uri videoUri = data.getData();
                    if (videoUri != null) {
                        IntentShare intentShare = new IntentShare("Hello World", null, videoUri);
                        if (requestCode == REQUEST_VIDEO) {
                            ShareUtil.shareVideoTextToApp(this, intentShare, mSharePackageName, null);
                        } else {
                            ShareUtil.shareVideoTextToOther(this, intentShare);
                        }
                    }
                }
                break;

            case REQUEST_FILE:
            case REQUEST_FILE_OTHER:
                if (data != null) {
                    Uri fileUri = data.getData();
                    if (fileUri != null) {
                        mFileUriList.add(fileUri);
                        if (requestCode == REQUEST_FILE) {
                            if (mFileUriList.size() > 1) {
                                IntentShare intentShare = new IntentShare("Hello World", null, fileUri).setFileUriList(mFileUriList);
                                ShareUtil.shareMulFileTextToApp(this, intentShare, mSharePackageName);
                            }
                        } else {
//                            ShareUtil.shareVideoTextToOther(this, intentShare);
                        }
                    }
                }
                break;
        }
    }

    private ArrayList<Uri> mFileUriList = new ArrayList<>(4);
//    class MyAdapter extends RecyclerView.VHAdapter<ShareHolder>{
//
//        private List<String> data = new ArrayList<String>(){{
//            add(IntentShare.PACKAGE_FB); //不支持text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_IMO); //支持纯text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_LINE);//支持纯text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_QQ); //支持纯text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_SNAPCHAT);
//            add(IntentShare.PACKAGE_FACEBOOK_LITE); //支持纯text，支持纯图，不支持视频
//            add(IntentShare.PACKAGE_MESSENGER); //支持纯text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_INSTAGRAM); //支持纯text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_TELEGRAM); //支持纯text，支持纯图，纯视频
//            add(IntentShare.PACKAGE_TWITTER); //支持纯text，支持图文，视频文
//            add(IntentShare.PACKAGE_WELIKE); //支持纯text，支持图文，不支持视频
//            add(IntentShare.PACKAGE_WEIBO); //支持纯text，支持图文，视频文
//        }};
//        @NonNull
//        @Override
//        public ShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            return new ShareHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_share, parent, false));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ShareHolder holder, int position) {
//            holder.bind(position, data.get(position));
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//    }


//    class ShareHolder extends VHolder<String> implements View.OnClickListener{
//        private TextView mTvShareText;
//        private TextView mTvShareImage;
//        private TextView mTvShareVideo;
//        private TextView mTvShareName;
//        public ShareHolder(View itemView) {
//            super(itemView);
//            mTvShareText = itemView.findViewById(R.id.tv_1);
//            mTvShareImage = itemView.findViewById(R.id.tv_2);
//            mTvShareVideo = itemView.findViewById(R.id.tv_3);
//            mTvShareName = itemView.findViewById(R.id.tv_share_name);
//            mTvShareText.clickListener(this);
//            mTvShareImage.clickListener(this);
//            mTvShareVideo.clickListener(this);
//        }
//
//        public void bind(int pos, String data){
//            super.bind(pos, data);
//            mTvShareName.setText(data);
//        }
//
//        @Override
//        public void onClick(View v) {
//            mSharePackageName = data;
//            IntentShare intentShare = new IntentShare();
//            intentShare.setText("Hello World");
//            switch (v.getId()){
//                case R.id.tv_1:
//                    ShareUtil.shareTextToApp(mActivity, intentShare, data, null);
//                    break;
//
//                case R.id.tv_2:
//                    choosePhoto(REQUEST_IMAGE);
//                    break;
//
//                case R.id.tv_3:
//                    File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "mymymymy.mp4");
//                    intentShare.setVideoUri(FileProviderUtil.getUriForFile(file));
//                    ShareUtil.shareVideoTextToApp(mActivity, intentShare, data, null);
//                    break;
//            }
//        }
//    }
}
