package com.chd.chd56lc.mvp.model;


import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.WithDrawInfo;
import com.chd.chd56lc.net.api.BalanceApi;

import io.reactivex.Observable;

public class BalanceModel {
    private BalanceApi balanceApi;

    public BalanceModel() {
        this.balanceApi = BaseApplication.getAppComponent().retrofit().create(BalanceApi.class);
    }

    /**
     * 查询个人银行信息,包括余额,处理中的银行信息
     *
     * @return
     */
    public Observable<BaseBean<BankInfoOrBalanceBean>> queryRechargeUserBankInfo(int type) {
        return balanceApi.queryRechargeUserBankInfo(type);
    }

    /**
     * 绑定卡到电子账户充值
     *
     * @param amount
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> rechargeP(double amount) {
        return balanceApi.rechargeP(amount,1);
    }

    /**
     * 提现(页面)
     *
     * @param amount
     * @param couponStatus
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> withdrawP(double amount, boolean couponStatus) {
        return balanceApi.withdrawP(amount, couponStatus,1);
    }

    /**
     * 个人用户提现信息查询
     *
     * @return
     */
    public Observable<BaseBean<WithDrawInfo>> queryPersonalWithdrawInfo(String amount) {
        return balanceApi.queryPersonalWithdrawInfo(amount);
    }

}
