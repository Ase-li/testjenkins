package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.TransactionRecordBean;
import com.chd.chd56lc.entity.TransactionRecordList;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.ITransactionRecordDetailView;
import com.chd.chd56lc.mvp.view.ITransactionRecordListView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class TransactionRecordPresenter extends NetPresenter {
    private ITransactionRecordListView iTransRecordListView;
    private ITransactionRecordDetailView iTransactionRecordDetailView;
    private AssetModel assetModel;

    public TransactionRecordPresenter(ITransactionRecordListView iTransRecordListView, AssetModel assetModel) {
        this.iTransRecordListView = iTransRecordListView;
        this.assetModel = assetModel;
    }

    public TransactionRecordPresenter(ITransactionRecordDetailView iTransactionRecordDetailView, AssetModel assetModel) {
        this.iTransactionRecordDetailView = iTransactionRecordDetailView;
        this.assetModel = assetModel;
    }

    /**
     * 交易明细
     *
     * @param id
     * @return
     */
    public void transactionDetail(String id, int type) {
        iTransactionRecordDetailView.showLoading();
        addSubscription(assetModel.transactionDetail(id, type), new ApiCallback<TransactionRecordBean>() {
            @Override
            public void onSuccess(TransactionRecordBean model) {
                iTransactionRecordDetailView.updateTransRecordDetail(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iTransactionRecordDetailView.dismissLoading();
            }
        });
    }

    /**
     * 交易记录
     *
     * @param page
     * @param count
     * @param type  交易类型；1：充值，2：提现，3：收益，4：回款，5：现金奖励，6：投资
     * @param year
     * @param month
     * @return
     */
    public void paginate(int page, int count, Integer type, Integer year, Integer month) {
//        iTransRecordListView.showLoading();
        addSubscription(assetModel.paginate(page, count, type, year, month), new ApiCallback<TransactionRecordList>() {
            @Override
            public void onSuccess(TransactionRecordList model) {
                iTransRecordListView.updateTransRecordList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iTransRecordListView.updateTransRecordList(null);
            }

            @Override
            public void onFinish() {
                iTransRecordListView.dismissLoading();
            }
        });
    }
}
