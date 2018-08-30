package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.AssetAnalyzeBean;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.IAssetAnalyzeView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import javax.inject.Inject;


public class AssetAnalyzePresenter extends NetPresenter {
    private IAssetAnalyzeView iAssetAnalyzeView;
    private AssetModel assetModel;

    @Inject
    public AssetAnalyzePresenter(IAssetAnalyzeView iAssetAnalyzeView, AssetModel assetModel) {
        super();
        this.iAssetAnalyzeView = iAssetAnalyzeView;
        this.assetModel = assetModel;
    }

    /**
     * 资产分析-总资产
     */
    public void assetInvest() {
        iAssetAnalyzeView.showLoading();
        addSubscription(assetModel.assetInvest(), new ApiCallback<AssetAnalyzeBean>() {
            @Override
            public void onSuccess(AssetAnalyzeBean assetAnalyzeBean) {
                iAssetAnalyzeView.updateAssetData(assetAnalyzeBean);
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onFinish() {
                iAssetAnalyzeView.dismissLoading();
            }
        });
    }

    /**
     * 资产分析-收益
     */
    public void assetProfit() {
        iAssetAnalyzeView.showLoading();
        addSubscription(assetModel.assetProfit(), new ApiCallback<AssetAnalyzeBean>() {
            @Override
            public void onSuccess(AssetAnalyzeBean assetAnalyzeBean) {
                iAssetAnalyzeView.updateAssetData(assetAnalyzeBean);
            }

            @Override
            public void onFailure(int code, String msg) {
            }

            @Override
            public void onFinish() {
                iAssetAnalyzeView.dismissLoading();
            }
        });
    }


}
