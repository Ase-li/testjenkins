package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.OrderDetailInfo;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.IOrderDetailView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;
import com.chd.chd56lc.utils.Logger;

import javax.inject.Inject;


public class OrderDetailPresenter extends NetPresenter {
    private IOrderDetailView iOrderDetailView;
    private AssetModel assetModel;

    @Inject
    public OrderDetailPresenter(IOrderDetailView iOrderDetailView, AssetModel assetModel) {
        super();
        this.iOrderDetailView = iOrderDetailView;
        this.assetModel = assetModel;
    }

    /**
     * 订单详情
     *
     * @param orderId
     */
    public void bidDetail(String orderId) {
        iOrderDetailView.showLoading();
        addSubscription(assetModel.bidDetail(orderId), new ApiCallback<OrderDetailInfo>() {
            @Override
            public void onSuccess(OrderDetailInfo periodOrderDetailInfo) {
                iOrderDetailView.setOrderDetail(periodOrderDetailInfo);
            }

            @Override
            public void onFailure(int code, String msg) {
                Logger.d("onFailure_测试" + msg);
            }

            @Override
            public void onFinish() {
                iOrderDetailView.dismissLoading();
            }
        });
    }


}
