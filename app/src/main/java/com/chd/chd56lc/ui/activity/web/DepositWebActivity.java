package com.chd.chd56lc.ui.activity.web;

import android.content.Intent;
import android.graphics.Bitmap;
import android.webkit.WebView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.manager.WebViewClientManager;
import com.chd.chd56lc.ui.activity.circum.ResultDepositActivity;
import com.chd.chd56lc.ui.activity.personCenter.RiskAssessmentActivity;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.UIUtils;
import com.umeng.analytics.MobclickAgent;

public class DepositWebActivity extends BaseWebviewActivity {

//    type为1，获取存管注册url
//    type为2，存管交易密码
//    type 3充值
//    type 4提现
//    type 5年无忧，季无忧投资
//    type 6债权转让
    /**
     * 初始化数据，并显示到界面上
     */
    public static final String TITLE = "title";
    public static final String DEPOSIT = "deposit";
    String successUrl;
    String failUrl;
    private DepositLinkBean deposit;

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        setTitle(getIntent().getStringExtra(TITLE));
        setTitleBgColor(UIUtils.getColor(R.color.app_main_black));
        setRight(null, R.mipmap.btn_ktcgzh_kf);
        deposit = getIntent().getParcelableExtra(DEPOSIT);
        successUrl = deposit.getSuccessUrl();
        failUrl = deposit.getFailUrl();
        webview.loadUrl(deposit.getUrl());
        webview.setWebViewClient(new WebViewClientManager(this) {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Logger.d("网页", url);
                if (url.contains(successUrl.trim())) {
                    Intent intent = new Intent();
                    switch (url.substring(url.indexOf("type=") + 5, url.indexOf("type=") + 6)) {
                        case "1":
                            startActivity(intent.setClass(activity, RiskAssessmentActivity.class));
                            finish();
                            break;
                        case "2":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "密码设置成功"));
                            finish();
                            break;
                        case "3":
                            startActivity(new Intent(activity, ResultDepositActivity.class)
                                    .putExtra("order_id", deposit.getOrderId())
                                    .putExtra("title", "充值成功"));
                            MobclickAgent.onEvent(activity, UMConstants.LOGIN_RECHARGE_SUCCESS_EVENT);
                            setResult(RESULT_OK);
                            finish();
                            break;
                        case "4":
                            startActivity(new Intent(activity, ResultDepositActivity.class)
                                    .putExtra("order_id", deposit.getOrderId())
                                    .putExtra("title", "提现成功"));
                            setResult(RESULT_OK);
                            finish();
                            break;
                        case "5":
                        case "6":
                            startActivity(new Intent(activity, ResultDepositActivity.class)
                                    .putExtra("order_id", deposit.getOrderId())
                                    .putExtra("title", "投资成功"));
                            MobclickAgent.onEvent(activity, UMConstants.LOGIN_INVEST_PAY_SUCCESS_EVENT);
                            setResult(RESULT_OK);
                            finish();
                            break;
                        case "7":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "银行卡绑定成功"));
                            finish();
                            break;
                        case "8":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "手续费签约成功"));
                            finish();
                            break;
                    }

                    return true;
                } else if (url.contains(failUrl)) {
                    switch (url.substring(url.indexOf("type=") + 5, url.indexOf("type=") + 6)) {
                        case "1":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "开户失败"));
                            break;
                        case "2":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "密码设置失败"));
                            break;
                        case "3":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "充值失败"));
                            break;
                        case "4":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "提现失败"));
                            break;
                        case "5":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "投资失败"));
                            break;
                        case "6":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "转让失败"));
                            break;
                        case "7":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "银行卡绑定失败"));
                            break;
                        case "8":
                            startActivity(new Intent(activity, ResultDepositActivity.class).putExtra("title", "手续费签约失败"));
                            finish();
                            break;
                    }
                    finish();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }
        });
    }
}
