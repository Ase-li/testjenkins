package com.chd.chd56lc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;


/**
 * Created by Aesop on 2018/14/22.
 */

public class NewTwoSideTextView extends RelativeLayout implements View.OnTouchListener {
    private Drawable mRightArrow;
    private OnTextClickListener mListener;

    private TextView mTvDesc;
    private TextView mTvValue;
    private ImageView mIvRight;

    public NewTwoSideTextView(Context context) {
        super(context);
        this.init(context, null);
    }

    public NewTwoSideTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs);
    }

    public NewTwoSideTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.item_new_two_side_text, this);
        mTvDesc = (TextView) this.findViewById(R.id.tv_item_two_side_desc);
        mTvValue = (TextView) this.findViewById(R.id.tv_item_two_side_value);
        mIvRight = (ImageView) this.findViewById(R.id.iv_item_two_side_right_icon);
        mTvDesc.setOnTouchListener(this);
        mTvValue.setOnTouchListener(this);

        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.NewTwoSideTextView);
            String desc = ta.getString(R.styleable.NewTwoSideTextView_descText1);
            mTvDesc.setText(desc);

            String value = ta.getString(R.styleable.NewTwoSideTextView_valueText1);
            mTvValue.setText(value);

            String hint = ta.getString(R.styleable.NewTwoSideTextView_valueHintText1);
            if (hint != null) {
                mTvValue.setHint(hint);
            }

            Drawable background = ta.getDrawable(R.styleable.NewTwoSideTextView_backgroundDrawable1);
            if (background == null) {
                background = new ColorDrawable(context.getResources().getColor(R.color.transparent));
            }
            this.setBackgroundDrawable(background);

            int descLeftIcon = ta.getResourceId(R.styleable.NewTwoSideTextView_descLeftIcon1, 0);
            int descRightIcon = ta.getResourceId(R.styleable.NewTwoSideTextView_descRightIcon1, 0);
            int valueRightIcon = ta.getResourceId(R.styleable.NewTwoSideTextView_valueRightIcon1, 0);
            int valueLeftIcon = ta.getResourceId(R.styleable.NewTwoSideTextView_valueLeftIcon1, 0);
            int contentGravity = ta.getResourceId(R.styleable.NewTwoSideTextView_contentGravity, Gravity.LEFT);
            this.setGravity(contentGravity);

            //前部分说明文本的左右图标
            Drawable leftIcon = this.getDrawable(descLeftIcon);
            Drawable rightIcon = this.getDrawable(descRightIcon);
            mTvDesc.setCompoundDrawables(leftIcon, null, rightIcon, null);

            //后部分内容文本的左右图标
            leftIcon = this.getDrawable(valueLeftIcon);
            rightIcon = this.getDrawable(valueRightIcon);
            mTvValue.setCompoundDrawables(leftIcon, null, rightIcon, null);

            //说明文本的图标间距
            //默认padding为5dp
            float defaultPadding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, context.getResources().getDisplayMetrics());
            float drawPadding = ta.getDimension(R.styleable.NewTwoSideTextView_descDrawablePadding1, defaultPadding);
            mTvDesc.setCompoundDrawablePadding((int) drawPadding);

            //  setTextSize
            float decTextSize = ta.getDimensionPixelSize(R.styleable.NewTwoSideTextView_setDescTextSize1, 28);
            float valueTextSize = ta.getDimensionPixelSize(R.styleable.NewTwoSideTextView_setValueTextSize1, 28);
            if (decTextSize != 0) {
                mTvDesc.setTextSize(TypedValue.COMPLEX_UNIT_PX, decTextSize);
            }
            if (valueTextSize != 0) {
                mTvValue.setTextSize(TypedValue.COMPLEX_UNIT_PX, valueTextSize);
            }

            //setValueMarginRight
            int valueTextMargin = ta.getDimensionPixelSize(R.styleable.NewTwoSideTextView_setValueTextMarginRight, 0);
            if (valueTextMargin != 0) {
                ViewGroup.MarginLayoutParams layoutParams = (MarginLayoutParams) mTvValue.getLayoutParams();
                layoutParams.setMarginEnd(valueTextMargin);
            }
            //  setColor
            int decTextColor = ta.getColor(R.styleable.NewTwoSideTextView_setDescTextColor1, Integer.MIN_VALUE);
            int valueTextColor = ta.getColor(R.styleable.NewTwoSideTextView_setValueTextColor1, Integer.MIN_VALUE);
            int hintTextColor = ta.getColor(R.styleable.NewTwoSideTextView_valueHintTextColor1, Integer.MIN_VALUE);
            if (decTextColor != Integer.MIN_VALUE) {
                mTvDesc.setTextColor(decTextColor);
            }
            if (valueTextColor != Integer.MIN_VALUE) {
                mTvValue.setTextColor(valueTextColor);
            }
            if (hintTextColor != Integer.MIN_VALUE) {
                mTvValue.setHintTextColor(hintTextColor);
            }

            //内容文本的图标间距
            drawPadding = ta.getDimension(R.styleable.NewTwoSideTextView_valueDrawablePadding1, defaultPadding);
            mTvValue.setCompoundDrawablePadding((int) drawPadding);

            //内容文本的对齐方式
            int gravity = ta.getInteger(R.styleable.NewTwoSideTextView_valueTextGravity1, 0);
            if (gravity != 0) {
                mTvValue.setGravity(gravity);
            }

            int resourceId = ta.getResourceId(R.styleable.NewTwoSideTextView_rightIcon, 0);
            mIvRight.setBackgroundResource(resourceId);

            ta.recycle();
        }
    }

    public Drawable getDrawable(@DrawableRes int drawRes) {
        if (drawRes == 0) {
            return null;
        } else {
            Drawable drawable = this.getContext().getResources().getDrawable(drawRes);
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
            }
            return drawable;
        }
    }

    public void setOnTextClickListener(OnTextClickListener listener) {
        mListener = listener;
    }

    public TextView getDescText() {
        return mTvDesc;
    }

    public TextView getValueText() {
        return mTvValue;
    }

    public void setValueText(String text) {
        if (text == null) {
            text = "";
        }
        mTvValue.setText(text);
    }

    public void setValueText(@StringRes int strRes) {
        mTvValue.setText(strRes);
    }

    public void setDescText(String text) {
        mTvDesc.setText(text);
    }

    public void setDescText(@StringRes int strRes) {
        mTvDesc.setText(strRes);
    }

    public void setTextColor(int textColor) {
        mTvDesc.setTextColor(textColor);
        mTvValue.setTextColor(textColor);
    }

    public void setValueTextColor(int textColor) {
        mTvValue.setTextColor(textColor);
    }

    public void setTextSize(float textSize) {
        mTvDesc.setTextSize(textSize);
        mTvValue.setTextSize(textSize);
    }

    public void setDescRightIcon(Drawable rightIcon) {
        mTvDesc.setCompoundDrawables(null, null, rightIcon, null);
    }

    public void setRightIcon(int rightIconId) {
        mIvRight.setBackgroundResource(rightIconId);
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        boolean isHandle = false;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (mListener != null) {
                switch (v.getId()) {
                    case R.id.tv_item_two_side_desc:
                        mListener.onDescTextClickListener(v);
                        isHandle = true;
                        break;
                    case R.id.tv_item_two_side_value:
                        mListener.onValueTextClickListener(v);
                        isHandle = true;
                        break;
                }
            }
        }
        return isHandle;
    }

    public interface OnTextClickListener {
        public void onDescTextClickListener(View view);

        public void onValueTextClickListener(View view);
    }

}
