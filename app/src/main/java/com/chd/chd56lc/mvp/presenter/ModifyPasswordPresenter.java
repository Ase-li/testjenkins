package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IModifyLoginPassword;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class ModifyPasswordPresenter extends NetPresenter {
    public static final int LOGIN_OUT = 0x0001;
    public static final int RESET_PASSWORD = 0x0002;
    private UserServerModel userServerModel;
    private IModifyLoginPassword iModifyLoginPassword;

    public ModifyPasswordPresenter(IModifyLoginPassword iModifyLoginPassword, UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
        this.iModifyLoginPassword = iModifyLoginPassword;
    }

    /**
     * 更改登录密码
     *
     * @param phone
     * @param password
     * @param confirmPassword
     * @param captcha
     */
    public void changePassword(String phone, String password, String confirmPassword, String captcha) {
        iModifyLoginPassword.showLoading();
        addSubscription(userServerModel.changePassword(phone, password, confirmPassword, captcha), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                iModifyLoginPassword.modifySuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                iModifyLoginPassword.modifyFailure();
            }

            @Override
            public void onFinish() {
                iModifyLoginPassword.dismissLoading();
            }
        });
    }

    /**
     * 忘记登录密码
     *
     * @param phone
     * @param password
     * @param confirmPassword
     * @param captcha
     */
    public void forgotPassword(String phone, String password, String confirmPassword, String captcha) {
        iModifyLoginPassword.showLoading();
        addSubscription(userServerModel.forgotPassword(phone, password, confirmPassword, captcha), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                iModifyLoginPassword.modifySuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                iModifyLoginPassword.modifyFailure();
            }

            @Override
            public void onFinish() {
                iModifyLoginPassword.dismissLoading();
            }
        });
    }

}
