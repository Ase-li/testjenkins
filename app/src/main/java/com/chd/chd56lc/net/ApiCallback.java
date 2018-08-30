package com.chd.chd56lc.net;


import android.content.Intent;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.PreferenceConstant;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.manager.AppManager;
import com.chd.chd56lc.manager.PreferenceManager;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.utils.StringUtils;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;


public abstract class ApiCallback<M> implements Observer<BaseBean<M>> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(int code, String msg);

    public abstract void onFinish();


    private int errorCode = -1111;
    private String errorMsg = "未知的错误！";
    private final int RESPONSE_CODE_OK = 0;       //自定义的业务逻辑，成功返回积极数据
    private final int RESPONSE_FATAL_EOR = -1;    //返回数据失败,严重的错误
    private Disposable disposable;

    @Override
    public void onSubscribe(Disposable d) {
        this.disposable = d;
    }

    public Disposable getDisposable() {
        return disposable;
    }

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            errorCode = httpException.code();
            errorMsg = httpException.getMessage();
            getErrorMsg(httpException);
        } else if (e instanceof SocketTimeoutException) {  //VPN open
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "服务器响应超时";
        } else if (e instanceof ConnectException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "网络连接异常，请检查网络";
        } else if (e instanceof UnknownHostException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "无法解析主机，请检查网络连接";
        } else if (e instanceof UnknownServiceException) {
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "未知的服务器错误";
        } else if (e instanceof IOException) {   //飞行模式等
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "没有网络，请检查网络连接";
        } else if (e instanceof NetworkOnMainThreadException) {//主线程不能网络请求，这个很容易发现
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "主线程不能网络请求";

        } else if (e instanceof RuntimeException) { //很多的错误都是extends RuntimeException
            errorCode = RESPONSE_FATAL_EOR;
            errorMsg = "运行时错误" + e.toString();
        }
        BaseApplication.getAppComponent().customToast().setText(errorMsg);
        handleError(errorCode, errorMsg);
        onFinish();
    }

    private void getErrorMsg(HttpException httpException) {

    }

    @Override
    public void onNext(BaseBean<M> mHttpResponse) {
        if (mHttpResponse.getStatus().getErrCode() == 200) {
            onSuccess(mHttpResponse.getData());
        } else {
            handleError(mHttpResponse.getStatus().getErrCode(), mHttpResponse.getStatus().getMessage());
        }
    }

    @Override
    public void onComplete() {
        onFinish();
    }

    void handleError(Integer errorCode, String errorMsg) {
        switch (errorCode) {
            case 506:
                BaseApplication.getAppComponent().customToast().setText(errorMsg);
                PreferenceManager.saveValue(PreferenceConstant.ACCESS_TOKEN, "");
                UserManager.getInstance().saveLogin(false);
                UserManager.getInstance().saveUserInfo(null);
                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        Intent intent = new Intent(AppManager.getAppManager().currentActivity(),
                                LoginActivity.class);
                        AppManager.getAppManager().currentActivity().startActivity(intent);
                    }
                }, 1000);
                break;
            default:
                if (!StringUtils.isEmpty(errorMsg))
                    BaseApplication.getAppComponent().customToast().setText(errorMsg);
                onFailure(errorCode, errorMsg);
                break;
        }
    }

}
