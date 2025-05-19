package com.example.administrator.androidtest.demo.OtherDemo.MediaStoreDemo;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bear.librv.MultiTypeDelegate;
import com.bear.librv.MultiTypeHolder;
import com.example.administrator.androidtest.other.fileMedia.Info.AudioInfo;
import com.example.administrator.androidtest.other.fileMedia.Info.BaseInfo;
import com.example.administrator.androidtest.R;
import com.example.libcommon.Util.DecimalUtil;

public class AudioDelegate extends MultiTypeDelegate<Cursor, AudioDelegate.AudioHolder> {
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
    protected AudioHolder onCreateViewHolder(@NonNull View itemView) {
        return new AudioHolder(itemView);
    }

    @Override
    protected int layoutId() {
        return R.layout.item_audio;
    }

    public static class AudioHolder extends MultiTypeHolder<Cursor> {
        private final TextView tvAudioName;
        private final TextView tvAudioArtist;
        private final TextView tvAudioDuration;

        public AudioHolder(View itemView) {
            super(itemView);
            tvAudioName = (TextView) findViewById(R.id.tv_audio_name);
            tvAudioArtist = (TextView) findViewById(R.id.tv_audio_artist);
            tvAudioDuration = (TextView) findViewById(R.id.tv_audio_duration);
        }

        @Override
        public void bindCursor(int pos, Cursor cursor) {
            BaseInfo baseInfo = BaseInfo.from(cursor);
            if (baseInfo instanceof AudioInfo) {
                AudioInfo audioInfo = (AudioInfo) baseInfo;
                tvAudioName.setText(audioInfo.mName);
                tvAudioArtist.setText(audioInfo.mSuffix);
                tvAudioDuration.setText(DecimalUtil.videoDurFormat(audioInfo.mDuration));
            }
        }
    }
}
