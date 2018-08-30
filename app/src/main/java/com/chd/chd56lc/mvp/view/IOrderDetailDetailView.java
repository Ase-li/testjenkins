package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.OrderDetailDetailBean;

import java.util.List;

/**
 * 订单详情
 */
public interface IOrderDetailDetailView extends LoadingView {
    void setOrderDetailDetail(List<OrderDetailDetailBean> orderDetailDetailBeans);
}
