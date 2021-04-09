package com.example.administrator.androidtest.Widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DropTextView extends androidx.appcompat.widget.AppCompatEditText implements TextWatcher, View.OnClickListener {
    private static final int RESULT_TEXT_WINDOW_HEIGHT = 500;
    private static final int HISTORY_SEARCH_MAX_COUNT = 10;
    private List<String> mOriArray = Arrays.asList("星期一", "星期一", "星期一", "星期一", "星期一", "星期一", "星期一", "星期一");
    private List<String> mHisArray = Arrays.asList("晴晴", "晴晴", "晴晴");
    private int mTvLayoutId = -1;
    private int mResultWindowHeight = RESULT_TEXT_WINDOW_HEIGHT;
    private boolean mIsFromNet;
    private boolean mIsSearching;
    private String mSearchStr = "";
    private Context mContext;
    private RecyclerView mRvResultText;
    private ResultTextAdapter mResultTextAdapter;
    private PopupWindow mPopupWindow;
    private LayoutInflater mInflater;
    private IResultTextListener mListener;
    public DropTextView(Context context) {
        this(context, null);
    }

    public DropTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.DropTextView);
        mTvLayoutId = typedArray.getResourceId(R.styleable.DropTextView_dtv_layout_id, -1);
        mResultWindowHeight = typedArray.getInteger(R.styleable.DropTextView_dtv_window_height, RESULT_TEXT_WINDOW_HEIGHT);
        typedArray.recycle();
        initEdit();
        initRecyclerView();
        initHistory();
        // TODO: 2018/12/20添加删除图标和搜索图标
    }

    private void initHistory() {
        // TODO: 2018/12/20 加载历史搜索数据
//        SPUtil.fromSetting(mContext, )
//        MmpUtil.mapFile(mContext.getFilesDir().getAbsolutePath(), "drop_text_view.txt", MmpUtil.MODE_WRITE, "123");
//        MmpUtil.read()
        Map<Long, String> map = new HashMap<>();

        List<Map.Entry<Long, String>> list = new ArrayList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Long, String>>() {
            @Override
            public int compare(Map.Entry<Long, String> o1, Map.Entry<Long, String> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
    }

    private void initEdit() {
        // TODO: 2018/12/20 edittext基本设置
        addTextChangedListener(this);
        setOnClickListener(this);
        TextViewCompat.setCompoundDrawablesRelativeWithIntrinsicBounds(this, R.drawable.delete, 0, R.drawable.search, 0);
    }

    private void initRecyclerView() {
        mRvResultText = new RecyclerView(mContext);
        mRvResultText.setLayoutManager(new LinearLayoutManager(mContext));
        mResultTextAdapter = new ResultTextAdapter();
        mRvResultText.setAdapter(mResultTextAdapter);
    }

    private void setUpPopupWindow() {
        mPopupWindow = new PopupWindow(getMeasuredWidth(), mResultWindowHeight);
        mPopupWindow.setTouchable(true);
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopupWindow.setContentView(mRvResultText);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 默认是本地搜索，如果需要联网搜索，需要重写该方法发送搜索请求加载推荐列表
     */
    @Override
    public void afterTextChanged(Editable s) {
        // TODO: 2018/12/20 判断是否点击列表
        mSearchStr = String.valueOf(s);
        if(mIsFromNet && mListener != null){
            mListener.onPrepareSearch(mSearchStr);
        }
        else {
            List<String> result = new ArrayList<>();
            if(mListener != null){

            }
            if(mOriArray != null && !mSearchStr.trim().equals("")) {
                for (String text : mOriArray) {
                    if(text.contains(mSearchStr)){
                        result.add(text);
                    }
                }
            }
            showWindow(result);
        }
    }

    public void showWindow(List<String> result){
        if(result.isEmpty()){
            result = mHisArray;
        }
        mResultTextAdapter.setResultArray(result);
        if(mPopupWindow == null){
            setUpPopupWindow();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mPopupWindow.showAsDropDown(this, 0, 0, Gravity.CENTER);
        }
    }

    @Override
    public void onClick(View v) {
        if(mSearchStr.trim().equals("")){
            showWindow(mHisArray);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mPopupWindow.showAsDropDown(this, 0, 0, Gravity.CENTER);
            }
        }
    }

    class ResultTextAdapter extends RecyclerView.Adapter implements View.OnClickListener{
        private List<String> mResultArray;
        private String mResultStr;
        private int mPos;
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RecyclerView.ViewHolder(mInflater.inflate(mTvLayoutId, parent, false)){};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder.itemView instanceof TextView){
                mPos = position;
                mResultStr = mResultArray.get(position);
                TextView tv = (TextView) holder.itemView;
                tv.setText(mResultStr);
                tv.setOnClickListener(this);
            }
        }

        @Override
        public int getItemCount() {
            return mResultArray == null ? 0 : mResultArray.size();
        }

        public void setResultArray(List<String> resultArray){
            mResultArray = resultArray;
            notifyDataSetChanged();
        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                mListener.onResult(mResultStr, mPos);
            }
            mPopupWindow.dismiss();
            mSearchStr = mResultStr;
            mIsSearching = true;
            setText(mSearchStr);
        }
    }


    /**
     * 本地搜索设置搜索源
     */
    public void setOriArray(List<String> oriArray){
        if(oriArray != null) {
            mOriArray = oriArray;
        }
    }


    /**
     * 设置历史搜索和推荐搜索
     */
    public void setHisArray(List<String> hisArray){
        if(hisArray != null){
            mHisArray = hisArray;
        }
    }

    public void setListener(IResultTextListener listener){
        mListener = listener;
    }

    public void setIsFromNet(boolean isFromNet){
        mIsFromNet = isFromNet;
    }

    public interface IResultTextListener{
        void onResult(String text, int pos);

        void onPrepareSearch(String text);
    }

}
