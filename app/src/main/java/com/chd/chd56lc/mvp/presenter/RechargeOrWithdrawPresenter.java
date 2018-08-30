package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.WithDrawInfo;
import com.chd.chd56lc.mvp.model.BalanceModel;
import com.chd.chd56lc.mvp.view.IRechargeOrWithdrawView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import javax.inject.Inject;

public class RechargeOrWithdrawPresenter extends NetPresenter {
    private BalanceModel balanceModel;
    private IRechargeOrWithdrawView rechargeOrWithdrawView;

    @Inject
    public RechargeOrWithdrawPresenter(IRechargeOrWithdrawView rechargeOrWithdrawView, BalanceModel balanceModel) {
        this.balanceModel = balanceModel;
        this.rechargeOrWithdrawView = rechargeOrWithdrawView;
    }

    /**
     * 充值
     */
    public void rechargeP(double amount) {
        rechargeOrWithdrawView.showLoading();
        addSubscription(balanceModel.rechargeP(amount), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean model) {
                rechargeOrWithdrawView.rechargeOrWithdraw(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                rechargeOrWithdrawView.dismissLoading();
            }
        });
    }

    /**
     * 提现
     */
    public void withdrawP(double amount, boolean couponStatus) {
        rechargeOrWithdrawView.showLoading();
        addSubscription(balanceModel.withdrawP(amount, couponStatus), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean model) {
                rechargeOrWithdrawView.rechargeOrWithdraw(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                rechargeOrWithdrawView.dismissLoading();
            }
        });
    }

    /**
     * 查询个人银行信息,包括余额,处理中的银行信息
     */
    public void queryUserBankInfo(int type) {
        rechargeOrWithdrawView.showLoading();
        addSubscription(balanceModel.queryRechargeUserBankInfo(type), new ApiCallback<BankInfoOrBalanceBean>() {
            @Override
            public void onSuccess(BankInfoOrBalanceBean model) {
                rechargeOrWithdrawView.setBankInfo(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                rechargeOrWithdrawView.dismissLoading();
            }
        });
    }

    /**
     * 提现查看提现手续费与提现券
     */
    public void queryPersonalWithdrawInfo(String amount) {
        rechargeOrWithdrawView.showLoading();
        addSubscription(balanceModel.queryPersonalWithdrawInfo(amount), new ApiCallback<WithDrawInfo>() {
            @Override
            public void onSuccess(WithDrawInfo model) {
                rechargeOrWithdrawView.withdrawInfo(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                rechargeOrWithdrawView.dismissLoading();
            }
        });
    }
}
