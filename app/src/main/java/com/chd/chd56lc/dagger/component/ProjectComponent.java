package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.ProjectModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.investment.InvestmentActivity;
import com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity;
import com.chd.chd56lc.ui.fragment.base.ProjectFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = ProjectModule.class)
public interface ProjectComponent {
    void inject(ProjectFragment projectFragment);

    void inject(InvestmentDetailActivity investmentDetailActivity);

    void inject(InvestmentActivity investmentActivity);
}
