package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.CouponMsgBean;
import com.chd.chd56lc.entity.UserInfoBean;

/**
 * 个人中心
 */
public interface IPersonCenterView extends LoadingView {
    /**
     * 更新个人信息
     *
     * @param userInfo
     */
    void updateUserInfo(UserInfoBean userInfo);

    /**
     * 更新优惠券信息
     *
     * @param couponMsgBean
     */
    void updateCouponMsg(CouponMsgBean couponMsgBean);
}
