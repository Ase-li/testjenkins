package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.RegisterModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.personCenter.RegisterActivity;
import com.chd.chd56lc.ui.activity.investment.RegisterCheckVerifyActivity;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = RegisterModule.class)
public interface RegisterComponent {

    void inject(RegisterCheckVerifyActivity registerCheckVerifyActivity);
    void inject(RegisterActivity registerActivity);
}
