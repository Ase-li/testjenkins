package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.PushSetBean;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IPushSetView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class PushSetPresenter extends NetPresenter {
    private UserServerModel userServerModel;
    private IPushSetView iPushSetView;

    public PushSetPresenter(IPushSetView iPushSetView, UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
        this.iPushSetView = iPushSetView;
    }

    /**
     * 获取推送设置
     */
    public void getPushSettings() {
        iPushSetView.showLoading();
        addSubscription(userServerModel.getPushSettings(), new ApiCallback<PushSetBean>() {
            @Override
            public void onSuccess(PushSetBean pushSetBean) {
                iPushSetView.updatePush(pushSetBean);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iPushSetView.dismissLoading();
            }
        });
    }

    /**
     * 设置推送设置
     *
     * @param recharge 充值
     * @param withdraw 提现
     * @param invest   投资
     * @param dividend 派息
     * @param remit    回款
     * @param debt     债权转让
     * @param channel  推送渠道 （0-短信 、 1-APP 、 2-微信 、 3-邮件）
     * @param email    邮件
     */
    public void setPushSettings(boolean recharge, boolean withdraw, boolean invest, boolean dividend,
                                boolean remit, boolean debt, int channel, String email) {
        iPushSetView.showLoading();
        addSubscription(userServerModel.setPushSettings(new PushSetBean(channel, debt, dividend, email, invest, recharge, remit, withdraw)), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                iPushSetView.setPushSuccess();
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iPushSetView.dismissLoading();
            }
        });
    }


}
