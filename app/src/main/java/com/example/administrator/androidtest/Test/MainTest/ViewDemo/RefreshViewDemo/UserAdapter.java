package com.example.administrator.androidtest.Test.MainTest.ViewDemo.RefreshViewDemo;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.androidtest.R;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new UserViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, null));
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class UserViewHolder extends BaseViewHolder<User>{

        public UserViewHolder(View itemView) {
            super(itemView);
        }
    }
}
