package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.OperateModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.fragment.base.FindFragment;
import com.chd.chd56lc.ui.fragment.base.HomeFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = OperateModule.class)
public interface OperateComponent {
    void inject(HomeFragment homeFragment);

    void inject(FindFragment findFragment);
}
