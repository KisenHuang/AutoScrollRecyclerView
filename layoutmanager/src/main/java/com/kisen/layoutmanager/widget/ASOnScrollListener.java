package com.kisen.layoutmanager.widget;

import android.support.v7.widget.RecyclerView;
import android.util.Log;

/**
 * Created by huangwy on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class ASOnScrollListener extends RecyclerView.OnScrollListener {

    private LayoutManagerHelper mManagerHelper;
    private int mSpeedDx, mSpeedDy;

    public ASOnScrollListener(LayoutManagerHelper helper) {
        mManagerHelper = helper;
    }

    public void reset() {
        mSpeedDx = mSpeedDy = 0;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        if (mManagerHelper.isPointTouch()) {
            mSpeedDx = 0;
            mSpeedDy = 0;
            return;
        }
        boolean vertical;
        if (dx == 0) {//垂直滚动
            mSpeedDy += dy;
            vertical = true;
        } else {//水平滚动
            mSpeedDx += dx;
            vertical = false;
        }
        Log.e("onScroll", "dy:" + dy+" mSpeedDy:"+mSpeedDy);
        if (vertical) {
            if (Math.abs(mSpeedDy) >= Math.abs(mManagerHelper.getCurrentSpeed())) {
                mSpeedDy = 0;
                mManagerHelper.smoothScroll();
            }
        } else {
            if (Math.abs(mSpeedDx) >= Math.abs(mManagerHelper.getCurrentSpeed())) {
                mSpeedDx = 0;
                mManagerHelper.smoothScroll();
            }
        }
    }
}
