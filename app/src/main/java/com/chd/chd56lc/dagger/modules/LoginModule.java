package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.LoginInfoModel;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.LoginPresenter;
import com.chd.chd56lc.mvp.view.ILoginView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class LoginModule {
    private ILoginView iLoginView;


    public LoginModule(ILoginView iLoginView) {
        this.iLoginView = iLoginView;
    }

    @Provides
    public LoginInfoModel provideLoginInfoModel() {
        return new LoginInfoModel();
    }

    @Provides
    public LoginPresenter provideLoginPresenter(LoginInfoModel loginInfoModel) {
        return new LoginPresenter(iLoginView, loginInfoModel);
    }

    @Provides
    public CaptchaPresenter provideCaptchaPresenter(Retrofit retrofit) {
        return new CaptchaPresenter(iLoginView, retrofit);
    }
}
