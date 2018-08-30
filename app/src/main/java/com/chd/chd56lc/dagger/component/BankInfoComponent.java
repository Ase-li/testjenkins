package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.BankInfoModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.mvp.presenter.UnbindBankActivity;
import com.chd.chd56lc.ui.activity.personCenter.MyBankInfoActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = BankInfoModule.class)
public interface BankInfoComponent {
    void inject(MyBankInfoActivity myBankInfoActivity);
    void inject(UnbindBankActivity unbindBankActivity);

}
