package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.OperateModel;
import com.chd.chd56lc.mvp.model.ProjectModel;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.presenter.FindPresenter;
import com.chd.chd56lc.mvp.presenter.HomePresenter;
import com.chd.chd56lc.mvp.view.IFindView;
import com.chd.chd56lc.mvp.view.IHomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class OperateModule {
    private IHomeView homeView;
    private IFindView iFindView;

    public OperateModule(IHomeView homeView) {
        this.homeView = homeView;
    }

    public OperateModule(IFindView iFindView) {
        this.iFindView = iFindView;
    }

    @Provides
    public OperateModel provideOperateModel() {
        return new OperateModel();
    }

    @Provides
    public ProjectModel provideProjectModel() {
        return new ProjectModel();
    }

    @Provides
    public UserServerModel provideUserServerModel() {
        return new UserServerModel();
    }

    @Provides
    public HomePresenter provideLoginPresenter(OperateModel operateModel, ProjectModel projectModel, UserServerModel userServerModel) {
        return new HomePresenter(homeView, operateModel, projectModel, userServerModel);
    }

    @Provides
    public FindPresenter provideFindPresenter(OperateModel operateModel) {
        return new FindPresenter(iFindView, operateModel);
    }

}
