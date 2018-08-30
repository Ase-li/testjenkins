package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.ICreateGestureView;
import com.chd.chd56lc.mvp.view.ILoginGestureView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class GesturePresenter extends NetPresenter {
    private ILoginGestureView iLoginGestureView;
    private ICreateGestureView iCreateGestureView;
    private UserServerModel userServerModel;

    public GesturePresenter(ICreateGestureView iCreateGestureView, UserServerModel userServerModel) {
        this.iCreateGestureView = iCreateGestureView;
        this.userServerModel = userServerModel;
    }

    public GesturePresenter(ILoginGestureView iLoginGestureView, UserServerModel userServerModel) {
        this.iLoginGestureView = iLoginGestureView;
        this.userServerModel = userServerModel;
    }

    /**
     * 设置手势密码
     *
     * @param gesture
     * @param confirmGesture
     */
    public void setGesture(String gesture, String confirmGesture) {
        iCreateGestureView.showLoading();
        addSubscription(userServerModel.setGesture(gesture, confirmGesture), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                iCreateGestureView.setLockPatternResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iCreateGestureView.setLockPatternResult(false);
            }

            @Override
            public void onFinish() {
                iCreateGestureView.dismissLoading();
            }
        });
    }

    /**
     * 校验手势密码
     *
     * @param gesture
     */
    public void verifyGesture(String gesture) {
        iLoginGestureView.showLoading();
        addSubscription(userServerModel.verifyGesture(gesture), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                iLoginGestureView.loginGestureResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iLoginGestureView.loginGestureResult(false);
            }

            @Override
            public void onFinish() {
                iLoginGestureView.dismissLoading();
            }
        });
    }
}
