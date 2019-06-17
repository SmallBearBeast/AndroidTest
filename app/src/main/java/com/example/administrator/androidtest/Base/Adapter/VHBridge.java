/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.administrator.androidtest.Base.Adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

public abstract class VHBridge<VH extends VHolder> {
    protected VHAdapter mAdapter;
    protected DataManager mDataManager;
    protected Context mContext;

    @NonNull
    protected abstract VH onCreateViewHolder(@NonNull View itemView);

    protected @LayoutRes int layoutId(){
        return -1;
    }

    protected View itemView(){
        return null;
    }


    protected final int getPosition(@NonNull final VHolder holder) {
        return holder.getAdapterPosition();
    }
}