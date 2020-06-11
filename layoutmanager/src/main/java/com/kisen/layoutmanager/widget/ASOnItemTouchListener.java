package com.kisen.layoutmanager.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * 事件处理类
 * Created by kisen on 2017/12/19.
 * email: kisenhuang@163.com.
 */

public class ASOnItemTouchListener extends RecyclerView.SimpleOnItemTouchListener {

    private LayoutManagerHelper mHelper;
    private GestureDetector mGestureDetector;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mHelper.setPointTouch(false);
            mHelper.smoothScroll();
        }
    };

    ASOnItemTouchListener(LayoutManagerHelper helper) {
        mHelper = helper;
        mGestureDetector = new ASGestureDetector(helper.getContext());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        boolean canTouch = mHelper.isCanTouch();
        int action = e.getAction();
        Log.e("TouchEvent", "intercept :" + action + "   canTouch   " + canTouch);
        if (action == MotionEvent.ACTION_DOWN) {
            mHandler.removeCallbacks(mRunnable);
            mHelper.setPointTouch(true);
        }
        if (canTouch) {
            if (action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_UP) {
                mHandler.postDelayed(mRunnable, 100);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        Log.e("TouchEvent", "touch :" + e.getAction());
//        if (mHelper.isCanTouch()) {
//            switch (e.getAction()) {
//                case MotionEvent.ACTION_UP:
//                case MotionEvent.ACTION_CANCEL:
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (mHelper.isOpenAuto()) {
//                                mHelper.setPointTouch(false);
//                                mHelper.smoothScroll();
//                            }
//                        }
//                    }, 100);
//                    break;
//                case MotionEvent.ACTION_MOVE:
////                    rv.getScrollState()
//                    break;
//            }
//        }
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.onRequestDisallowInterceptTouchEvent(disallowIntercept);
        mHelper.mRecyclerView.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private static class ASGestureDetector extends GestureDetector {

        public ASGestureDetector(Context context) {
            super(context, new OnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {

                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return false;
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    return false;
                }

                @Override
                public void onLongPress(MotionEvent e) {

                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return false;
                }
            });
        }

    }
}
