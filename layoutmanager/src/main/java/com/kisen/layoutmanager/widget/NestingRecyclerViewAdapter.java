package com.kisen.layoutmanager.widget;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;

/**
 * 自定义Adapter容器，使列表可以无限循环显示
 * Created by kisen on 2017/12/19.
 * email: kisenhuang@163.com.
 */
public class NestingRecyclerViewAdapter extends RecyclerView.Adapter {

    private LayoutManagerHelper mLayoutManagerHelper;
    private RecyclerView.Adapter mAdapter;


    NestingRecyclerViewAdapter(LayoutManagerHelper layoutManagerHelper, RecyclerView.Adapter adapter) {
        mAdapter = adapter;
        mLayoutManagerHelper = layoutManagerHelper;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        mAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        mAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(ViewHolder holder, int position) {
        mAdapter.onBindViewHolder(holder, generatePosition(position));
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        mAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public int getItemCount() {
        //如果是无限滚动模式，设置有无限个item
        return getLoopEnable() ? Integer.MAX_VALUE : mAdapter.getItemCount();
    }

    @Override
    public int getItemViewType(int position) {
        return mAdapter.getItemViewType(generatePosition(position));
    }

    @Override
    public long getItemId(int position) {
        return mAdapter.getItemId(generatePosition(position));
    }

    /**
     * 根据当前滚动模式返回对应position
     */
    private int generatePosition(int position) {
        return getLoopEnable() ? getActualPosition(position) : position;
    }

    /**
     * 返回Item实际的位置
     *
     * @param position 开始滚动以后的位置，会无限增长
     * @return Item实际位置
     */
    private int getActualPosition(int position) {
        int itemCount = mAdapter.getItemCount();
        return position >= itemCount ? position % itemCount : position;

    }

    private boolean getLoopEnable() {
        return mLayoutManagerHelper.isLoopEnabled();
    }
}
