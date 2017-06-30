package com.kisen.lib;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.Interpolator;

/**
 * 标题：自动滚动RecyclerView
 * 作者：kisen
 * 版本：
 * 创建时间：on 2017/6/30 13:29.
 */
public class AutoScrollRecyclerView extends RecyclerView {

    private static final String TAG = AutoScrollRecyclerView.class.getSimpleName();
    private static final int SPEED = 100;
    private UniformSpeedInterpolator mInterpolator;
    private int mSpeedDx, mSpeedDy;
    private int currentSpeed = SPEED;
    private RecyclerViewPagerAdapter<?> mViewPagerAdapter;
    private boolean mLoopEnabled;
    private boolean mReverse;

    public AutoScrollRecyclerView(Context context) {
        this(context, null);
    }

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mInterpolator = new UniformSpeedInterpolator();
    }

    @Override
    public void swapAdapter(Adapter adapter, boolean removeAndRecycleExistingViews) {
        mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
        super.swapAdapter(mViewPagerAdapter, removeAndRecycleExistingViews);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mViewPagerAdapter = ensureRecyclerViewPagerAdapter(adapter);
        super.setAdapter(mViewPagerAdapter);
    }

    /**
     * 开始滑动
     */
    public void startScroll() {
        startScroll(currentSpeed, false);
    }

    /**
     * 开始滑动
     *
     * @param speed   滑动距离（决定滑动速度）
     * @param reverse 是否反向滑动
     */
    public void startScroll(int speed, boolean reverse) {
        mReverse = reverse;
        currentSpeed = speed;
        notifyLayoutManager();
        startSmoothScroll();
    }

    private void startSmoothScroll() {
        int absSpeed = Math.abs(currentSpeed);
        int d = mReverse ? -absSpeed : absSpeed;
        smoothScrollBy(d, d, mInterpolator);
    }

    private void notifyLayoutManager() {
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof LinearLayoutManager) {
            ((LinearLayoutManager) layoutManager).setReverseLayout(mReverse);
        } else {
            ((StaggeredGridLayoutManager) layoutManager).setReverseLayout(mReverse);
        }
    }

    /**
     * 设置是否无限循环显示列表
     */
    public void setLoopEnabled(boolean loopEnabled) {
        this.mLoopEnabled = loopEnabled;
    }

    public boolean isLoopEnabled() {
        return mLoopEnabled;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        boolean vertical;
        if (dx == 0) {//垂直滚动
            mSpeedDy += dy;
            vertical = true;
        } else {//水平滚动
            mSpeedDx += dx;
            vertical = false;
        }

        if (vertical) {
            if (Math.abs(mSpeedDy) >= Math.abs(currentSpeed)) {
                mSpeedDy = 0;
                startSmoothScroll();
            }
        } else {
            if (Math.abs(mSpeedDx) >= Math.abs(currentSpeed)) {
                mSpeedDx = 0;
                startSmoothScroll();
            }
        }
    }

    @NonNull
    @SuppressWarnings("unchecked")
    private RecyclerViewPagerAdapter ensureRecyclerViewPagerAdapter(Adapter adapter) {
        return new RecyclerViewPagerAdapter(this, adapter);
    }

    /**
     * 自定义估值器
     * 使列表匀速滑动
     */
    private static class UniformSpeedInterpolator implements Interpolator {
        @Override
        public float getInterpolation(float input) {
            return input;
        }
    }

    /**
     * 自定义Adapter容器，使列表可以无限循环显示
     */
    private static class RecyclerViewPagerAdapter<VH extends RecyclerView.ViewHolder>
            extends RecyclerView.Adapter<VH> {

        private AutoScrollRecyclerView mRecyclerView;
        RecyclerView.Adapter<VH> mAdapter;


        RecyclerViewPagerAdapter(AutoScrollRecyclerView recyclerView, RecyclerView.Adapter<VH> adapter) {
            mAdapter = adapter;
            mRecyclerView = recyclerView;
            setHasStableIds(mAdapter.hasStableIds());
        }

        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
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
        public void onBindViewHolder(VH holder, int position) {
            mAdapter.onBindViewHolder(holder, generatePosition(position));
        }

        @Override
        public void setHasStableIds(boolean hasStableIds) {
            super.setHasStableIds(hasStableIds);
            mAdapter.setHasStableIds(hasStableIds);
        }

        @Override
        public int getItemCount() {
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

        private int generatePosition(int position) {
            return getLoopEnable() ? getActualPosition(position) : position;
        }

        private int getActualPosition(int position) {
            int itemCount = mAdapter.getItemCount();
            return position >= itemCount ? position % itemCount : position;

        }

        private boolean getLoopEnable() {
            return mRecyclerView.mLoopEnabled;
        }
    }

}
