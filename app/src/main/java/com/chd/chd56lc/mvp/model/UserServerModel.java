package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
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
import com.chd.chd56lc.net.api.UserServerApi;

import java.util.List;

import io.reactivex.Observable;


public class UserServerModel {
    private UserServerApi userServerApi;

    public UserServerModel() {
        userServerApi = BaseApplication.getAppComponent().retrofit().create(UserServerApi.class);
    }

    /**
     * 获取用户信息
     *
     * @return
     */
    public Observable<BaseBean<UserInfoBean>> getUserStatistics() {
        return userServerApi.getUserStatistics();
    }

    /**
     * 获取推送设置
     *
     * @return
     */
    public Observable<BaseBean<PushSetBean>> getPushSettings() {
        return userServerApi.getPushSettings();
    }


    /**
     * 更改手机号第二步
     *
     * @param phone
     * @param newCaptchae
     * @return
     */
    public Observable<BaseBean> resetPhoneStepTwo(String phone, String newCaptchae) {
        return userServerApi.resetPhoneStepTwo(phone, newCaptchae);
    }

    /**
     * 更改手机号第一步
     *
     * @param newCaptchae
     * @return
     */
    public Observable<BaseBean> resetPhoneStepOne(String newCaptchae) {
        return userServerApi.resetPhoneStepOne(newCaptchae);
    }


    /**
     * 设置推送设置
     *
     * @return
     */
    public Observable<BaseBean> setPushSettings(PushSetBean pushSetBean) {
        return userServerApi.setPushSettings(pushSetBean);
    }

    /**
     * 个人开户接口
     *
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> createAccountP() {
        return userServerApi.createAccountP();
    }

    /**
     * 解绑银行卡
     *
     * @return
     */
    public Observable<BaseBean> unbindBankCard(String phone, String captcha) {
        return userServerApi.unbindBankCard(phone, captcha);
    }

    /**
     * 绑定银行卡Url
     *
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> bindBankCard() {
        return userServerApi.bindBankCard();
    }

    /**
     * 重置交易密码Url
     *
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> resetPassword() {
        return userServerApi.resetPassword();
    }

    /**
     * 更改登录密码
     *
     * @param phone           手机号
     * @param password        密码
     * @param confirmPassword 确认密码
     * @param captcha         验证码
     * @return
     */
    public Observable<BaseBean> changePassword(String phone, String password, String confirmPassword, String captcha) {
        return userServerApi.changePassword(phone, password, confirmPassword, captcha);
    }

    /**
     * 忘记密码
     *
     * @param phone           手机号
     * @param password        密码
     * @param confirmPassword 确认密码
     * @param captcha         验证码
     * @return
     */
    public Observable<BaseBean> forgotPassword(String phone, String password, String confirmPassword, String captcha) {
        return userServerApi.forgotPassword(phone, password, confirmPassword, captcha);
    }

    /**
     * 获取存管状态
     */
    public Observable<BaseBean<DepositLinkBean>> getDepositInfo() {
        return userServerApi.getDepositInfo();
    }

    /**
     * 设置手势密码
     *
     * @param gesture
     * @param confirmGesture
     * @return
     */
    public Observable<BaseBean> setGesture(String gesture, String confirmGesture) {
        return userServerApi.setGesture(gesture, confirmGesture);
    }

    /**
     * 验证手势
     *
     * @param gesture
     * @return
     */
    public Observable<BaseBean> verifyGesture(String gesture) {
        return userServerApi.verifyGesture(gesture);
    }

    /**
     * 开启手势
     *
     * @param openGesture
     * @return
     */
    public Observable<BaseBean> setIfGesture(boolean openGesture) {
        return userServerApi.setIfGesture(openGesture);
    }

    /**
     * 开启指纹
     *
     * @param openGesture
     * @return
     */
    public Observable<BaseBean> setIfTouchID(boolean openGesture) {
        return userServerApi.setIfTouchID(openGesture);
    }

    /**
     * 风险测评
     *
     * @param score 0：保守型
     * @return
     */
    public Observable<BaseBean> setRaLevel(String score) {
        return userServerApi.setRaLevel(score);
    }


    /**
     * 户资金,获取用户银行限额信息
     *
     * @return
     */
    public Observable<BaseBean<BankInfoOrBalanceBean>> getBankLimitInfo() {
        return userServerApi.getBankLimitInfo();
    }

    /**
     * 获取首页聚合信息(消息数,新手任务首投标的等)
     *
     * @return
     */
    public Observable<BaseBean<HomeDataBean>> getHomePageData() {
        return userServerApi.getHomePageData();
    }

    /**
     * 查询佣金及在持信息
     *
     * @return
     */
    public Observable<BaseBean<CommissionInfo>> getCommissionAndHoldInfo() {
        return userServerApi.getCommissionAndHoldInfo();
    }

    /**
     * 佣金明细列表
     *
     * @return
     */
    public Observable<BaseBean<List<CommissionBean>>> listCommissionDetail() {
        return userServerApi.listCommissionDetail();
    }

    /**
     * 分页好友列表
     *
     * @return
     */
    public Observable<BaseBean<PageListBean<FriendBean>>> paginateInvitee(int page, int count) {
        return userServerApi.paginateInvitee(page, count);
    }

    /**
     * 获取用户理财师状态
     *
     * @return
     */
    public Observable<BaseBean<List<Integer>>> listUserFinancialPlannerStatus() {
        return userServerApi.listUserFinancialPlannerStatus();
    }

    /**
     * 申请成为理财师
     *
     * @return
     */
    public Observable<BaseBean> applyForFinancialPlanner() {
        return userServerApi.applyForFinancialPlanner();
    }

    /**
     * 获取优惠券信息
     *
     * @return
     */
    public Observable<BaseBean<List<CouponMsgBean>>> getExpiredCoupon() {
        return userServerApi.getExpiredCoupon();
    }
}
