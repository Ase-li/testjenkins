package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.DepositoryModel;
import com.chd.chd56lc.mvp.presenter.DebtSignPresenter;
import com.chd.chd56lc.mvp.presenter.ResultDepositPresenter;
import com.chd.chd56lc.mvp.view.IDebtSignView;
import com.chd.chd56lc.mvp.view.IResultDepositView;

import dagger.Module;
import dagger.Provides;

@Module
public class DepositModule {
    private IResultDepositView iResultDepositView;
    private IDebtSignView iDebtSignView;

    public DepositModule(IResultDepositView iResultDepositView) {
        this.iResultDepositView = iResultDepositView;
    }

    public DepositModule(IDebtSignView iDebtSignView) {
        this.iDebtSignView = iDebtSignView;
    }

    @Provides
    public DepositoryModel provideDespositoryResultModel() {
        return new DepositoryModel();
    }

    @Provides
    public ResultDepositPresenter provideResultDepositPresenter(DepositoryModel despositoryResultModel) {
        return new ResultDepositPresenter(despositoryResultModel, iResultDepositView);
    }

    @Provides
    public DebtSignPresenter provideDebtSignPresenter(DepositoryModel despositoryResultModel) {
        return new DebtSignPresenter(iDebtSignView, despositoryResultModel);
    }
}
