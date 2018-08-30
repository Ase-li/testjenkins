package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.DepositLinkBean;

/**
 * 债转签约
 */
public interface IDebtSignView extends LoadingView {
    void debtSignStatus(DepositLinkBean depositLinkBean);
}
