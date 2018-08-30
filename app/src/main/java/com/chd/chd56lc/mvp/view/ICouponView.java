package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.CouponBean;

/**
 * 优惠券列表
 */
public interface ICouponView extends LoadingView {
    void updateCouponList(CouponBean couponBean);
}
