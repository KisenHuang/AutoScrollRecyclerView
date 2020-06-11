package com.kisen.layoutmanager.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.animation.Interpolator;

/**
 * Created by kisen on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class LayoutManagerHelper {

    private static final int SPEED = 100;
    private final Context mContext;
    private Adapter mAdapter;
    RecyclerView mRecyclerView;
    private NestingRecyclerViewAdapter mNestingAdapter;
    private ASOnScrollListener mOnScrollListener;
    private ASOnItemTouchListener mOnItemTouchListener;
    private IScrollInfo mIScrollInfo;
    private int mCurrentSpeed = SPEED;
    private UniformSpeedInterpolator mInterpolator;

    private boolean mLoopEnabled;
    /**
     * 是否开启自动滑动
     */
    private boolean mIsOpenAuto;
    /**
     * 用户是否点击屏幕
     */
    private boolean mPointTouch;
    /**
     * 用户是否可以手动滑动屏幕
     */
    private boolean mCanTouch = true;
    /**
     * 是否准备好数据
     */
    private boolean mReady;
    /**
     * 是否初始化完成
     */
    private boolean mInflate;

    public LayoutManagerHelper(Context context) {
        mContext = context;
        mOnScrollListener = new ASOnScrollListener(this);
        mOnItemTouchListener = new ASOnItemTouchListener(this);
        mReady = false;
        mInterpolator = new UniformSpeedInterpolator();
    }

    public void onAdapterChanged(Adapter newAdapter) {
        if (mReady && newAdapter == mNestingAdapter)
            return;
        mReady = true;
        mAdapter = newAdapter;
        mNestingAdapter = new NestingRecyclerViewAdapter(this, newAdapter);
        if (mRecyclerView != null && mRecyclerView.getAdapter() != mNestingAdapter)
            mRecyclerView.setAdapter(mNestingAdapter);
    }

    public void onAttachedToWindow(RecyclerView view, IScrollInfo scrollInfo) {
        mInflate = true;
        mRecyclerView = view;
        mIScrollInfo = scrollInfo;
        mRecyclerView.addOnScrollListener(mOnScrollListener);
        mRecyclerView.addOnItemTouchListener(mOnItemTouchListener);
        if (mReady && mRecyclerView.getAdapter() != mNestingAdapter) {
            mRecyclerView.setAdapter(mNestingAdapter);
        }
        startScroll();
    }

    public Context getContext() {
        return mContext;
    }

    /**
     * 开始滑动
     */
    public void openAutoScroll() {
        openAutoScroll(mCurrentSpeed);
    }

    /**
     * 开始滑动
     *
     * @param speed 滑动距离（决定滑动速度）
     */
    public void openAutoScroll(int speed) {
        mCurrentSpeed = speed;
        mIsOpenAuto = true;
        startScroll();
    }

    public boolean isLoopEnabled() {
        return mLoopEnabled;
    }

    public void setLoopEnabled(boolean mLoopEnabled) {
        this.mLoopEnabled = mLoopEnabled;
        mNestingAdapter.notifyDataSetChanged();
        startScroll();
    }

    public boolean isOpenAuto() {
        return mIsOpenAuto;
    }

    public void setOpenAuto(boolean mIsOpenAuto) {
        this.mIsOpenAuto = mIsOpenAuto;
    }

    public void setReverseLayout(boolean reverse) {
        mIScrollInfo.setReverseLayout(reverse);
    }

    public boolean getReverseLayout() {
        return mIScrollInfo.getReverseLayout();
    }

    boolean isPointTouch() {
        return mPointTouch;
    }

    void setPointTouch(boolean mPointTouch) {
        this.mPointTouch = mPointTouch;
    }

    public boolean isCanTouch() {
        return mCanTouch;
    }

    public void setCanTouch(boolean mCanTouch) {
        this.mCanTouch = mCanTouch;
        if (mRecyclerView != null)
            mRecyclerView.setEnabled(mCanTouch);
    }

    public int getCurrentSpeed() {
        return mCurrentSpeed;
    }

    /**
     * 启动滚动
     */
    private void startScroll() {
        if (!mIsOpenAuto)
            return;
        if (mRecyclerView != null && mRecyclerView.getScrollState() == RecyclerView.SCROLL_STATE_SETTLING)
            return;
        if (mInflate && mReady) {
            mOnScrollListener.reset();
            smoothScroll();
        }
    }

    void smoothScroll() {
        int absSpeed = Math.abs(mCurrentSpeed);
        if (mIScrollInfo != null && mRecyclerView != null) {
            int d = mIScrollInfo.getReverseLayout() ? -absSpeed : absSpeed;
            mRecyclerView.smoothScrollBy(d, d, mInterpolator);
        }
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
}
