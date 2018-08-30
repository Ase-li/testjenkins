package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.ProjectModel;
import com.chd.chd56lc.mvp.presenter.InvestmentDetailPresenter;
import com.chd.chd56lc.mvp.presenter.InvestmentPresenter;
import com.chd.chd56lc.mvp.presenter.ProjectFragmentPresenter;
import com.chd.chd56lc.mvp.view.IInvestmentDetailView;
import com.chd.chd56lc.mvp.view.IInvestmentView;
import com.chd.chd56lc.mvp.view.IProjectFragmentView;

import dagger.Module;
import dagger.Provides;

@Module
public class ProjectModule {
    private IProjectFragmentView iProjectFragmentView;
    private IInvestmentDetailView iInvestmentDetailView;
    private IInvestmentView iInvestmentView;

    public ProjectModule(IProjectFragmentView iProjectFragmentView) {
        this.iProjectFragmentView = iProjectFragmentView;
    }

    public ProjectModule(IInvestmentDetailView iInvestmentView) {
        this.iInvestmentDetailView = iInvestmentView;
    }

    public ProjectModule(IInvestmentView iInvestmentView) {
        this.iInvestmentView = iInvestmentView;
    }

    @Provides
    public ProjectModel provideProjectModel() {
        return new ProjectModel();
    }

    @Provides
    public ProjectFragmentPresenter provideProjectFragmentPresenter(ProjectModel projectModel) {
        return new ProjectFragmentPresenter(iProjectFragmentView, projectModel);
    }

    @Provides
    public InvestmentDetailPresenter provideInvestmentDetailPresenter(ProjectModel projectModel) {
        return new InvestmentDetailPresenter(iInvestmentDetailView, projectModel);
    }
    @Provides
    public InvestmentPresenter provideInvestmentPresenter(ProjectModel projectModel) {
        return new InvestmentPresenter(iInvestmentView, projectModel);
    }
}
