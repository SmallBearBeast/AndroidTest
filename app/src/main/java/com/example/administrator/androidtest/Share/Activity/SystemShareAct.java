package com.example.administrator.androidtest.Share.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.androidtest.Base.ViewHolder;
import com.example.administrator.androidtest.Base.ComponentAct;
import com.example.administrator.androidtest.Common.Util.FileProviderUtil;
import com.example.administrator.androidtest.R;
import com.example.administrator.androidtest.Share.IntentShare;
import com.example.administrator.androidtest.Share.ShareUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SystemShareAct extends ComponentAct {
    private static final int REQUEST_IMAGE = 1001;
    private static final int REQUEST_VIDEO = 1002;
    private static final int REQUEST_IMAGE_OTHER = 1003;
    private static final int REQUEST_VIDEO_OTHER = 1004;
    private String mSharePackageName;

    RecyclerView mRvSystemShare;
    @Override
    protected int layoutId() {
        return R.layout.act_system_share;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        mRvSystemShare = findViewById(R.id.rv_system_share);
        mRvSystemShare.setLayoutManager(new LinearLayoutManager(mContext));
        mRvSystemShare.setAdapter(new MyAdapter());
    }


    private void choosePhoto(int requestCode){
        Intent it = new Intent(Intent.ACTION_PICK, null);
        // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
        it.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(it, requestCode);
    }

    private void chooseVideo(int requestCode){
        Intent it = new Intent(Intent.ACTION_PICK, null);
        it.setDataAndType(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, "video/*");
        startActivityForResult(it, requestCode);
    }


    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_1:
                IntentShare intentShare = new IntentShare();
                intentShare.setText("Hello World");
                ShareUtil.shareTextToOther(this, intentShare);
                break;
            case R.id.tv_2:
                choosePhoto(REQUEST_IMAGE_OTHER);
                break;
            case R.id.tv_3:
                chooseVideo(REQUEST_VIDEO_OTHER);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_IMAGE_OTHER:
            case REQUEST_IMAGE:
                if(data != null){
                    Uri imageUri = data.getData();
                    if(imageUri != null){
                        IntentShare intentShare = new IntentShare("Hello World", imageUri, null);
                        if(requestCode == REQUEST_IMAGE){
                            ShareUtil.shareImageTextToApp(mActivity, intentShare, mSharePackageName, null);
                        }else {
                            ShareUtil.shareImageTextToOther(mActivity, intentShare);
                        }
                    }
                }
                break;

            case REQUEST_VIDEO_OTHER:
            case REQUEST_VIDEO:
                if(data != null){
                    Uri videoUri = data.getData();
                    if(videoUri != null){
                        IntentShare intentShare = new IntentShare("Hello World", null, videoUri);
                        if(requestCode == REQUEST_VIDEO){
                            ShareUtil.shareVideoTextToApp(mActivity, intentShare, mSharePackageName, null);
                        }else {
                            ShareUtil.shareVideoTextToOther(mActivity, intentShare);
                        }
                    }
                }
                break;
        }
    }

    class MyAdapter extends RecyclerView.Adapter<ShareHolder>{

        private List<String> mData = new ArrayList<String>(){{
            add(IntentShare.PACKAGE_FB); //不支持text，支持纯图，纯视频
            add(IntentShare.PACKAGE_IMO); //支持纯text，支持纯图，纯视频
            add(IntentShare.PACKAGE_LINE);//支持纯text，支持纯图，纯视频
            add(IntentShare.PACKAGE_QQ); //支持纯text，支持纯图，纯视频
            add(IntentShare.PACKAGE_SNAPCHAT);
            add(IntentShare.PACKAGE_FACEBOOK_LITE); //支持纯text，支持纯图，不支持视频
            add(IntentShare.PACKAGE_MESSENGER); //支持纯text，支持纯图，纯视频
            add(IntentShare.PACKAGE_INSTAGRAM); //支持纯text，支持纯图，纯视频
            add(IntentShare.PACKAGE_TELEGRAM); //支持纯text，支持纯图，纯视频
            add(IntentShare.PACKAGE_TWITTER); //支持纯text，支持图文，视频文
            add(IntentShare.PACKAGE_WELIKE); //支持纯text，支持图文，不支持视频
            add(IntentShare.PACKAGE_WEIBO); //支持纯text，支持图文，视频文
        }};
        @NonNull
        @Override
        public ShareHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ShareHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_system_share, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ShareHolder holder, int position) {
            holder.bind(position, mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }
    }


    class ShareHolder extends ViewHolder<String> implements View.OnClickListener{
        private TextView mTvShareText;
        private TextView mTvShareImage;
        private TextView mTvShareVideo;
        private TextView mTvShareName;
        public ShareHolder(View itemView) {
            super(itemView);
            mTvShareText = itemView.findViewById(R.id.tv_1);
            mTvShareImage = itemView.findViewById(R.id.tv_2);
            mTvShareVideo = itemView.findViewById(R.id.tv_3);
            mTvShareName = itemView.findViewById(R.id.tv_share_name);
            mTvShareText.setOnClickListener(this);
            mTvShareImage.setOnClickListener(this);
            mTvShareVideo.setOnClickListener(this);
        }

        public void bind(int pos, String data){
            super.bind(pos, data);
            mTvShareName.setText(mData);
        }

        @Override
        public void onClick(View v) {
            mSharePackageName = mData;
            IntentShare intentShare = new IntentShare();
            intentShare.setText("Hello World");
            switch (v.getId()){
                case R.id.tv_1:
                    ShareUtil.shareTextToApp(mActivity, intentShare, mData, null);
                    break;

                case R.id.tv_2:
                    choosePhoto(REQUEST_IMAGE);
                    break;

                case R.id.tv_3:
                    File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "mymymymy.mp4");
                    intentShare.setVideoUri(FileProviderUtil.getUriForFile(file));
                    ShareUtil.shareVideoTextToApp(mActivity, intentShare, mData, null);
                    break;
            }
        }
    }
}
