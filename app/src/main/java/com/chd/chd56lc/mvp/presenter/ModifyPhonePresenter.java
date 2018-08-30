package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.ISafeVerifyView;
import com.chd.chd56lc.mvp.view.ISetPhoneView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class ModifyPhonePresenter extends NetPresenter {
    private ISetPhoneView iSetPhoneView;
    private ISafeVerifyView iSafeVerifyView;
    private UserServerModel userServerModel;

    public ModifyPhonePresenter(ISetPhoneView iSetPhoneView, UserServerModel userServerModel) {
        this.iSetPhoneView = iSetPhoneView;
        this.userServerModel = userServerModel;
    }

    public ModifyPhonePresenter(ISafeVerifyView iSafeVerifyView, UserServerModel userServerModel) {
        this.iSafeVerifyView = iSafeVerifyView;
        this.userServerModel = userServerModel;
    }

    /**
     * 更改手机号第二步
     */
    public void resetPhoneStepTwo(String phone, String newCaptcha) {
        iSetPhoneView.showLoading();
        addSubscription(userServerModel.resetPhoneStepTwo(phone, newCaptcha), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                iSetPhoneView.setPhoneResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iSetPhoneView.setPhoneResult(false);
            }

            @Override
            public void onFinish() {
                iSetPhoneView.dismissLoading();
            }
        });
    }

    /**
     * 更改手机号第一步
     */
    public void resetPhoneStepOne(String newCaptcha) {
        iSafeVerifyView.showLoading();
        addSubscription(userServerModel.resetPhoneStepOne(newCaptcha), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                iSafeVerifyView.verifySuccess();
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onFinish() {
                iSafeVerifyView.dismissLoading();
            }
        });
    }


}
