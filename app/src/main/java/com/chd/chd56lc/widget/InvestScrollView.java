package com.chd.chd56lc.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by li on 2018/3/21.
 */

public class InvestScrollView extends ScrollView {

    public InvestScrollView(Context context) {
        super(context);
    }

    public InvestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InvestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                getParent().getParent().requestDisallowInterceptTouchEvent(true);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                if (getScaleY() <= 0) {
                    getParent().getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
