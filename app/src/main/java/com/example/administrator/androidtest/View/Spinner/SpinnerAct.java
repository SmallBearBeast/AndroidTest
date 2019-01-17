package com.example.administrator.androidtest.View.Spinner;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.example.administrator.androidtest.Base.ActAndFrag.ComponentAct;
import com.example.administrator.androidtest.R;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAct extends ComponentAct {
    private static final String TAG = "SpinnerAct_TAG";
    AppCompatSpinner spinner;
    @Override
    protected int layoutId() {
        return R.layout.act_spinner;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        super.init(savedInstanceState);
        spinner = findViewById(R.id.sp_text);
        spinner.setAdapter(new MyAdapter());
    }

    private class MyAdapter implements SpinnerAdapter {

        private List<String> mList = new ArrayList<String>(){{
           add("星期一"); add("星期二"); add("星期三"); add("星期四"); add("星期五"); add("星期六");add("星期日");
        }};


        @Override
        public void registerDataSetObserver(DataSetObserver observer) {
            Log.d(TAG, "registerDataSetObserver() called with: observer = [" + observer + "]");
        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver observer) {
            Log.d(TAG, "unregisterDataSetObserver() called with: observer = [" + observer + "]");
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");
            EditText editText = null;
            if(convertView == null){
                convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_edit, parent, false);
            }
            editText = (EditText) convertView;
            editText.setText(mList.get(position));
            return convertView;
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getDropDownView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");
            Log.d(TAG, "getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");
            TextView textView = null;
            if(convertView == null){
                convertView =  LayoutInflater.from(parent.getContext()).inflate(R.layout.item_spinner_text, parent, false);
            }
            textView = (TextView) convertView;
            textView.setText(mList.get(position));
            return textView;
        }
    }
}
