package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.LoginResponseInfo;
import com.chd.chd56lc.entity.UserInfoBean;
import com.chd.chd56lc.net.api.LoginInfoApi;
import com.chd.chd56lc.utils.AppUtil;

import io.reactivex.Observable;

public class LoginInfoModel {
    private LoginInfoApi loginInfoApi;

    public LoginInfoModel() {
        if (loginInfoApi == null)
            this.loginInfoApi = BaseApplication.getAppComponent().retrofit().create(LoginInfoApi.class);

    }

    /**
     * 自动登录
     *
     * @return
     */
    public Observable<BaseBean<UserInfoBean>> autoLogin() {
        return loginInfoApi.autoLogin();
    }

    /**
     * 检查用户登录状态
     *
     * @return
     */
    public Observable<BaseBean<UserInfoBean>> checkLogin() {
        return loginInfoApi.checkLogin();
    }

    /**
     * 退出登录
     *
     * @return
     */
    public Observable<BaseBean> logOut() {
        return loginInfoApi.logOut();
    }

    /**
     * 获取验证码
     *
     * @param captchaType 验证码类型 0-注册 1-登录 2-二次认证 3-更改登录密码 4-二次认证设置密码
     * @param phone
     * @return
     */
    public Observable<BaseBean> getCaptcha(String captchaType, String phone) {
        return loginInfoApi.getCaptcha(captchaType, phone);
    }

    /**
     * 登录
     *
     * @param phone
     * @param password
     * @return
     */
    public Observable<BaseBean<LoginResponseInfo>> login(String phone, String password) {
        return loginInfoApi.login(phone, password, AppUtil.getDeviceId());
    }

    /**
     * 验证码登录
     *
     * @param phone
     * @param captcha
     * @return
     */
    public Observable<BaseBean<LoginResponseInfo>> loginWithCaptcha(String phone, String captcha) {
        return loginInfoApi.loginWithCaptcha(phone, captcha, AppUtil.getDeviceId());
    }

    /**
     * 注册账号
     *
     * @param phone
     * @param password
     * @param captcha
     * @return
     */
    public Observable<BaseBean<UserInfoBean>> register(String phone, String inviteCode, String password, String captcha) {
        return loginInfoApi.register(phone, inviteCode, password, password, captcha, AppUtil.getDeviceId());
    }

    /**
     * 验证手机号
     *
     * @param phone
     * @return
     */
    public Observable<BaseBean> verifyPhoneRegister(String phone) {
        return loginInfoApi.verifyPhoneRegister(phone);
    }

    /**
     * 设置登录密码
     *
     * @param phone
     * @param password
     * @param confirmPassword
     * @return
     */
    public Observable<BaseBean<LoginResponseInfo>> setPassword(String phone, String password, String confirmPassword) {
        return loginInfoApi.setPassword(phone, password, confirmPassword);
    }

}
