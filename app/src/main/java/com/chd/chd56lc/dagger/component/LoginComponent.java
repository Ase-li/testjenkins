package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.LoginModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.base.LoginActivity;
import com.chd.chd56lc.ui.activity.investment.SecondLoginActivity;
import com.chd.chd56lc.ui.activity.investment.SecondLoginSetPassWordActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);

    void inject(SecondLoginSetPassWordActivity secondLoginSetPassWordActivity);

    void inject(SecondLoginActivity secondLoginActivity);

}
