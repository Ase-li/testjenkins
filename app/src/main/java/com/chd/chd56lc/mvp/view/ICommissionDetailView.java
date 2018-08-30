package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.CommissionBean;

import java.util.List;

/**
 * 佣金列表
 */
public interface ICommissionDetailView extends LoadingView {

    /**
     * 佣金列表
     *
     * @param commissionBeans
     */
    void setCommissionBeans(List<CommissionBean> commissionBeans);
}
