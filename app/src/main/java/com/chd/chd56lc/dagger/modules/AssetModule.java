package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.presenter.AssetAnalyzePresenter;
import com.chd.chd56lc.mvp.presenter.AssetInvestPresenter;
import com.chd.chd56lc.mvp.presenter.CouponPresenter;
import com.chd.chd56lc.mvp.presenter.MyInvestmentPresenter;
import com.chd.chd56lc.mvp.presenter.OrderDetailDetailPresenter;
import com.chd.chd56lc.mvp.presenter.OrderDetailPresenter;
import com.chd.chd56lc.mvp.presenter.TransactionRecordPresenter;
import com.chd.chd56lc.mvp.presenter.TransferPresenter;
import com.chd.chd56lc.mvp.view.IAssetAnalyzeView;
import com.chd.chd56lc.mvp.view.IAssetInvestInView;
import com.chd.chd56lc.mvp.view.IAssetInvestOverView;
import com.chd.chd56lc.mvp.view.IAssetTransferInView;
import com.chd.chd56lc.mvp.view.ICouponView;
import com.chd.chd56lc.mvp.view.IMyInvestmentView;
import com.chd.chd56lc.mvp.view.IInvestCouponView;
import com.chd.chd56lc.mvp.view.IInvestmentTransVerifyView;
import com.chd.chd56lc.mvp.view.IOrderDetailDetailView;
import com.chd.chd56lc.mvp.view.IOrderDetailView;
import com.chd.chd56lc.mvp.view.ITransactionRecordDetailView;
import com.chd.chd56lc.mvp.view.ITransactionRecordListView;
import com.chd.chd56lc.mvp.view.ITransferableViewInView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class AssetModule {
    private IAssetInvestInView iAssetInvestInView;
    private IAssetTransferInView iAssetTransferInView;
    private IAssetInvestOverView iAssetInvestOverView;
    private ITransferableViewInView iTransferableViewInView;
    private IInvestmentTransVerifyView iInvestmentTransVerifyView;
    private IOrderDetailView iOrderDetailView;
    private IOrderDetailDetailView iOrderDetailDetailView;
    private ICouponView iCouponView;
    private IAssetAnalyzeView iAssetAnalyzeView;
    private ITransactionRecordListView iTransRecordListView;
    private ITransactionRecordDetailView iTransactionRecordDetailView;
    private IInvestCouponView iInvestCouponView;
    private IMyInvestmentView iDebtSignView;

    public AssetModule(IAssetInvestInView iAssetInvestInView) {
        this.iAssetInvestInView = iAssetInvestInView;
    }

    public AssetModule(IAssetTransferInView iAssetTransferInView) {
        this.iAssetTransferInView = iAssetTransferInView;
    }

    public AssetModule(IAssetInvestOverView iAssetInvestOverView) {
        this.iAssetInvestOverView = iAssetInvestOverView;
    }

    public AssetModule(ITransferableViewInView iTransferableViewInView) {
        this.iTransferableViewInView = iTransferableViewInView;
    }

    public AssetModule(IInvestmentTransVerifyView iInvestmentTransVerifyView) {
        this.iInvestmentTransVerifyView = iInvestmentTransVerifyView;
    }

    public AssetModule(IOrderDetailView iOrderDetailView) {
        this.iOrderDetailView = iOrderDetailView;
    }

    public AssetModule(IOrderDetailDetailView iOrderDetailDetailView) {
        this.iOrderDetailDetailView = iOrderDetailDetailView;
    }

    public AssetModule(ICouponView iCouponView) {
        this.iCouponView = iCouponView;
    }

    public AssetModule(IAssetAnalyzeView iAssetAnalyzeView) {
        this.iAssetAnalyzeView = iAssetAnalyzeView;
    }

    public AssetModule(ITransactionRecordListView iTransRecordListView) {
        this.iTransRecordListView = iTransRecordListView;
    }

    public AssetModule(ITransactionRecordDetailView iTransactionRecordDetailView) {
        this.iTransactionRecordDetailView = iTransactionRecordDetailView;
    }

    public AssetModule(IInvestCouponView iInvestCouponView) {
        this.iInvestCouponView = iInvestCouponView;
    }

    public AssetModule(IMyInvestmentView iDebtSignView) {
        this.iDebtSignView = iDebtSignView;
    }

    @Provides
    public AssetModel provideAssetModel() {
        return new AssetModel();
    }

    @Provides
    public AssetInvestPresenter provideAssetInvestInPresenter(AssetModel assetModel) {
        return new AssetInvestPresenter(iAssetInvestInView, assetModel);
    }

    @Named("transferIn")
    @Provides
    public AssetInvestPresenter provideAssetTransferPresenter(AssetModel assetModel) {
        return new AssetInvestPresenter(iAssetTransferInView, assetModel);
    }

    @Named("investOver")
    @Provides
    public AssetInvestPresenter provideAssetInvestOverPresenter(AssetModel assetModel) {
        return new AssetInvestPresenter(iAssetInvestOverView, assetModel);
    }

    @Provides
    public TransferPresenter provideTransferPresenter(AssetModel assetModel) {
        return new TransferPresenter(iTransferableViewInView, assetModel);
    }

    @Named("transVerify")
    @Provides
    public TransferPresenter provideTransVerify(AssetModel assetModel) {
        return new TransferPresenter(iInvestmentTransVerifyView, assetModel);
    }

    @Provides
    public OrderDetailPresenter provideOrderDetailPresenter(AssetModel assetModel) {
        return new OrderDetailPresenter(iOrderDetailView, assetModel);
    }

    @Provides
    public OrderDetailDetailPresenter provideOrderDetailDetailPresenter(AssetModel assetModel) {
        return new OrderDetailDetailPresenter(iOrderDetailDetailView, assetModel);
    }

    @Named("Coupon")
    @Provides
    public CouponPresenter provideOrderCouponPresenter(AssetModel assetModel) {
        return new CouponPresenter(iCouponView, assetModel);
    }

    @Named("investCoupon")
    @Provides
    public CouponPresenter provideCouponPresenter(AssetModel assetModel) {
        return new CouponPresenter(iInvestCouponView, assetModel);
    }

    @Provides
    public AssetAnalyzePresenter provideOrderAssetAnalyzePresenter(AssetModel assetModel) {
        return new AssetAnalyzePresenter(iAssetAnalyzeView, assetModel);
    }

    @Named("TransactionRecordList")
    @Provides
    public TransactionRecordPresenter provideTransactionRecordListPresenter(AssetModel assetMode) {
        return new TransactionRecordPresenter(iTransRecordListView, assetMode);
    }

    @Named("transactionRecordDetail")
    @Provides
    public TransactionRecordPresenter provideTransactionRecordDetailPresenter(AssetModel assetMode) {
        return new TransactionRecordPresenter(iTransactionRecordDetailView, assetMode);
    }

    @Provides
    public MyInvestmentPresenter provideDebtSignPresenter(AssetModel assetMode) {
        return new MyInvestmentPresenter(iDebtSignView, assetMode);
    }


}
