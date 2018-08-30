package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.model.LoginInfoModel;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IPersonSetView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class PersonSetPresenter extends NetPresenter {
    private UserServerModel userServerModel;
    private IPersonSetView iPersonSetView;
    private LoginInfoModel loginInfoModel;

    public PersonSetPresenter(IPersonSetView iPersonSetView, UserServerModel userServerModel, LoginInfoModel loginInfoModel) {
        this.userServerModel = userServerModel;
        this.iPersonSetView = iPersonSetView;
        this.loginInfoModel = loginInfoModel;
    }

    /**
     * 重置交易密码
     */
    public void resetPassword() {
        iPersonSetView.showLoading();
        addSubscription(userServerModel.resetPassword(), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean depositLinkBean) {
                iPersonSetView.resetPasswordResult(depositLinkBean);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iPersonSetView.dismissLoading();
            }
        });
    }

    /**
     * 退出登录
     */
    public void loginOut() {
        iPersonSetView.showLoading();
        addSubscription(loginInfoModel.logOut(), new ApiCallback<Object>() {

            @Override
            public void onSuccess(Object model) {
                iPersonSetView.loginOutResult(true);
                UserManager.getInstance().saveLogin(false);
                UserManager.getInstance().saveUserInfo(null);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iPersonSetView.dismissLoading();
            }
        });
    }

    /**
     * 开启手势
     */
    public void setIfGesture(boolean isOpen) {
        iPersonSetView.showLoading();
        addSubscription(userServerModel.setIfGesture(isOpen), new ApiCallback<Object>() {

            @Override
            public void onSuccess(Object model) {
                iPersonSetView.openGestureResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iPersonSetView.openGestureResult(false);
            }

            @Override
            public void onFinish() {
                iPersonSetView.dismissLoading();
            }
        });
    }

    /**
     * 开启指纹
     */
    public void setIfTouchID(boolean isOpen) {
        iPersonSetView.showLoading();
        addSubscription(userServerModel.setIfTouchID(isOpen), new ApiCallback<Object>() {

            @Override
            public void onSuccess(Object model) {
                iPersonSetView.openTouchIdResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iPersonSetView.openTouchIdResult(false);
            }

            @Override
            public void onFinish() {
                iPersonSetView.dismissLoading();
            }
        });
    }
}
