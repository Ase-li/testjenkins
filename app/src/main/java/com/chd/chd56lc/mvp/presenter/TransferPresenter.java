package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.AssetTransferBean;
import com.chd.chd56lc.entity.TransferableBean;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.IInvestmentTransVerifyView;
import com.chd.chd56lc.mvp.view.ITransferableViewInView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

//转让presenter
public class TransferPresenter extends NetPresenter {
    private ITransferableViewInView iAssetInvestInView;
    private IInvestmentTransVerifyView iInvestmentTransVerifyView;
    private AssetModel assetModel;

    public TransferPresenter(ITransferableViewInView iAssetInvestInView, AssetModel assetModel) {
        this.iAssetInvestInView = iAssetInvestInView;
        this.assetModel = assetModel;
    }

    public TransferPresenter(IInvestmentTransVerifyView iInvestmentTransVerifyView, AssetModel assetModel) {
        this.iInvestmentTransVerifyView = iInvestmentTransVerifyView;
        this.assetModel = assetModel;
    }

    /**
     * 可转让列表
     */
    public void listMainCurrentOrder() {
        iAssetInvestInView.showLoading();
        addSubscription(assetModel.listCanTransferOrder(), new ApiCallback<List<TransferableBean>>() {
            @Override
            public void onSuccess(List<TransferableBean> model) {
                iAssetInvestInView.setTransferableList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iAssetInvestInView.setTransferableList(null);
            }

            @Override
            public void onFinish() {
                iAssetInvestInView.dismissLoading();
            }
        });
    }

    /**
     * 转让确认列表
     */
    public void transferPageInfo(String ids) {
        iInvestmentTransVerifyView.showLoading();
        addSubscription(assetModel.transferPageInfo(ids), new ApiCallback<List<AssetTransferBean>>() {
            @Override
            public void onSuccess(List<AssetTransferBean> model) {
                iInvestmentTransVerifyView.setTransferPageInfo(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iInvestmentTransVerifyView.setTransferPageInfo(null);
            }

            @Override
            public void onFinish() {
                iInvestmentTransVerifyView.dismissLoading();
            }
        });
    }

    /**
     * 转让确认
     */
    public void transfer(String ids) {
        iInvestmentTransVerifyView.showLoading();
        addSubscription(assetModel.transfer(ids), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                iInvestmentTransVerifyView.transferVerify();
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iInvestmentTransVerifyView.dismissLoading();
            }
        });
    }


}
