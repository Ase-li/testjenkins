package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BankInfoOrBalanceBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IMyBankInfoView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;
import com.chd.chd56lc.utils.StringUtils;

import javax.inject.Inject;


public class BankInfoPresenter extends NetPresenter {
    private IMyBankInfoView iMyBankInfoView;
    private UserServerModel userServerModel;

    @Inject
    public BankInfoPresenter(IMyBankInfoView iMyBankInfoView, UserServerModel userServerModel) {
        super();
        this.iMyBankInfoView = iMyBankInfoView;
        this.userServerModel = userServerModel;
    }

    /**
     * 绑定银行卡
     */
    public void bindBankCard() {
        iMyBankInfoView.showLoading();
        addSubscription(userServerModel.bindBankCard(), new ApiCallback<DepositLinkBean>() {
            @Override
            public void onSuccess(DepositLinkBean depositLinkBean) {
                iMyBankInfoView.bindBankCard(depositLinkBean);
            }

            @Override
            public void onFailure(int code, String msg) {
                iMyBankInfoView.bindBankCard(null);
            }

            @Override
            public void onFinish() {
                iMyBankInfoView.dismissLoading();
            }
        });
    }

    /**
     * 解除绑定银行卡
     */
    public void unbindBankCard(String phone, String captcha) {
        iMyBankInfoView.showLoading();
        if (StringUtils.isEmpty(captcha)) {
            BaseApplication.getAppComponent().customToast().setText("请输入验证码");
            return;
        }
        addSubscription(userServerModel.unbindBankCard(phone, captcha), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object object) {
                iMyBankInfoView.unbindBankCard();
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iMyBankInfoView.dismissLoading();
            }
        });
    }

    /**
     * 获取用户银行限额信息
     */
    public void getBankLimitInfo() {
        iMyBankInfoView.showLoading();
        addSubscription(userServerModel.getBankLimitInfo(), new ApiCallback<BankInfoOrBalanceBean>() {
            @Override
            public void onSuccess(BankInfoOrBalanceBean model) {
                iMyBankInfoView.updateBankInfo(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iMyBankInfoView.dismissLoading();
            }
        });
    }


}
