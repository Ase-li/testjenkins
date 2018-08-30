package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.DepositoryResultBean;
import com.chd.chd56lc.entity.SignDebtStatusBean;
import com.chd.chd56lc.mvp.model.DepositoryModel;
import com.chd.chd56lc.mvp.view.IResultDepositView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class ResultDepositPresenter extends NetPresenter {
    private DepositoryModel despositoryResultModel;
    private IResultDepositView iResultDepositView;

    public ResultDepositPresenter(DepositoryModel despositoryResultModel, IResultDepositView iResultDepositView) {
        this.despositoryResultModel = despositoryResultModel;
        this.iResultDepositView = iResultDepositView;
    }

    /**
     * 充值成功更新订单状态
     *
     * @param orderId
     * @return
     */
    public void updateOrderStatus(String orderId) {
        addSubscription(despositoryResultModel.updateOrderStatus(orderId), new ApiCallback<DepositoryResultBean>() {
            @Override
            public void onSuccess(DepositoryResultBean model) {
                iResultDepositView.rechargeResult(model.getStatusCode());
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 提现预扣
     *
     * @param orderId
     * @return
     */
    public void preDeductLocalAmount(String orderId) {
        addSubscription(despositoryResultModel.preDeductLocalAmount(orderId), new ApiCallback<DepositoryResultBean>() {
            @Override
            public void onSuccess(DepositoryResultBean model) {
                iResultDepositView.withDrawResult(model.getStatusCode());
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 投资成功重定向页面
     *
     * @param orderId
     * @return
     */
    public void bidApplyPSuccessPage(String orderId) {
        addSubscription(despositoryResultModel.bidApplyPSuccessPage(orderId), new ApiCallback<DepositoryResultBean>() {
            @Override
            public void onSuccess(DepositoryResultBean model) {
//                if (model.getStatusCode() == 1) {
//                    iResultDepositView.investmentResult(true);
//                } else {
//                    iResultDepositView.investmentResult(false);
//                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 购买债权成功重定向页面
     *
     * @param orderId
     * @return
     */
    public void buyCreditPSuccessPage(String orderId) {
        addSubscription(despositoryResultModel.buyCreditPSuccessPage(orderId), new ApiCallback<DepositoryResultBean>() {
            @Override
            public void onSuccess(DepositoryResultBean model) {
//                if (model.getStatusCode() == 1) {
//                    iResultDepositView.transferResult(true);
//                } else {
//                    iResultDepositView.transferResult(false);
//                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 修改债转签约状态为处理中
     *
     * @return
     */
    public void updateSignDebtStatus() {
        addSubscription(despositoryResultModel.updateSignDebtStatus(), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                iResultDepositView.updateDebtStatus();
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    /**
     * 查询债转签约状态
     *
     * @return
     */
    public void getSignDebtStatus() {
        addSubscription(despositoryResultModel.getSignDebtStatus(), new ApiCallback<SignDebtStatusBean>() {
            @Override
            public void onSuccess(SignDebtStatusBean model) {
                iResultDepositView.debtStatus(model.getStatus());
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }
}
