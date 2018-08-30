package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.CommissionInfo;

/**
 * 佣金详情
 */
public interface ICommissionInfoView extends LoadingView {

    /**
     * 佣金详情
     *
     * @param commissionInfo
     */
    void setCommissionInfo(CommissionInfo commissionInfo);
}
