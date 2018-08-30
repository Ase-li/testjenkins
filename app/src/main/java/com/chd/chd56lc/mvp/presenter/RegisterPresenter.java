package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.UserInfoBean;
import com.chd.chd56lc.helper.TagAliasOperatorHelper;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.model.LoginInfoModel;
import com.chd.chd56lc.mvp.view.IRegisterView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;
import com.chd.chd56lc.utils.Logger;

import javax.inject.Inject;


public class RegisterPresenter extends NetPresenter {
    private IRegisterView iRegisterView;
    private LoginInfoModel loginInfoModel;

    @Inject
    public RegisterPresenter(IRegisterView iRegisterView, LoginInfoModel loginInfoModel) {
        super();
        this.iRegisterView = iRegisterView;
        this.loginInfoModel = loginInfoModel;
    }

    /**
     * 注册
     * loginInfoModel
     *
     * @param phone    手机号码
     * @param password 密码
     * @param captcha  验证码
     */
    public void register(String phone, String inviteCode, String password, String captcha) {
        iRegisterView.showLoading();
        addSubscription(loginInfoModel.register(phone, inviteCode, password, captcha), new ApiCallback<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean userInfo) {
                UserManager.getInstance().saveUserInfo(userInfo);
                UserManager.getInstance().saveLogin(true);
                TagAliasOperatorHelper.getInstance().setAlias(userInfo.getMessagePushCode());
                iRegisterView.registerSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d("onFailure_测试" + msg);
            }

            @Override
            public void onFinish() {
                iRegisterView.dismissLoading();
            }
        });
    }

    /**
     * 验证手机号
     * loginInfoModel
     *
     * @param phone 手机号码
     */
    public void verifyPhoneRegister(String phone) {
        iRegisterView.showLoading();
        addSubscription(loginInfoModel.verifyPhoneRegister(phone), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object userInfo) {
                iRegisterView.registerSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d("onFailure_测试" + msg);
            }

            @Override
            public void onFinish() {
                iRegisterView.dismissLoading();
            }
        });
    }
}
