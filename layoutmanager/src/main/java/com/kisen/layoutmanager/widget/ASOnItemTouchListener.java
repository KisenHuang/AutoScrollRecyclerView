package com.kisen.layoutmanager.widget;

import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;

/**
 * Created by kisen on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class ASOnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private LayoutManagerHelper mHelper;

    public ASOnItemTouchListener(LayoutManagerHelper helper) {
        mHelper = helper;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mHelper.isCanTouch()) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mHelper.setPointTouch(true);
                    break;
                case MotionEvent.ACTION_CANCEL:
                case MotionEvent.ACTION_UP:
                    if (mHelper.isOpenAuto()) {
                        return true;
                    }
            }
            return true;
        } else return true;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        if (mHelper.isCanTouch()) {
            switch (e.getAction()) {
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    if (mHelper.isOpenAuto()) {
                        mHelper.setPointTouch(false);
                        mHelper.smoothScroll();
                    }
            }
        }
    }
}
