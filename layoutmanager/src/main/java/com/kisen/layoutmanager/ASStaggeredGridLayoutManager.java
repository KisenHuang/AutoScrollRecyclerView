package com.kisen.layoutmanager;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.View;

import com.kisen.layoutmanager.widget.IScrollInfo;
import com.kisen.layoutmanager.widget.LayoutManagerHelper;

/**
 * Created by huangwy on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class ASStaggeredGridLayoutManager extends StaggeredGridLayoutManager implements IScrollInfo {

    private LayoutManagerHelper mLayoutManagerHelper;

    public ASStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public ASStaggeredGridLayoutManager(int spanCount, int orientation) {
        super(spanCount, orientation);
        init();
    }

    private void init() {
        mLayoutManagerHelper = new LayoutManagerHelper();
    }

    public LayoutManagerHelper getLayoutManagerHelper() {
        return mLayoutManagerHelper;
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        mLayoutManagerHelper.onAttachedToWindow(view, this);
    }

    @Override
    public void onAdapterChanged(RecyclerView.Adapter oldAdapter, RecyclerView.Adapter newAdapter) {
        mLayoutManagerHelper.onAdapterChanged(newAdapter);
    }

    @Override
    public View onInterceptFocusSearch(View focused, int direction) {
        return super.onInterceptFocusSearch(focused, direction);
    }

    @Override
    public boolean getRevert() {
        return getReverseLayout();
    }
}
