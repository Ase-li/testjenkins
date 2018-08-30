package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.CommissionBean;
import com.chd.chd56lc.entity.CommissionInfo;
import com.chd.chd56lc.entity.CouponMsgBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.FriendBean;
import com.chd.chd56lc.entity.HomeDataBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.entity.PushSetBean;
import com.chd.chd56lc.entity.UserInfoBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserServerApi {
    /**
     * 获取用户数据
     *
     * @return
     */
    @GET("api/service-user/app/userInfo/getUserStatistics")
    Observable<BaseBean<UserInfoBean>> getUserStatistics();

    /**
     * 获取推送设置
     *
     * @return
     */
    @GET("api/service-user/app/userSettings/getPushSettings")
    Observable<BaseBean<PushSetBean>> getPushSettings();

    /**
     * 设置推送设置
     *
     * @return
     */
    @POST("api/service-user/app/userSettings/setPushSettings")
    Observable<BaseBean> setPushSettings(@Body PushSetBean pushSetBean);

    //更改登录密码
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/changePassword")
    Observable<BaseBean> changePassword(@Field("phone") String phone, @Field("password") String password, @Field("confirmPassword") String confirmPassword, @Field("captcha") String captcha);

    //更改登录密码
    @FormUrlEncoded
    @POST("api/service-user/app/userLogin/forgotPassword")
    Observable<BaseBean> forgotPassword(@Field("phone") String phone, @Field("password") String password, @Field("confirmPassword") String confirmPassword, @Field("captcha") String captcha);

    /**
     * @param captcha 验证码
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-user/app/userSettings/resetPhoneStepOne")
    Observable<BaseBean> resetPhoneStepOne(@Field("captcha") String captcha);

    /**
     * @param phone   新手机号码
     * @param captcha 验证码
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-user/app/userSettings/resetPhoneStepTwo")
    Observable<BaseBean> resetPhoneStepTwo(@Field("phone") String phone, @Field("captcha") String captcha);

    /**
     * 更新账户风险测评
     *
     * @param score
     */
    @FormUrlEncoded
    @POST("api/service-user/app/userSettings/setRaLevel")
    Observable<BaseBean> setRaLevel(@Field("score") String score);

    //个人开户接口(页面)
    @GET("api/service-user/app/account/createAccountP")
    Observable<BaseBean<DepositLinkBean>> createAccountP();

    //解绑银行卡
    @FormUrlEncoded
    @POST("/api/service-user/app/deposit/unbindBankCard")
    Observable<BaseBean> unbindBankCard(@Field("phone") String phone, @Field("captcha") String captcha);

    //绑定银行卡Url
    @GET("api/service-user/app/deposit/bindBankCard")
    Observable<BaseBean<DepositLinkBean>> bindBankCard();

    //重置交易密码Url
    @GET("api/service-user/app/deposit/resetPassword")
    Observable<BaseBean<DepositLinkBean>> resetPassword();

    //获取存管状态
    @GET("api/service-user/app/deposit/getDepositInfo")
    Observable<BaseBean<DepositLinkBean>> getDepositInfo();

    /**
     * 开启/关闭手势密码
     *
     * @param open
     * @return
     */
    @FormUrlEncoded
    @POST("/api/service-user/app/userSettings/setIfGesture")
    Observable<BaseBean> setIfGesture(@Field("open") boolean open);

    /**
     * 验证手势
     *
     * @param gesture
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-user/app/userSettings/verifyGesture")
    Observable<BaseBean> verifyGesture(@Field("gesture") String gesture);

    /**
     * 设置手势密码
     *
     * @param gesture
     * @param confirmGesture
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-user/app/userSettings/setGesture")
    Observable<BaseBean> setGesture(@Field("gesture") String gesture, @Field("confirmGesture") String confirmGesture);

    /**
     * 开启/关闭touchID
     *
     * @param open
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-user/app/userSettings/setIfTouchID")
    Observable<BaseBean> setIfTouchID(@Field("open") boolean open);


    //用户资金,获取用户银行限额信息
    @POST("api/service-depository/app/funds/getBankLimitInfo")
    Observable<BaseBean<BankInfoOrBalanceBean>> getBankLimitInfo();

    /**
     * 获取首页聚合信息(消息数,新手任务首投标的等)
     */
    @GET("api/service-user/app/frontend/getHomePageData")
    Observable<BaseBean<HomeDataBean>> getHomePageData();

    /**
     * 查询佣金及在持信息
     */
    @GET("/api/service-depository/app/commissionRecord/getCommissionAndHoldInfo")
    Observable<BaseBean<CommissionInfo>> getCommissionAndHoldInfo();

    /**
     * 佣金明细列表
     */
    @GET("/api/service-depository/app/commissionRecord/listCommissionDetail")
    Observable<BaseBean<List<CommissionBean>>> listCommissionDetail();

    /**
     * 分页好友列表
     */
    @GET("/api/service-depository/app/commissionRecord/paginateInvitee")
    Observable<BaseBean<PageListBean<FriendBean>>> paginateInvitee(@Query("page") int page, @Query("count") int count);

    /**
     * 申请成为理财师
     */
    @POST("/api/service-user/app/financialPlanner/applyForFinancialPlanner")
    Observable<BaseBean> applyForFinancialPlanner();

    /**
     * 获取用户理财师状态
     */
    @GET("/api/service-user/app/financialPlanner/listUserFinancialPlannerStatus")
    Observable<BaseBean<List<Integer>>> listUserFinancialPlannerStatus();

    /**
     * 获取优惠券信息
     */
    @GET("/api/service-depository/app/awardUser/getExpiredCoupon")
    Observable<BaseBean<List<CouponMsgBean>>> getExpiredCoupon();

}
