package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.CouponBean;
import com.chd.chd56lc.mvp.model.AssetModel;
import com.chd.chd56lc.mvp.view.ICouponView;
import com.chd.chd56lc.mvp.view.IInvestCouponView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

public class CouponPresenter extends NetPresenter {
    private AssetModel assetModel;
    private ICouponView iCouponView;
    private IInvestCouponView iInvestCouponView;

    public CouponPresenter(ICouponView iCouponView, AssetModel assetModel) {
        this.assetModel = assetModel;
        this.iCouponView = iCouponView;
    }

    public CouponPresenter(IInvestCouponView iInvestCouponView, AssetModel assetModel) {
        this.assetModel = assetModel;
        this.iInvestCouponView = iInvestCouponView;
    }

    /**
     * 获取优惠券
     *
     * @param page
     * @param count
     * @param type  类型，1：抵扣红包 2：加息券，3：其他
     * @return
     */
    public void getCouponList(int page, int count, int type) {
//        iCouponView.showLoading();
        addSubscription(assetModel.getCouponList(page, count, type), new ApiCallback<CouponBean>() {
            @Override
            public void onSuccess(CouponBean model) {
                iCouponView.updateCouponList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iCouponView.updateCouponList(null);
            }

            @Override
            public void onFinish() {
                iCouponView.dismissLoading();
            }
        });
    }

    /**
     * 获取优惠券
     *
     * @param page
     * @param count
     * @param status 状态：1：已使用，2：已过期
     * @return
     */
    public void paginateByStatus(int page, int count, int status) {
//        iCouponView.showLoading();
        addSubscription(assetModel.paginateByStatus(page, count, status), new ApiCallback<CouponBean>() {
            @Override
            public void onSuccess(CouponBean model) {
                iCouponView.updateCouponList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iCouponView.updateCouponList(null);
            }

            @Override
            public void onFinish() {
                iCouponView.dismissLoading();
            }
        });
    }

    /**
     * 获取使用优惠券
     *
     * @param page
     * @param count
     * @param scenario     场景：1：购买无忧产品，2：购买债转
     * @param bidId        标的id
     * @param investAmount 购买金额
     * @return
     */
    public void listAvailable(int page, int count, int scenario, String bidId, int investAmount) {
        iInvestCouponView.showLoading();
        addSubscription(assetModel.listAvailable(page, count, scenario, bidId, investAmount), new ApiCallback<List<CouponBean.Item>>() {
            @Override
            public void onSuccess(List<CouponBean.Item> model) {
                iInvestCouponView.updateCouponList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iInvestCouponView.updateCouponList(null);
            }

            @Override
            public void onFinish() {
                iInvestCouponView.dismissLoading();
            }
        });
    }
}
