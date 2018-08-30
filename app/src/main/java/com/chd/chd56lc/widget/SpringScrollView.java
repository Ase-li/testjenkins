package com.chd.chd56lc.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ScrollView;

import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.UIUtils;

/**
 * Created by li on 2018/3/21.
 */

public class SpringScrollView extends ScrollView {

    // 上下文
    private Context context;

    // ScrollView子布局
    private ViewGroup contentView;

    // 手势按下时，是否可以下拉/上拉标志
    private boolean isCanPullDown = false, isCanPullUp = true;

    // 保存ScrollView子布局的初始位置信息
    private int leftPosition, topPosition, rightPosition, bottomPosition;

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public SpringScrollView(Context context) {
        super(context);
        this.context = context;
    }

    public SpringScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    /**
     * 获取ScrollView的子布局
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0) {
            contentView = (ViewGroup) getChildAt(0);
        }
    }

    /**
     * 获取初始子布局的位置信息
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (contentView != null) {
            leftPosition = contentView.getLeft();
            rightPosition = contentView.getRight();
            topPosition = contentView.getTop();
            bottomPosition = contentView.getBottom();
        }
        contentView.layout(leftPosition, topPosition - dip2px(20), rightPosition, bottomPosition - dip2px(20));
    }

    /**
     * 判断是否可以下拉
     */
    private boolean isScrollViewTop() {
        if (getScrollY() == 0 && isCanPullDown)
            return true;
        return false;
    }

    /**
     * 判断是否可以上拉
     */
    private boolean isScrollViewBottom() {
        Log.d("springScrollbottom", contentView.getMeasuredHeight() + "--" + getScrollY() + "--" + getHeight());

        if (contentView.getMeasuredHeight() <= getScrollY() + getHeight() && isCanPullUp)
            return true;
        return false;
    }

    private float startY;

    /**
     * 触摸事件处理
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = ev.getY();
                if (contentView != null) {
                    leftPosition = contentView.getLeft();
                    rightPosition = contentView.getRight();
                    topPosition = contentView.getTop();
                    bottomPosition = contentView.getBottom();
                    Log.d("springScroll-down", leftPosition + "--" + topPosition + "--" + rightPosition + "--" + bottomPosition);

                }
//                isCanPullDown = isScrollViewTop();
//                isCanPullUp = isScrollViewBottom();
                break;

            case MotionEvent.ACTION_UP:
                // 手势放开时，采用动画形式返回原位置
                Log.d("springScroll", "getY:" + ev.getY() + "--startY:" + startY + "==" + (ev.getY() - startY));
                ObjectAnimator animator = ObjectAnimator.ofFloat(contentView, "translationY", 0);
                animator.setDuration(1000);
                animator.setInterpolator(new AccelerateInterpolator());
                animator.start();
                // 设置布局到正常位置
                contentView.layout(leftPosition, topPosition, rightPosition, bottomPosition);
                Log.d("springScroll-up", leftPosition + "--" + topPosition + "--" + rightPosition + "--" + bottomPosition);
                if (ev.getY() - startY > 400 && isScrollViewTop()) {
                    callBack.pullDown();
                    isCanPullDown = false;
                    isCanPullUp = true;
                } else if (ev.getY() - startY < -400 && isScrollViewBottom()) {
                    callBack.pullUp();
                    isCanPullUp = false;
                    isCanPullDown = true;
                }
                break;

            case MotionEvent.ACTION_MOVE:
                // 在最上部或最底部时，拉动移动布局
                // 1、下拉 2、上拉 3、布局内容比ScrollView小，则既可以上拉，也可以下拉
                if ((isScrollViewTop() ) || (isScrollViewBottom())) {
//                if ((isScrollViewTop() && ev.getY() > startY) || (isScrollViewBottom() && ev.getY() < startY)) {
                    int deltaY = (int) (ev.getY() - startY);
                    contentView.layout(leftPosition, topPosition + deltaY / 3, rightPosition, bottomPosition + deltaY / 3);
//                    contentView.scrollBy(0,deltaY/3);
                }
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Logger.d("springScrollView",l+"__");
    }

    public boolean isCanPullDown() {
        return isCanPullDown;
    }

    public void setCanPullDown(boolean canPullDown) {
        isCanPullDown = canPullDown;
    }

    public boolean isCanPullUp() {
        return isCanPullUp;
    }

    public void setCanPullUp(boolean canPullUp) {
        isCanPullUp = canPullUp;
    }

    public interface CallBack {
        /**
         * 下拉显示第二张
         */
        void pullUp();

        /**
         * 上拉显示第一张
         */
        void pullDown();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(UIUtils.getScreenWidth(getContext()), UIUtils.getScreenHeight(getContext()) - 40 - dip2px(48 + 45));
    }

    /**
     * 输入dp 输出px
     */
    public int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;// 设备密度
        int px = (int) (dip * density + 0.5f);// 3.1->3, 3.9+0.5->4.4->4
        return px;
    }
}
