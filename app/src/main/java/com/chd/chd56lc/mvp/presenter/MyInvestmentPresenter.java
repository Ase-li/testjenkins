package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.SignDebtStatusBean;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.IMyInvestmentView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

/**
 * 债转签约
 */
public class MyInvestmentPresenter extends NetPresenter {
    private AssetModel assetModel;
    private IMyInvestmentView iDebtSignView;

    public MyInvestmentPresenter(IMyInvestmentView iDebtSignView, AssetModel assetModel) {
        this.assetModel = assetModel;
        this.iDebtSignView = iDebtSignView;
    }

    /**
     * 查询债转签约状态
     *
     * @return
     */
    public void getSignDebtStatus() {
        iDebtSignView.showLoading();
        addSubscription(assetModel.getSignDebtStatus(), new ApiCallback<SignDebtStatusBean>() {
            @Override
            public void onSuccess(SignDebtStatusBean model) {
                iDebtSignView.debtSignStatus(model.getStatus());
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onFinish() {
                iDebtSignView.dismissLoading();
            }
        });
    }
}
