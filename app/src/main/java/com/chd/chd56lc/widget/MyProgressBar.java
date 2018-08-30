package com.chd.chd56lc.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.chd.chd56lc.R;


/**
 * Created by li on 2018/3/21.
 */

public class MyProgressBar extends View {
    private Paint bottomPaint;
    private Paint topPaint;
    private float percent;
    int width;
    int height;

    public MyProgressBar(Context context) {
        super(context);
        initView(context, null);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void initView(Context context, AttributeSet attrs) {
        bottomPaint = new Paint();
        topPaint = new Paint();
        topPaint.setAntiAlias(true);
        topPaint.setStyle(Paint.Style.FILL);
        topPaint.setStrokeCap(Paint.Cap.ROUND);
        bottomPaint.setAntiAlias(true);
        bottomPaint.setStyle(Paint.Style.FILL);
        bottomPaint.setStrokeCap(Paint.Cap.ROUND);
        if (attrs != null) {
            TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
            bottomPaint.setColor(ta.getColor(R.styleable.MyProgressBar_bottomLineColor, Color.GRAY));
            bottomPaint.setStrokeWidth(ta.getDimensionPixelSize(R.styleable.MyProgressBar_bottomLineSize, 20));
            topPaint.setColor(ta.getColor(R.styleable.MyProgressBar_topLineColor, Color.GRAY));
            topPaint.setStrokeWidth(ta.getDimensionPixelSize(R.styleable.MyProgressBar_topLineSize, 20));
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();
        canvas.drawLine(30, height / 2, width - 30, height / 2, bottomPaint);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_cpxx_huojian);
        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();
        if (percent != 0) {
            canvas.drawLine(30, height / 2, (width - 30) * percent, height / 2, topPaint);
            canvas.drawBitmap(bitmap, (width - 30) * percent - bitmapWidth * 2 / 3, (height - bitmapHeight) / 2, new Paint());
        } else {
            canvas.drawLine(30, height / 2, 30, height / 2, topPaint);
            canvas.drawBitmap(bitmap, 30 - bitmapWidth * 2 / 3, (height - bitmapHeight) / 2, new Paint());
        }

    }

    public void setTopPaintWidth(float percent) {
        this.percent = percent;
        invalidate();
    }

}
