package com.chd.chd56lc.utils;

import android.text.ParcelableSpan;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.chd.chd56lc.manager.CustomUrlSpan;

public class TextViewSpanUtils {
    /**
     * 设置超链接
     *
     * @param textView
     * @param text     需要修改文字
     * @param url      跳转的链接
     * @param color    字体颜色
     */
    public static CustomUrlSpan setURLSpan(TextView textView, String text, String url, int color) {
        return new CustomUrlSpan(url, textView.getContext(), text, color);
    }

    /**
     * 设置超链接
     *
     * @param textView
     * @param text     需要修改文字
     * @param url      跳转的链接
     * @param color    字体颜色
     */
    public static void setURLSpanSigle(TextView textView, String text, String url, int color) {
        CustomUrlSpan customUrlSpan = new CustomUrlSpan(url, textView.getContext(), text, color);
        baseSpan(customUrlSpan, textView, text);

    }

    /**
     * 设置字体颜色
     *
     * @param textView
     * @param text     需要修改文字
     * @param color    字体颜色
     */
    public static void setForegroundColor(TextView textView, String text, int color) {
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(color);
        baseSpan(foregroundColorSpan, textView, text);
    }

    /**
     * 设置字体大小
     *
     * @param textView
     * @param text     需要修改文字
     * @param size     字体颜色
     */
    public static void setAbsoluteSizeSpan(TextView textView, String text, int size) {
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(size, true);
        baseSpan(absoluteSizeSpan, textView, text);
    }

    /**
     * @param textView
     * @param text     需要修改文字
     */
    public static void baseSpan(ParcelableSpan span, TextView textView, String text) {

        String content = textView.getText().toString();
        SpannableString s = new SpannableString(content);
        s.setSpan(span, content.indexOf(text), content.indexOf(text) + text.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(s);
    }

    /**
     * @param spans
     * @param textView
     * @param texts
     */
    public static void baseMultiSpans(ParcelableSpan[] spans, TextView textView, String[] texts) {

        String content = textView.getText().toString();
        SpannableString s = new SpannableString(content);
        for (int i = 0; i < spans.length; i++) {
            s.setSpan(spans[i], content.indexOf(texts[i]), content.indexOf(texts[i]) + texts[i].length(),
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(s);
    }


}
