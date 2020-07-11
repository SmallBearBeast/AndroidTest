package com.example.administrator.androidtest.Test.MediaTest;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bear.librv.VHBridge;
import com.bear.librv.VHolder;
import com.example.administrator.androidtest.Common.Media.Info.AudioInfo;
import com.example.administrator.androidtest.Common.Media.Info.BaseInfo;
import com.example.administrator.androidtest.R;
import com.example.libbase.Util.DecimalUtil;

public class AudioBridge extends VHBridge {
    @Override
    protected int getSpanSize(RecyclerView rv) {
        if (rv != null && rv.getLayoutManager() instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) rv.getLayoutManager();
            return gridLayoutManager.getSpanCount();
        }
        return super.getSpanSize(rv);
    }

    @NonNull
    @Override
    protected VHolder onCreateViewHolder(@NonNull View itemView) {
        return new AudioHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_audio;
    }

    class AudioHolder extends VHolder{
        private TextView mTvAudioName;
        private TextView mTvAudioArtist;
        private TextView mTvAudioDuration;
        public AudioHolder(View itemView) {
            super(itemView);
            mTvAudioName = (TextView) findViewById(R.id.tv_audio_name);
            mTvAudioArtist = (TextView) findViewById(R.id.tv_audio_artist);
            mTvAudioDuration = (TextView) findViewById(R.id.tv_audio_duration);
        }

        @Override
        public void bindCursor(int pos, Cursor cursor) {
            BaseInfo baseInfo = BaseInfo.from(cursor);
            if (baseInfo instanceof AudioInfo) {
                AudioInfo audioInfo = (AudioInfo) baseInfo;
                mTvAudioName.setText(audioInfo.mName);
                mTvAudioArtist.setText(audioInfo.mSuffix);
                mTvAudioDuration.setText(DecimalUtil.videoDurFormat(audioInfo.mDuration));
            }
        }
    }
}
