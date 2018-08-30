package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.presenter.BankInfoPresenter;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.view.IMyBankInfoView;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class BankInfoModule {
    private IMyBankInfoView iMyBankInfoView;

    public BankInfoModule(IMyBankInfoView iMyBankInfoView) {
        this.iMyBankInfoView = iMyBankInfoView;
    }

    @Provides
    public UserServerModel provideUserServerModel() {
        return new UserServerModel();
    }

    @Provides
    public BankInfoPresenter provideBankInfoPresenter(UserServerModel userServerModel) {
        return new BankInfoPresenter(iMyBankInfoView, userServerModel);
    }

    @Provides
    public CaptchaPresenter provideCaptchaPresenter(Retrofit retrofit) {
        return new CaptchaPresenter(iMyBankInfoView, retrofit);
    }
}
