package com.chd.chd56lc.mvp.view;

/**
 * 网络加载
 */
public interface LoadingView {
    void showLoading();

    void dismissLoading();

    void showMsgLoading(String msg);
}
