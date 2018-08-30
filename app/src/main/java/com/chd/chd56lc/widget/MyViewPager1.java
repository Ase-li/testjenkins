package com.chd.chd56lc.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.chd.chd56lc.utils.UIUtils;

/**
 * Created by Aesop on 2017/5/25.
 */

public class MyViewPager1 extends ViewPager {
    public boolean isCanScroll = false;

    public MyViewPager1(Context context) {
        super(context);
    }

    public MyViewPager1(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onTouchEvent(ev);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(UIUtils.getScreenHeight() - UIUtils.getStatusHeight() - UIUtils.dip2px(50+39+45), MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
