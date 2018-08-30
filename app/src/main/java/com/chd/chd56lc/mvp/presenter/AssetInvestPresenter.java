package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.AssetInvestInBean;
import com.chd.chd56lc.entity.AssetInvestOverBean;
import com.chd.chd56lc.entity.AssetTransferInBean;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.IAssetInvestInView;
import com.chd.chd56lc.mvp.view.IAssetInvestOverView;
import com.chd.chd56lc.mvp.view.IAssetTransferInView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

public class AssetInvestPresenter extends NetPresenter {
    private IAssetInvestInView iAssetInvestInView;
    private IAssetTransferInView iAssetTransferInView;
    private IAssetInvestOverView iAssetInvestOverView;
    private AssetModel assetModel;

    //已完成
    public AssetInvestPresenter(IAssetInvestOverView iAssetInvestOverView, AssetModel assetModel) {
        this.iAssetInvestOverView = iAssetInvestOverView;
        this.assetModel = assetModel;
    }

    //在持
    public AssetInvestPresenter(IAssetInvestInView iAssetInvestInView, AssetModel assetModel) {
        this.iAssetInvestInView = iAssetInvestInView;
        this.assetModel = assetModel;
    }

    //转让中
    public AssetInvestPresenter(IAssetTransferInView iAssetTransferInView, AssetModel assetModel) {
        this.iAssetTransferInView = iAssetTransferInView;
        this.assetModel = assetModel;
    }

    /**
     * 我的资产在持
     */
    public void listMainCurrentOrder() {
//        iAssetInvestInView.showLoading();
        addSubscription(assetModel.listMainCurrentOrder(), new ApiCallback<List<AssetInvestInBean>>() {
            @Override
            public void onSuccess(List<AssetInvestInBean> model) {
                iAssetInvestInView.setAssetInvestIn(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iAssetInvestInView.setAssetInvestIn(null);
            }

            @Override
            public void onFinish() {
                iAssetInvestInView.dismissLoading();
            }
        });
    }

    /**
     * 我的资产已完成
     *
     * @param page
     * @param count
     * @param type
     */
    public void listFinishOrder(int page, int count, int type) {
//        iAssetInvestOverView.showLoading();
        addSubscription(assetModel.listFinishOrder(page, count, type), new ApiCallback<AssetInvestOverBean>() {
            @Override
            public void onSuccess(AssetInvestOverBean model) {
                iAssetInvestOverView.setAssetInvestOver(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iAssetInvestOverView.setAssetInvestOver(null);
            }

            @Override
            public void onFinish() {
                iAssetInvestOverView.dismissLoading();
            }
        });
    }

    /**
     * 转让中
     */
    public void listTransferingOrder() {
//        iAssetTransferInView.showLoading();
        addSubscription(assetModel.listTransferingOrder(), new ApiCallback<List<AssetTransferInBean>>() {
            @Override
            public void onSuccess(List<AssetTransferInBean> model) {
                iAssetTransferInView.setAssetTransferIn(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iAssetTransferInView.setAssetTransferIn(null);
            }

            @Override
            public void onFinish() {
                iAssetTransferInView.dismissLoading();
            }
        });
    }

    /**
     * 取消转让
     */
    public void cancelTransfer(String id) {
        iAssetTransferInView.showLoading();
        addSubscription(assetModel.cancelTransfer(id), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                iAssetTransferInView.cancelResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iAssetTransferInView.cancelResult(false);
            }

            @Override
            public void onFinish() {
                iAssetTransferInView.dismissLoading();
            }
        });
    }


}
