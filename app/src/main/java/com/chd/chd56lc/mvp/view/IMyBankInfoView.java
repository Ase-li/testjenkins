package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;

/**
 * 我的银行卡
 */
public interface IMyBankInfoView extends LoadingView {
    /**
     * 绑定卡
     *
     * @param depositLinkBean
     */
    void bindBankCard(DepositLinkBean depositLinkBean);

    /**
     * 解除绑定
     */
    void unbindBankCard();

    /**
     * 获取银行卡信息
     */
    void updateBankInfo(BankInfoOrBalanceBean bankInfoOrBalanceBean);
}
