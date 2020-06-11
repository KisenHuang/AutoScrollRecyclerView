package com.kisen.layoutmanager;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.kisen.layoutmanager.widget.IScrollInfo;
import com.kisen.layoutmanager.widget.LayoutManagerHelper;

/**
 * Created by kisen on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class ASGridLayoutManager extends GridLayoutManager implements IScrollInfo{

    private LayoutManagerHelper mLayoutManagerHelper;

    public ASGridLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
        init(context);
    }

    public ASGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
        super(context, spanCount, orientation, reverseLayout);
        init(context);
    }

    public ASGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        mLayoutManagerHelper = new LayoutManagerHelper(context);
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
}
