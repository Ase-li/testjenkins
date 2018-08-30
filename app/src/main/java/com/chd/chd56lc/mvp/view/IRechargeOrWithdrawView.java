package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.WithDrawInfo;
/**
 * 充值提现
 */
public interface IRechargeOrWithdrawView extends LoadingView {
    /**
     * 个人银行信息，正在处理信息
     *
     * @param bankInfoOrBalanceBean
     */
    void setBankInfo(BankInfoOrBalanceBean bankInfoOrBalanceBean);

    /**
     * 充值提现
     *
     * @param depositLinkBean
     */
    void rechargeOrWithdraw(DepositLinkBean depositLinkBean);

    /**
     * 提现查看提现券 提现页面使用
     *
     * @param withDrawInfo
     */
    void withdrawInfo(WithDrawInfo withDrawInfo);
}
