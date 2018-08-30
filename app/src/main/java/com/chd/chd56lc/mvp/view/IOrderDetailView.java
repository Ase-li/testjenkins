package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.OrderDetailInfo;

/**
 * 订单详情
 */
public interface IOrderDetailView extends LoadingView {
    void setOrderDetail(OrderDetailInfo periodOrderDetailInfo);
}
