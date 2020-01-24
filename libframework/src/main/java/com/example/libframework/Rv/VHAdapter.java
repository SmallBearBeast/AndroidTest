package com.example.libframework.Rv;

import android.annotation.SuppressLint;
import androidx.lifecycle.GenericLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import android.content.Context;
import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressLint({"RestrictedApi"})
@SuppressWarnings("unchecked")
public class VHAdapter<VH extends VHolder> extends RecyclerView.Adapter<VH> implements GenericLifecycleObserver {
    protected String TAG = RvConstant.RV_LOG_TAG + "-" + getClass().getSimpleName();
    private static final int DATA_TYPE_LIMIT = 100;
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private DataManager mDataManager;
    private Map<Integer, Integer> mClzMap = new HashMap<>();
    private SparseArray<VHBridge> mBridgeMap = new SparseArray<>();
    private int mIncrease = DATA_TYPE_LIMIT;
    private Context mContext; //通过外部传入好还是onAttachedToRecyclerView拿去

    public VHAdapter() {
        mDataManager = new DataManager();
        mDataManager.setAdapter(this);
    }

    @Override
    public int getItemCount() {
        return mDataManager.size();
    }


    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (mInflater == null) {
            mInflater = LayoutInflater.from(parent.getContext());
        }
        VHBridge bridge = mBridgeMap.get(viewType);
        if (bridge != null) {
            View view = bridge.itemView();
            if (view == null) {
                int layoutId = bridge.layoutId();
                if (layoutId != -1) {
                    view = mInflater.inflate(layoutId, parent, false);
                }
            }
            if (view != null) {
                setUpStaggerFullSpan(view, bridge);
                return (VH) bridge.onCreateViewHolder(view);
            }
            VHolder vh = bridge.onCreateViewHolder(parent, viewType);
            if (vh != null) {
                return (VH) vh;
            }
        }
        return null;
    }

    private void setUpStaggerFullSpan(View itemView, VHBridge bridge) {
        if (!bridge.isFullSpan()) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            lp.setFullSpan(true);
            itemView.setLayoutParams(lp);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        holder.bindFull(position, mDataManager.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            for (Object payload : payloads) {
                if (payload instanceof Notify) {
                    holder.bindPartial(mDataManager.get(position), (Notify) payload);
                } else {
                    super.onBindViewHolder(holder, position, payloads);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object data = mDataManager.get(position);
        if (mOnGetDataType != null) {
            int type = mOnGetDataType.getType(data, position);
            if (type != -1) {
                return mClzMap.get(type);
            }
        }
        if (data instanceof Cursor) {
            return mClzMap.get(Cursor.class.hashCode());
        }
        if (mClzMap.containsKey(data.getClass().hashCode())) {
            return mClzMap.get(data.getClass().hashCode());
        }
        return super.getItemViewType(position);
    }

    /**
     * register bridge with many class
     */
    public void register(VHBridge bridge, Class... clzs) {
        for (Class clz : clzs) {
            if (clz == null) {
                continue;
            }
            int dataType = clz.hashCode();
            registerInternal(bridge, dataType, false);
        }
    }

    /**
     * This method should be used in conjunction with the {@link OnGetDataType}
     * @param bridge The VHBridge to be registered
     * @param dataType DataType definition is less than 100
     */
    public void register(VHBridge bridge, int dataType) {
        registerInternal(bridge, dataType, true);
    }

    private void registerInternal(VHBridge bridge, int dataType, boolean fromSetup) {
        if (fromSetup && dataType > DATA_TYPE_LIMIT) {
            Log.w(TAG, "registerInternal: dataType is out of range");
            return;
        }
        if (mClzMap.containsKey(dataType)) {
            return;
        }
        bridge.onInitAdapterAndManager(this, mDataManager);
        if (mRecyclerView != null && mContext != null) {
            bridge.onInitRvAndContext(mRecyclerView, mContext);
        }
        mIncrease++;
        bridge.mType = mIncrease;
        mBridgeMap.put(mIncrease, bridge);
        mClzMap.put(dataType, mIncrease);
    }

    public boolean isRegister(Object obj) {
        if (obj == null) {
            return false;
        }
        if (mOnGetDataType != null) {
            int type = mOnGetDataType.getType(obj, -1);
            if (type != -1) {
                return mClzMap.containsKey(type);
            }
        }
        if (obj instanceof Cursor) {
            return mClzMap.containsKey(Cursor.class.hashCode());
        }
        return mClzMap.containsKey(obj.getClass().hashCode());
    }

    public DataManager getDataManager() {
        return mDataManager;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mContext = recyclerView.getContext();
        if (mContext instanceof LifecycleOwner) {
            LifecycleOwner owner = (LifecycleOwner) mContext;
            owner.getLifecycle().addObserver(this);
        }
        for (int i = 0, size = mBridgeMap.size(); i < size; i++) {
            VHBridge bridge = mBridgeMap.valueAt(i);
            bridge.onInitRvAndContext(mRecyclerView, mContext);
        }
        setUpGridSpanSize();
    }

    private void setUpGridSpanSize() {
        if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager)mRecyclerView.getLayoutManager();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    for (int i = 0, size = mBridgeMap.size(); i < size; i++) {
                        if (mBridgeMap.valueAt(i).mType == type) {
                            return mBridgeMap.valueAt(i).getSpanSize(mRecyclerView);
                        }
                    }
                    return 1;
                }
            });
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner source, Lifecycle.Event event) {
        if (mRecyclerView != null) {
            int count = mRecyclerView.getChildCount();
            for (int i = 0; i < count; i++) {
                RecyclerView.ViewHolder viewHolder = mRecyclerView.getChildViewHolder(mRecyclerView.getChildAt(i));
                if (viewHolder instanceof GenericLifecycleObserver) {
                    ((GenericLifecycleObserver) viewHolder).onStateChanged(source, event);
                }
            }
        }
        if (event == Lifecycle.Event.ON_DESTROY) {
            mDataManager.clear();
            source.getLifecycle().removeObserver(this);
        }
    }

    public interface OnGetDataType {
        int getType(Object obj, int pos);
    }

    private OnGetDataType mOnGetDataType;

    public void setOnGetDataType(OnGetDataType onGetDataType) {
        mOnGetDataType = onGetDataType;
    }
}
