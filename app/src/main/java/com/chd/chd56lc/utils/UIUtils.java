package com.chd.chd56lc.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.chd.chd56lc.common.BaseApplication;

/**
 * 资源工具类
 * Created by lyl.
 */
public class UIUtils {

    // TODO BaseApplication相关方法

    private static Context getContext() {
        return BaseApplication.getInstance();
    }

    // TODO 加载资源文件

    /**
     * 加载字符串
     */
    public static String getString(int id) {
        return getContext().getResources().getString(id);
    }

    /**
     * 加载整型
     */
    public static int getInteger(int id) {
        return getContext().getResources().getInteger(id);
    }

    /**
     * 加载字符串数组
     */
    public static String[] getStringArray(int id) {
        return getContext().getResources().getStringArray(id);
    }

    /**
     * 加载图片
     */
    public static Drawable getDrawable(int id) {
        return getContext().getResources().getDrawable(id);
    }

    /**
     * 加载颜色
     */
    public static int getColor(int id) {
        return getContext().getResources().getColor(id);
    }

    /**
     * 加载颜色的状态选择器
     */
    public static ColorStateList getColorStateList(int id) {
        return getContext().getResources().getColorStateList(id);
    }

    /**
     * 加载尺寸
     */
    public static int getDimen(int id) {
        return getContext().getResources().getDimensionPixelSize(id);// 获取对应像素值
    }

    //TODO dip转px, px转dp dp=px / 设备密度

    /**
     * 输入dp 输出px
     */
    public static int dip2px(float dip) {
        float density = getContext().getResources().getDisplayMetrics().density;// 设备密度
        int px = (int) (dip * density + 0.5f);// 3.1->3, 3.9+0.5->4.4->4
        return px;
    }

    /**
     * 输入px 输出dp
     */
    public static float px2dip(int px) {
        float density = getContext().getResources().getDisplayMetrics().density;// 设备密度
        return px / density;
    }

    /**
     * 通过布局文件加载布局对象
     */
    public static View inflate(int layoutId) {
        return View.inflate(getContext(), layoutId, null);
    }

    //TODO 线程操作


    /**
     * 查找Button和ImageButton并设置单击监听器
     *
     * @param view
     */
    public static void findButtonSetOnClickListener(View view, OnClickListener listener) {
        // 遍历view的所有子view
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            for (int i = 0; i < viewGroup.getChildCount(); i++) {
                View child = viewGroup.getChildAt(i);
                if (child instanceof Button || child instanceof ImageButton) {
                    child.setOnClickListener(listener);
                } else if (child instanceof ViewGroup) {
                    findButtonSetOnClickListener(child, listener);
                }
            }
        }
    }

    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context
                .WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context
                .WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        return screenHeight;
    }


    /**
     * 获取屏幕宽
     */
    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        return screenWidth;
    }

    /**
     * 获取屏幕高
     */
    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context
                .WINDOW_SERVICE);
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        return screenHeight;
    }

    /**
     * 打印Cursor里面所有的记录
     *
     * @param cursor
     */
    public static void printCursor(Cursor cursor) {
        if (cursor == null) {
            return;
        }

        Logger.i(UIUtils.class.toString(), "共有" + cursor.getCount() + "条记录");
        while (cursor.moveToNext()) {
            Logger.i(UIUtils.class.toString(), "---------------");
            // 遍历所有的列
            for (int i = 0; i < cursor.getColumnCount(); i++) {
                String columnName = cursor.getColumnName(i);
                String value = cursor.getString(i);
                Logger.i(UIUtils.class.toString(), columnName + " = " + value);
            }
        }
    }

    /**
     * 获取屏幕分辨率
     *
     * @return
     */
    public static int[] getScreenDispaly() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context
                .WINDOW_SERVICE);
        int width = windowManager.getDefaultDisplay().getWidth();// 手机屏幕的宽度
        int height = windowManager.getDefaultDisplay().getHeight();// 手机屏幕的高度
        int result[] = {width, height};
        return result;
    }

    /**
     * 字体大小适配
     **/
    public static void adjustTvTextSize(TextView tv, int maxWidth, String text) {
        int avaiWidth = maxWidth - tv.getPaddingLeft() - tv.getPaddingRight() - 10;

        if (avaiWidth <= 0) {
            return;
        }

        TextPaint textPaintClone = new TextPaint(tv.getPaint());
        float trySize = textPaintClone.getTextSize();

        while (textPaintClone.measureText(text) > avaiWidth) {
            trySize--;
            textPaintClone.setTextSize(trySize);
        }

        tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
    }

    @TargetApi(21)
    public static int getStatusHeight() {
        /**
         * 获取状态栏高度——方法1
         * */
        int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
        int resourceId = BaseApplication.getInstance().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight1 = BaseApplication.getInstance().getResources().getDimensionPixelSize(resourceId);
        }

        Log.e("WangJ", "状态栏-方法1:" + statusBarHeight1);
        return statusBarHeight1;
    }

}
