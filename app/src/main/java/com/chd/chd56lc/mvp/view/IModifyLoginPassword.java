package com.chd.chd56lc.mvp.view;
/**
 * 修改登录密码
 */
public interface IModifyLoginPassword extends LoadingView {
    /**
     * 更新密码成功
     */
    void modifySuccess();

    /**
     * 更新密码失败
     */
    void modifyFailure();
}
