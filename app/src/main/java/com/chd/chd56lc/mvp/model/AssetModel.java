package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.AssetAnalyzeBean;
import com.chd.chd56lc.entity.AssetInvestInBean;
import com.chd.chd56lc.entity.AssetInvestOverBean;
import com.chd.chd56lc.entity.AssetTransferBean;
import com.chd.chd56lc.entity.AssetTransferInBean;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.CouponBean;
import com.chd.chd56lc.entity.OrderDetailDetailBean;
import com.chd.chd56lc.entity.OrderDetailInfo;
import com.chd.chd56lc.entity.ProjectListItem;
import com.chd.chd56lc.entity.SignDebtStatusBean;
import com.chd.chd56lc.entity.TransactionRecordBean;
import com.chd.chd56lc.entity.TransactionRecordList;
import com.chd.chd56lc.entity.TransferableBean;
import com.chd.chd56lc.net.api.AssetApi;

import java.util.List;

import io.reactivex.Observable;

public class AssetModel {

    private AssetApi assetApi;

    public AssetModel() {
        assetApi = BaseApplication.getAppComponent().retrofit().create(AssetApi.class);
    }


    /**
     * 我的投资-转让列表
     *
     * @return
     */
    public Observable<BaseBean<List<TransferableBean>>> listCanTransferOrder() {
        return assetApi.listCanTransferOrder();
    }


    /**
     * 我的投资-已完成
     *
     * @return
     */
    public Observable<BaseBean<AssetInvestOverBean>> listFinishOrder(int page, int count, int type) {
        return assetApi.listFinishOrder(page, count, type);
    }


    /**
     * 我的投资-在持
     *
     * @return
     */
    public Observable<BaseBean<List<AssetInvestInBean>>> listMainCurrentOrder() {
        return assetApi.listMainCurrentOrder();
    }


    /**
     * 我的投资-转让中
     *
     * @return
     */
    public Observable<BaseBean<List<AssetTransferInBean>>> listTransferingOrder() {
        return assetApi.listTransferingOrder();
    }

    /**
     * 查询债转签约状态
     *
     * @return
     */
    public Observable<BaseBean<SignDebtStatusBean>> getSignDebtStatus() {
        return assetApi.getSignDebtStatus();
    }


    /**
     * 订单详情
     *
     * @return
     */
    public Observable<BaseBean<ProjectListItem>> orderDetail() {
        return assetApi.orderDetail();
    }


    /**
     * 转让确认
     *
     * @return
     */
    public Observable<BaseBean> transfer(String ids) {
        return assetApi.transfer(ids);
    }

    /**
     * 请求转让列表
     *
     * @return
     */
    public Observable<BaseBean<List<AssetTransferBean>>> transferPageInfo(String ids) {
        return assetApi.transferPageInfo(ids);
    }

    /**
     * 收益详情
     *
     * @param id
     * @return
     */
    public Observable<BaseBean<OrderDetailInfo>> bidDetail(String id) {
        return assetApi.bidDetail(id);
    }

    /**
     * 收益详情
     *
     * @param id
     * @return
     */
    public Observable<BaseBean<List<OrderDetailDetailBean>>> orderDetailRecord(String id) {
        return assetApi.orderDetailRecord(id);
    }

    /**
     * 获取优惠券
     *
     * @param page
     * @param count
     * @param type  0其他,3现金红包,2加息券
     * @return
     */
    public Observable<BaseBean<CouponBean>> getCouponList(int page, int count, int type) {
        return assetApi.paginateByType(page, count, type);
    }

    /**
     * 获取优惠券
     *
     * @param page
     * @param count
     * @param status 状态：1：已使用，2：已过期
     * @return
     */
    public Observable<BaseBean<CouponBean>> paginateByStatus(int page, int count, int status) {
        return assetApi.paginateByStatus(page, count, status);
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
    public Observable<BaseBean<List<CouponBean.Item>>> listAvailable(int page, int count, int scenario, String bidId, int investAmount) {
        return assetApi.listAvailable(page, count, scenario, bidId, investAmount);
    }

    /**
     * 资产分析-总资产
     *
     * @return
     */
    public Observable<BaseBean<AssetAnalyzeBean>> assetInvest() {
        return assetApi.assetInvest();
    }

    /**
     * 资产分析-收益
     *
     * @return
     */
    public Observable<BaseBean<AssetAnalyzeBean>> assetProfit() {
        return assetApi.assetProfit();
    }

    /**
     * 交易明细
     *
     * @param id
     * @return
     */
    public Observable<BaseBean<TransactionRecordBean>> transactionDetail(String id, int type) {
        return assetApi.transactionDetail(id, type);
    }

    /**
     * 交易记录
     *
     * @param page
     * @param count
     * @param type
     * @param year
     * @param month
     * @return
     */
    public Observable<BaseBean<TransactionRecordList>> paginate(int page, int count, Integer type, Integer year, Integer month) {
        return assetApi.paginate(page, count, type, year, month);
    }

    /**
     * 交易记录
     *
     * @param id
     * @return
     */
    public Observable<BaseBean> cancelTransfer(String id) {
        return assetApi.cancelTransfer(id);
    }

}
