package com.chd.chd56lc.mvp.view;

public interface ILoginView extends LoadingView {
    /**
     * 双因子登录
     *
     * @param loginStatus 登录状态
     */
    void login(int loginStatus);

}
