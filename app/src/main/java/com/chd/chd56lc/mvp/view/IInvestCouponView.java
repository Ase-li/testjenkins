package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.CouponBean;

import java.util.List;
/**
 * 投资可用红包
 */
public interface IInvestCouponView extends LoadingView {
    void updateCouponList(List<CouponBean.Item> couponBean);
}
