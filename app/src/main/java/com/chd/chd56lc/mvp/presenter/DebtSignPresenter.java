package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.SignDebtStatusBean;
import com.chd.chd56lc.mvp.model.DepositoryModel;
import com.chd.chd56lc.mvp.view.IDebtSignView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class DebtSignPresenter extends NetPresenter {
    private IDebtSignView iDebtSignView;
    private DepositoryModel depositoryModel;

    public DebtSignPresenter(IDebtSignView iDebtSignView, DepositoryModel depositoryModel) {
        this.iDebtSignView = iDebtSignView;
        this.depositoryModel = depositoryModel;
    }

    /**
     * 进行债券转让签约
     *
     * @return
     */
    public void signTransferP() {
        iDebtSignView.showLoading();
        addSubscription(depositoryModel.signTransferP(), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean model) {
                iDebtSignView.debtSignStatus(model);
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
