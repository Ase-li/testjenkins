package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.OrderDetailDetailBean;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.IOrderDetailDetailView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;
import com.chd.chd56lc.utils.Logger;

import java.util.List;

import javax.inject.Inject;


public class OrderDetailDetailPresenter extends NetPresenter {
    private IOrderDetailDetailView iOrderDetailDetailView;
    private AssetModel assetModel;

    @Inject
    public OrderDetailDetailPresenter(IOrderDetailDetailView iOrderDetailDetailView, AssetModel assetModel) {
        super();
        this.iOrderDetailDetailView = iOrderDetailDetailView;
        this.assetModel = assetModel;
    }

    /**
     * 订单详情
     *
     * @param orderId
     */
    public void orderDetailRecord(String orderId) {
        iOrderDetailDetailView.showLoading();
        addSubscription(assetModel.orderDetailRecord(orderId), new ApiCallback<List<OrderDetailDetailBean>>() {
            @Override
            public void onSuccess(List<OrderDetailDetailBean> orderDetailDetailBean) {
                iOrderDetailDetailView.setOrderDetailDetail(orderDetailDetailBean);
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d("onFailure_测试" + msg);
            }

            @Override
            public void onFinish() {
                iOrderDetailDetailView.dismissLoading();
            }
        });
    }


}
