package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.LoginResponseInfo;
import com.chd.chd56lc.helper.TagAliasOperatorHelper;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.model.LoginInfoModel;
import com.chd.chd56lc.mvp.view.ILoginView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;
import com.chd.chd56lc.utils.Logger;

import javax.inject.Inject;


public class LoginPresenter extends NetPresenter {
    private ILoginView iLoginView;
    private LoginInfoModel loginInfoModel;

    @Inject
    public LoginPresenter(ILoginView iLoginView, LoginInfoModel loginInfoModel) {
        super();
        this.iLoginView = iLoginView;
        this.loginInfoModel = loginInfoModel;
    }

    /**
     * 密码登录
     *
     * @param phone
     * @param password
     */
    public void login(String phone, String password) {
        iLoginView.showLoading();
        addSubscription(loginInfoModel.login(phone, password), new ApiCallback<LoginResponseInfo>() {
            @Override
            public void onSuccess(LoginResponseInfo loginResponseInfo) {
                if (loginResponseInfo.getCode() == 2) {
                    UserManager.getInstance().saveUserInfo(loginResponseInfo.getUserInfo());
                    TagAliasOperatorHelper.getInstance().setAlias(loginResponseInfo.getUserInfo().getMessagePushCode());
                }
//                TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
//                tagAliasBean.isAliasAction = true;
//                tagAliasBean.alias = loginResponseInfo.resetPhoneStepTwo().get;
//                tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
//                TagAliasOperatorHelper.getInstance().handleAction(BaseApplication.getInstance(), TagAliasOperatorHelper.sequence, tagAliasBean);
                iLoginView.login(loginResponseInfo.getCode());
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d("onFailure_测试" + msg);
            }

            @Override
            public void onFinish() {
                iLoginView.dismissLoading();
            }
        });
    }

    /**
     * 验证码登录
     *
     * @param phone
     * @param captcha
     */
    public void loginBySms(String phone, String captcha) {
        iLoginView.showLoading();
        addSubscription(loginInfoModel.loginWithCaptcha(phone, captcha), new ApiCallback<LoginResponseInfo>() {
            @Override
            public void onSuccess(LoginResponseInfo loginResponseInfo) {
                if (loginResponseInfo.getCode() == 2) {
                    UserManager.getInstance().saveUserInfo(loginResponseInfo.getUserInfo());
                    TagAliasOperatorHelper.getInstance().setAlias(loginResponseInfo.getUserInfo().getMessagePushCode());
                }
                iLoginView.login(loginResponseInfo.getCode());
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d(msg);
            }

            @Override
            public void onFinish() {
                iLoginView.dismissLoading();
            }
        });
    }

    /**
     * 双因子设置密码
     *
     * @param phone
     * @param password
     * @param confirmPassword
     */
    public void loginSetPassword(String phone, String password, String confirmPassword) {
        iLoginView.showLoading();
        addSubscription(loginInfoModel.setPassword(phone, password, confirmPassword), new ApiCallback<LoginResponseInfo>() {
            @Override
            public void onSuccess(LoginResponseInfo loginResponseInfo) {
                if (loginResponseInfo.getCode() == 2) {
                    UserManager.getInstance().saveUserInfo(loginResponseInfo.getUserInfo());
                    TagAliasOperatorHelper.getInstance().setAlias(loginResponseInfo.getUserInfo().getMessagePushCode());
                }
                iLoginView.login(loginResponseInfo.getCode());
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d(msg);
            }

            @Override
            public void onFinish() {
                iLoginView.dismissLoading();
            }
        });
    }
}
