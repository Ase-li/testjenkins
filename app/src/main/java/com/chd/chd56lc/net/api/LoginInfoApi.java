package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.LoginResponseInfo;
import com.chd.chd56lc.entity.UserInfoBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginInfoApi {
    //自动登录
    @POST("api/service-user/app/userLogin/autoLogin")
    Observable<BaseBean<UserInfoBean>> autoLogin();

    //检查用户登录状态
    @POST("api/service-user/app/userLogin/checkLogin")
    Observable<BaseBean<UserInfoBean>> checkLogin();

    //获取验证码
    //验证码类型 0-注册 1-登录 2-二次认证 3-更改登录密码 4-二次认证设置密码
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/getCaptcha")
    Observable<BaseBean> getCaptcha(@Field("captchaType") String captchaType, @Field("phone") String phone);

    //退出登录
    @POST("api/service-user/app/userLogin/logOut")
    Observable<BaseBean> logOut();

    //登录
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/login")
    Observable<BaseBean<LoginResponseInfo>> login(@Field("phone") String phone, @Field("password") String password, @Field("deviceNo") String deviceNo);

    //验证码登录
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/loginWithCaptcha")
    Observable<BaseBean<LoginResponseInfo>> loginWithCaptcha(@Field("phone") String phone, @Field("captcha") String captcha, @Field("deviceNo") String deviceNo);

    //注册账号
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/register")
    Observable<BaseBean<UserInfoBean>> register(@Field("phone") String phone, @Field("inviteCode") String inviteCode, @Field("password") String password, @Field("confirmPassword") String confirmPassword, @Field("captcha") String captcha, @Field("deviceNo") String deviceNo);

    //验证手机号
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/verifyPhoneRegister")
    Observable<BaseBean> verifyPhoneRegister(@Field("phone") String phone);

    //设置登录密码
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/setPassword")
    Observable<BaseBean<LoginResponseInfo>> setPassword(@Field("phone") String phone, @Field("password") String password, @Field("confirmPassword") String confirmPassword);

}
