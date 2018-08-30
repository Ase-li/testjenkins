package com.chd.chd56lc.manager;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.URLSpan;
import android.view.View;

import com.chd.chd56lc.ui.activity.web.CommonWebActivity;

/**
 * Created by li on 2018/3/20.
 */

public class CustomUrlSpan extends URLSpan {
    private Context context;
    private String title;
    private int color;

    /**
     * @param url     连接
     * @param context
     * @param title   标题不要书名号
     */
    public CustomUrlSpan(String url, Context context, String title, int color) {
        super(url);
        this.context = context;
        this.color = color;
        if (title.contains("《") || title.contains("》"))
            this.title = title.replace("《", "").replace("》", "");
        else
            this.title = title;
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setUnderlineText(false);
        ds.setColor(color);
    }

    @Override
    public void onClick(View widget) {
        Intent loanIntent = new Intent(context, CommonWebActivity.class);
        loanIntent.putExtra(CommonWebActivity.URL, getURL());
        loanIntent.putExtra(CommonWebActivity.TITLE, title);
        context.startActivity(loanIntent);

    }
}
