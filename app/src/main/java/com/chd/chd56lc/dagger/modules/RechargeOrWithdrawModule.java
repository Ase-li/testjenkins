package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.BalanceModel;
import com.chd.chd56lc.mvp.presenter.RechargeOrWithdrawPresenter;
import com.chd.chd56lc.mvp.view.IRechargeOrWithdrawView;

import dagger.Module;
import dagger.Provides;

@Module
public class RechargeOrWithdrawModule {
    private IRechargeOrWithdrawView iRechargeOrWithdrawView;

    public RechargeOrWithdrawModule(IRechargeOrWithdrawView iRechargeOrWithdrawView) {
        this.iRechargeOrWithdrawView = iRechargeOrWithdrawView;
    }

    @Provides
    public BalanceModel provideBalanceModel() {
        return new BalanceModel();
    }

    @Provides
    public RechargeOrWithdrawPresenter provideRechargeOrWithdrawPresenter(BalanceModel balanceModel) {
        return new RechargeOrWithdrawPresenter(iRechargeOrWithdrawView, balanceModel);
    }

}
