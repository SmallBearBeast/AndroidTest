package com.bear.librv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import java.util.List;

public class MultiTypeAdapter extends RecyclerView.Adapter<MultiTypeHolder<Object>>
        implements LifecycleEventObserver {
    protected String TAG = RvLog.RV_LOG_TAG + "-" + getClass().getSimpleName();
    private LayoutInflater inflater;
    private RecyclerView recyclerView;
    private MultiItemChanger multiItemChanger;
    private Context context;
    private Lifecycle lifecycle;
    private MultiTypeContainer multiTypeContainer;

    public MultiTypeAdapter(Lifecycle l) {
        multiItemChanger = new MultiItemChanger();
        multiItemChanger.attachAdapter(this);
        multiTypeContainer = new MultiTypeContainer();
        lifecycle = l;
    }

    @Override
    public int getItemCount() {
        return multiItemChanger.size();
    }

    @NonNull
    @Override
    public MultiTypeHolder<Object> onCreateViewHolder(@NonNull
                                                          ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        MultiTypeHolder<Object> vh = null;
        MultiTypeDelegate<Object, ?> delegate = multiTypeContainer.getType(viewType).getDelegate();
        if (delegate != null) {
            View view = delegate.itemView();
            if (view == null) {
                int layoutId = delegate.layoutId();
                if (layoutId != -1) {
                    view = inflater.inflate(layoutId, parent, false);
                }
            }
            if (view != null) {
                setUpStaggerFullSpan(view, delegate);
                vh = delegate.onCreateViewHolder(view);
            } else {
                vh = delegate.onCreateViewHolder(parent, viewType);
            }
            vh.attachDelegate(delegate);
            if (lifecycle != null && delegate.isSupportLifecycle()) {
                lifecycle.addObserver(vh);
            }
        }
        if (vh == null) {
            throw new RuntimeException("VHolder is null");
        }
        return vh;
    }

    private void setUpStaggerFullSpan(View itemView, MultiTypeDelegate<?, ?> delegate) {
        if (!delegate.isFullSpan()) {
            return;
        }
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) itemView.getLayoutParams();
            lp.setFullSpan(true);
            itemView.setLayoutParams(lp);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MultiTypeHolder<Object> holder, int position) {
        holder.bindFull(position, multiItemChanger.get(position));
    }

    @Override
    public void onBindViewHolder(@NonNull MultiTypeHolder<Object> holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads);
        } else {
            for (Object payload : payloads) {
                if (payload instanceof Payload) {
                    holder.bindPartial(multiItemChanger.get(position), (Payload) payload);
                } else {
                    super.onBindViewHolder(holder, position, payloads);
                }
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        Object data = multiItemChanger.get(position);
        int index = multiTypeContainer.getItemViewType(data, position);
        if (index != -1) {
            return index;
        }
        throw new RuntimeException("Have you register the " + data.getClass().getName() + " and its delegate");
    }

    public <ITEM> void register(Class<ITEM> clz, MultiTypeDelegate<ITEM, ?> delegate) {
        register(new Type<>(clz, delegate, new DefaultTypeLinker<>()));
    }

    public <ITEM> void register(@NonNull Type<ITEM> type) {
        if (multiTypeContainer.contain(type)) {
            RvLog.w(TAG, type.getClazz().getName() + " has registered");
            return;
        }
        MultiTypeDelegate<ITEM, ?> delegate = type.getDelegate();
        if (context != null) {
            delegate.attachContext(context);
        }
        if (recyclerView != null) {
            delegate.attachRecyclerView(recyclerView);
        }
        delegate.attachAdapter(this);
        delegate.attachChanger(multiItemChanger);
        multiTypeContainer.register(type);
    }

    public <ITEM> OneToManyBuilder<ITEM> register(Class<ITEM> clazz) {
        return new OneToManyBuilder<>(this, clazz);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView rv) {
        recyclerView = rv;
        context = rv.getContext();
        if (lifecycle != null) {
            lifecycle.addObserver(this);
        }
        List<MultiTypeDelegate<?, ?>> delegateList = multiTypeContainer.getDelegateList();
        for (MultiTypeDelegate<?, ?> delegate : delegateList) {
            if (context != null) {
                delegate.attachContext(context);
            }
            if (recyclerView != null) {
                delegate.attachRecyclerView(recyclerView);
            }
        }
        setUpGridSpanSize();
    }

    private void setUpGridSpanSize() {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                public int getSpanSize(int position) {
                    int index = getItemViewType(position);
                    Type<?> type = multiTypeContainer.getType(index);
                    MultiTypeDelegate<?, ?> delegate = type.getDelegate();
                    if (delegate.isFullSpan()) {
                        return gridLayoutManager.getSpanCount();
                    } else {
                        return delegate.getSpanSize(recyclerView);
                    }
                }
            });
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            source.getLifecycle().removeObserver(this);
            inflater = null;
            recyclerView = null;
            multiItemChanger.clear();
            multiItemChanger = null;
            multiTypeContainer.clear();
            multiTypeContainer = null;
            context = null;
            lifecycle = null;
        }
    }

    public MultiItemChanger getChanger() {
        return multiItemChanger;
    }

    public boolean isRegister(Object item) {
        return multiTypeContainer.contain(item.getClass());
    }
}
