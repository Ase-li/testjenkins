package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.DepositModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.circum.ResultDepositActivity;
import com.chd.chd56lc.ui.activity.investment.DebtSignActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = DepositModule.class)
public interface DepositComponent {

    void inject(ResultDepositActivity resultDepositActivity);

    void inject(DebtSignActivity debtSignActivity);

}
