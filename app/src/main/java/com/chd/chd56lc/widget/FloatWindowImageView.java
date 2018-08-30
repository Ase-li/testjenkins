package com.chd.chd56lc.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.UIUtils;

import java.util.logging.Handler;

public class FloatWindowImageView extends AppCompatImageView implements GestureDetector.OnGestureListener {
    private float lastX;
    private float lastY;
    private float screenWidth;
    private float screenHeight;
    private RelativeLayout.LayoutParams layoutParams;

    private GestureDetector gestureDetector;

    private OnSingleClick onSingleClick;

    public void setOnSingleClick(OnSingleClick onSingleClick) {
        this.onSingleClick = onSingleClick;
    }

    public FloatWindowImageView(Context context) {
        super(context);
        init();
    }

    private void init() {
        screenHeight = UIUtils.getScreenHeight();
        screenWidth = UIUtils.getScreenWidth();
        layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        gestureDetector = new GestureDetector(getContext(), this);
    }

    public FloatWindowImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public FloatWindowImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * Notified when a tap occurs with the down {@link MotionEvent}
     * that triggered it. This will be triggered immediately for
     * every down event. All other events should be preceded by this.
     *
     * @param e The down motion event.
     */
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d("GestureDetector_Down", "e1:" + e.getRawX() + "--" + e.getRawY());

        lastX = e.getRawX();
        lastY = e.getRawY();
        return true;
    }

    /**
     * The user has performed a down {@link MotionEvent} and not performed
     * a move or up yet. This event is commonly used to provide visual
     * feedback to the user to let them know that their action has been
     * recognized i.e. highlight an element.
     *
     * @param e The down motion event
     */
    @Override
    public void onShowPress(MotionEvent e) {

    }

    /**
     * Notified when a tap occurs with the up {@link MotionEvent}
     * that triggered it.
     *
     * @param e The up motion event that completed the first tap
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (onSingleClick != null)
            onSingleClick.onSingleClick();
        return false;
    }

    /**
     * Notified when a scroll occurs with the initial on down {@link MotionEvent} and the
     * current move {@link MotionEvent}. The distance in x and y is also supplied for
     * convenience.
     *
     * @param e1        The first down motion event that started the scrolling.
     * @param e2        The move motion event that triggered the current onScroll.
     * @param distanceX The distance along the X axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @param distanceY The distance along the Y axis that has been scrolled since the last
     *                  call to onScroll. This is NOT the distance between {@code e1}
     *                  and {@code e2}.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float moveX = e2.getRawX() - lastX;
        float moveY = e2.getRawY() - lastY;
        float left = getLeft() + moveX;
        float top = getTop() + moveY;
        float right = getRight() + moveX;
        float bottom = getBottom() + moveY;
        if (moveX > 20 || moveY > 20)
            setClickable(false);
        if (left < 0) {
            left = 0;
            right = left + getMeasuredWidth();
        }
        if (right > screenWidth) {
            right = screenWidth;
            left = right - getMeasuredWidth();
        }
        if (top < 0) {
            top = 0;
            bottom = top + getMeasuredHeight();
        }
        if (bottom > screenHeight) {
            bottom = screenHeight;
            top = bottom - getMeasuredHeight();
        }
        layout((int) left, (int) top, (int) right, (int) bottom);
        lastX = (int) e2.getRawX();
        lastY = (int) e2.getRawY();
        //防止父布局重绘时，回到原点
        layoutParams.leftMargin = getLeft();
        layoutParams.topMargin = getTop();
        setLayoutParams(layoutParams);
        return true;
    }

    /**
     * Notified when a long press occurs with the initial on down {@link MotionEvent}
     * that trigged it.
     *
     * @param e The initial on down motion event that started the longpress.
     */
    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
//            Log.d("GestureDetector_Fling", "e1:" + e1.getRawX() + "--" + e1.getRawY() + ";e2:" + e2.getRawX() + "---" + e2.getRawY());
            if (event.getRawX() > screenWidth / 2) {
                layout((int) (screenWidth - getMeasuredWidth()), getTop(), (int) screenWidth, getTop() + getMeasuredHeight());
            } else {
                layout(0, getTop(), getMeasuredWidth(), getTop() + getMeasuredHeight());
            }
            //防止父布局重绘时，回到原点
            layoutParams.leftMargin = getLeft();
            layoutParams.topMargin = getTop();
            setLayoutParams(layoutParams);
        }
        return gestureDetector.onTouchEvent(event);
    }

    /**
     * Notified of a fling event when it occurs with the initial on down {@link MotionEvent}
     * and the matching up {@link MotionEvent}. The calculated velocity is supplied along
     * the x and y axis in pixels per second.
     *
     * @param e1        The first down motion event that started the fling.
     * @param e2        The move motion event that triggered the current onFling.
     * @param velocityX The velocity of this fling measured in pixels per second
     *                  along the x axis.
     * @param velocityY The velocity of this fling measured in pixels per second
     *                  along the y axis.
     * @return true if the event is consumed, else false
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        return false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(UIUtils.dip2px(40), UIUtils.dip2px(40));
    }

    public interface OnSingleClick {
        void onSingleClick();
    }
}
