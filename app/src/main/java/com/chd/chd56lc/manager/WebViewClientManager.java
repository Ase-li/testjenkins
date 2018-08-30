package com.chd.chd56lc.manager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.view.Gravity;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.entity.SocialShareData;
import com.chd.chd56lc.ui.activity.web.BaseWebviewActivity;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.helper.MsgHelper;
import com.chd.chd56lc.utils.NetUtil;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.dialog.ShareBoard;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.net.URLDecoder;

/**
 * 桥接库，拦截url方式
 * Created by li on 2018/2/27.
 */

public class WebViewClientManager extends WebViewClient {
    public static final String RETURN_URL = "return_url";

    private Activity activity;

    public WebViewClientManager(Activity activity) {
        this.activity = activity;
    }


    @Override
    public boolean shouldOverrideUrlLoading(WebView view, final String url) {
        try {
            if (!NetUtil.isNetworkAvailable()) {
                BaseApplication.getAppComponent().customToast().setText("当前网络异常，请刷新重试");
                return true;
            }
            Logger.d("url", url);
            if (url.contains("56lc://login")) {
                if (!UserManager.getInstance().isLogin()) {
                    BaseApplication.getAppComponent().customToast().setText("登录后才能查看哦");
                    new Handler().postDelayed(new Runnable() {

                        @Override
                        public void run() {
                            Intent intent = new Intent(activity, LoginActivity.class);
                            try {
                                JSONObject jsonObject = new JSONObject(url.substring(url.indexOf("?") + 1));
                                String returnUrl = jsonObject.getString("returnUrl");
                                if (!StringUtils.isEmpty(returnUrl) && activity instanceof BaseWebviewActivity) {
                                    ((BaseWebviewActivity) activity).setReturnUrl(returnUrl);
                                }
                                activity.startActivity(intent);
                                activity.finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    }, 1000);
                    view.loadUrl("about:blank");
                    return true;
                }
//            sso(view, url.substring(url.indexOf("https://")));
                return true;
            } else if (url.contains("56lc://share")) {
                String query = URLDecoder.decode(url.substring(url.indexOf("?") + 1).replaceAll("%(?![0-9a-fA-F]{2})", "%25"), "UTF-8");
                SocialShareData socialShareData = new Gson().fromJson(query, SocialShareData.class);
                new ShareBoard(activity, socialShareData, new ShareBoard.ShareResponse() {
                    @Override
                    public void setRet(int ret) {
                    }
                }).showAtLocation(activity.getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
            } else if (url.contains("56lc://backToRoot")) {
                activity.startActivity(new Intent(activity, MainActivity.class));
                activity.finish();
                return true;
            } else if (url.contains("56lc://goBack")) {
                activity.finish();
                return true;
            } else if (url.contains("tel:")) {
                if (MsgHelper.isHasSimCard(BaseApplication.getInstance())) {
                    AlertDialog dialog = new AlertDialog.Builder(activity)
                            .setTitle("呼叫")
                            .setMessage(url.substring(url.indexOf(":") + 1))
                            .setPositiveButton("呼叫", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_CALL);//设置动作
                                    intent.setData(Uri.parse(url));//设置要传递的数据 uri格式的数据 同意资源标识符 url
                                    intent.addCategory(Intent.CATEGORY_DEFAULT);//添加类别
                                    activity.startActivity(intent);//执行意图
                                }
                            }).create();
                    dialog.show();
                } else {
                    BaseApplication.getAppComponent().customToast().setText("没有sim卡...");
                }
                return true;
            }
        } catch (Exception e) {
            Logger.d("异常", e.toString());
        }
        return super.shouldOverrideUrlLoading(view, url);
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
//        try {
//            URL url1 = new URL(url);
//            if ("/".equals(url1.getPath())) {
//                activity.startActivity(new Intent(activity, MainActivity.class));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String
            failingUrl) {
//        WaitDialog.getInstance().closeDialog();
        BaseApplication.getAppComponent().customToast().setText("当前网络异常，请刷新重试");
    }

    /**
     * 客户端设置cookies
     */
    public void synCookies(String url) {
        CookieSyncManager.createInstance(activity);
        CookieManager cookieManager = CookieManager.getInstance();
        if (url.contains(".html"))
            cookieManager.setCookie(Url.HOSTHOME, "PHPSESSID=" + PreferenceManager.getToken());
        else
            cookieManager.setCookie(url, "PHPSESSID=" + PreferenceManager.getToken());
    }
}
