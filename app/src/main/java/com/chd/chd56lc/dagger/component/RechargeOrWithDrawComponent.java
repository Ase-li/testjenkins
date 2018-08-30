package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.RechargeOrWithdrawModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.personCenter.RechargeActivity;
import com.chd.chd56lc.ui.activity.personCenter.WithdrawDepositActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = RechargeOrWithdrawModule.class)
public interface RechargeOrWithDrawComponent {
    void inject(RechargeActivity rechargeActivity);

    void inject(WithdrawDepositActivity withdrawDepositActivity);
}
