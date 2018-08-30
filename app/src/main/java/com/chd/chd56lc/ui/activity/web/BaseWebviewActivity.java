package com.chd.chd56lc.ui.activity.web;

import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.manager.PreferenceManager;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.AppUtil;
import com.chd.chd56lc.utils.StringUtils;

import butterknife.BindView;

/**
 * webview载体基类，包含了webview的初始基本设置，webview后退，cookie设置
 * Created by li on 2018/3/13.
 */

public abstract class BaseWebviewActivity extends BaseActivity {
    @BindView(R.id.myProgressBar)
    public ProgressBar myProgressBar;
    public WebView webview;
    @BindView(R.id.fl_wv)
    public FrameLayout flWv;

    private String returnUrl;

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_base_web;
    }

    protected abstract void initData();

    public void initView() {
        webview = new WebView(getApplicationContext());
        flWv.addView(webview);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true); // 启用JS脚本
        settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        String userAgentString = settings.getUserAgentString();
        settings.setUserAgentString(userAgentString + " 56jf/" + AppUtil.versionName());
        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    myProgressBar.setVisibility(View.INVISIBLE);
                } else {
                    if (View.INVISIBLE == myProgressBar.getVisibility()) {
                        myProgressBar.setVisibility(View.VISIBLE);
                    }
                    myProgressBar.setProgress(newProgress);
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if (title.equals("找不到网页")) {
                    return;
                }
                if (!StringUtils.isSignEmpty(title) && title.length() < 8)
                    setTitle(title);
            }

        });
        // 点击后退按钮,让WebView后退一页(也可以覆写Activity的onKeyDown方法)
        webview.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
                        webview.goBack(); // 后退
                        return true; // 已处理
                    }
                }
                return false;
            }
        });
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!StringUtils.isEmpty(returnUrl) && UserManager.getInstance().isLogin()) {
            synCookies();
            webview.loadUrl(Url.HOSTHOME + returnUrl);
            //使用后置空
            returnUrl = null;
        }
    }

    /**
     * 客户端设置cookies
     */
    public void synCookies() {
        CookieSyncManager.createInstance(BaseApplication.getInstance());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setCookie(Url.HOST, "56lcWechat=" + PreferenceManager.getToken());
        CookieSyncManager.getInstance().sync();

    }

    public static void clearCookies() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
    }

    /**
     * 客户端注销cookies
     */
    public void removeCookie() {
        CookieSyncManager.createInstance(BaseApplication.getInstance());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();
        CookieSyncManager.getInstance().sync();
    }

    @Override
    protected void onDestroy() {
        removeCookie();
        if (webview != null) {
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            webview.getSettings().setJavaScriptEnabled(false);
            webview.clearCache(true);
            webview.removeAllViews();
            webview.destroy();
        }
        super.onDestroy();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_back:
                if (webview.canGoBack()) {
                    webview.goBack(); // 后退
                } else {
                    super.onClick(v);
                }
                break;
            default:
                break;
        }
    }
}
