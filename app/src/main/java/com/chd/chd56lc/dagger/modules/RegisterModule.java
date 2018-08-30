package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.LoginInfoModel;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.RegisterPresenter;
import com.chd.chd56lc.mvp.view.IRegisterView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class RegisterModule {
    private IRegisterView iRegisterView;

    public RegisterModule(IRegisterView iRegisterView) {
        this.iRegisterView = iRegisterView;
    }

    @Provides
    public LoginInfoModel provideLoginInfoModel() {
        return new LoginInfoModel();
    }

    @Provides
    public RegisterPresenter provideRegisterPresenter(LoginInfoModel loginInfoModel) {
        return new RegisterPresenter(iRegisterView, loginInfoModel);
    }

    @Provides
    public CaptchaPresenter provideCaptchaPresenter(Retrofit retrofit) {
        return new CaptchaPresenter(iRegisterView, retrofit);
    }
}
