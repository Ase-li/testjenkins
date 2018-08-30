package com.chd.chd56lc.ui.activity.web;

import android.content.Intent;
import android.webkit.WebView;

import com.chd.chd56lc.manager.WebViewClientManager;
import com.chd.chd56lc.utils.StringUtils;
import com.umeng.socialize.UMShareAPI;

public class CommonWebActivity extends BaseWebviewActivity {
    public static final String URL = "url";
    public static final String TITLE = "title";
    private String url;
    private String title;


    @Override
    public void initData() {
        url = getIntent().getStringExtra(URL);
        title = getIntent().getStringExtra(TITLE);
        if (StringUtils.isEmpty(url)) return;
        init_WebView(url);
    }


    private void init_WebView(String url) {
        if (url.contains("myservice"))
            webview.getSettings().setDomStorageEnabled(true);
        else if (url.contains("customerService"))
            webview.getSettings().setDomStorageEnabled(true);
        webview.setWebViewClient(new WebViewClientManager(this) {
            // 当点击链接时,希望覆盖而不是打开新窗口
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (super.shouldOverrideUrlLoading(view, url)) {
                    return true;
                }
                return false; // 返回true,代表事件已处理,事件流到此终止
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });
        synCookies();
        webview.loadUrl(url);
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        clearCookies();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
