package com.chd.chd56lc.ui.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.manager.WebViewClientManager;
import com.chd.chd56lc.utils.AppUtil;
import com.chd.chd56lc.widget.InvestScrollView;
import com.chd.chd56lc.widget.InvestWebView;

/**
 * Created by li on 2018/3/24.
 */

public class InvestIntroduceAdapter extends PagerAdapter {

    String[] tab_array = new String[]{"项目介绍", "风险提示", "多重保障"};

    private Context context;
    private LayoutInflater inflater;
    private int type;
    private int interestWay;
    private WebView webview;
    ViewGroup viewGroup;
    private ScrollView ss_month;
    private ProgressBar myProgressBar;
    public WebView getWebview() {
        return webview;
    }

    public ScrollView getSs_month() {
        return ss_month;
    }

    public InvestIntroduceAdapter(Context context, int type, int interestWay) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.type = type;
        this.interestWay = interestWay;
    }

    public void closeWebview() {
        if (webview != null && viewGroup != null) {
            webview.destroy();
            webview.removeAllViews();
        }
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return tab_array.length;
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //展示的view
    @SuppressLint("ResourceAsColor")
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View inflate = inflater.inflate(R.layout.invest_product_present_season, container, false);
        switch (position) {
            case 0:
                switch (type) {
                    case 1:
                        if (interestWay == 2) {
                            inflate = inflater.inflate(R.layout.invest_product_present_season, container, false);
                        } else {
                            inflate = inflater.inflate(R.layout.invest_product_present, container, false);
                        }
                        break;
                    case 2:
                        inflate = inflater.inflate(R.layout.invest_product_present_day, container, false);
                        break;
                }
                break;
            case 1:
                inflate = inflater.inflate(R.layout.invest_risk_warm, container, false);
                break;
            case 2:
                inflate = inflater.inflate(R.layout.activity_base_web, container, false);
                inflate.findViewById(R.id.top).setVisibility(View.GONE);
                myProgressBar=inflate.findViewById(R.id.myProgressBar);
                viewGroup = (ViewGroup) inflate.findViewById(R.id.fl_wv);
                webview = new InvestWebView(BaseApplication.getInstance());
                viewGroup.addView(webview);
                WebSettings settings = webview.getSettings();
                settings.setJavaScriptEnabled(true); // 启用JS脚本
                settings.setUseWideViewPort(true);// 设置此属性，可任意比例缩放
                settings.setLoadWithOverviewMode(true);
                settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
                String userAgentString = settings.getUserAgentString();
                settings.setUserAgentString(userAgentString + " 56lf/" + AppUtil.versionName());
                webview.setWebViewClient(new WebViewClientManager((Activity) context));
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
                });
                webview.loadUrl(Url.MULTISCURE);
                break;
        }
        //添加到容器
        container.addView(inflate);
        //返回显示的view
        return inflate;
    }

    //销毁view
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //从容器中移除view
        container.removeView((View) object);
//        if (webview != null) {
//            webview.setWebChromeClient(null);
//            webview.setWebViewClient(null);
//            webview.getSettings().setJavaScriptEnabled(false);
//            webview.clearCache(true);
//            webview.removeAllViews();
//            webview.destroy();
//        }
    }


    @Override
    public CharSequence getPageTitle(int position) {

        return tab_array[position];
    }
}
