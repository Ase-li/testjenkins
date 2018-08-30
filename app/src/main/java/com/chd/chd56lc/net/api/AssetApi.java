package com.chd.chd56lc.net.api;

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

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AssetApi {

    /**
     * 我的投资-转让
     *
     * @return
     */
    @GET("api/service-depository/app/order/listCanTransferOrder")
    Observable<BaseBean<List<TransferableBean>>> listCanTransferOrder();

    /**
     * 我的投资-已完成
     *
     * @return
     */
    @GET("api/service-depository/app/order/paginateFinishOrder")
    Observable<BaseBean<AssetInvestOverBean>> listFinishOrder(@Query("page") int page, @Query("count") int count, @Query("type") int type);

    /**
     * 我的投资-在持
     *
     * @return
     */
    @GET("api/service-depository/app/order/listMainCurrentOrder")
    Observable<BaseBean<List<AssetInvestInBean>>> listMainCurrentOrder();

    /**
     * 我的投资-转让中
     *
     * @return
     */
    @GET("api/service-depository/app/order/listTransferingOrder")
    Observable<BaseBean<List<AssetTransferInBean>>> listTransferingOrder();

    /**
     * 查询债转签约状态
     *
     * @return
     */
    @GET("api/service-depository/app/invest/getSignDebtStatus")
    Observable<BaseBean<SignDebtStatusBean>> getSignDebtStatus();


    /**
     * 订单详情
     *
     * @return
     */
    @GET("api/service-depository/app/order/orderDetail")
    Observable<BaseBean<ProjectListItem>> orderDetail();

    /**
     * 转让确认
     *
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-depository/app/order/transfer")
    Observable<BaseBean> transfer(@Field("idsJson") String idsJson);

    /**
     * 转让确认页面信息
     *
     * @return
     */
    @GET("api/service-depository/app/order/transferPageInfo")
    Observable<BaseBean<List<AssetTransferBean>>> transferPageInfo(@Query("idsJson") String idsJson);

    /**
     * 订单列表
     *
     * @param id
     * @return
     */
    @GET("api/service-depository/app/order/orderDetail")
    Observable<BaseBean<OrderDetailInfo>> bidDetail(@Query("id") String id);

    /**
     * 标的详情
     *
     * @param id
     * @return
     */
    @GET("api/service-depository/app/order/orderDetailRecord")
    Observable<BaseBean<List<OrderDetailDetailBean>>> orderDetailRecord(@Query("id") String id);

    /**
     * 获取优惠券
     *
     * @param page
     * @param count
     * @param type  类型，1：抵扣红包 2：加息券，3：其他
     * @return
     */
    @GET("api/service-depository/app/awardUser/paginateByType")
    Observable<BaseBean<CouponBean>> paginateByType(@Query("page") int page, @Query("count") int count, @Query("type") int type);

    /**
     * 获取优惠券
     *
     * @param page
     * @param count
     * @param status 状态：1：已使用，2：已过期
     * @return
     */
    @GET("api/service-depository/app/awardUser/paginateByStatus")
    Observable<BaseBean<CouponBean>> paginateByStatus(@Query("page") int page, @Query("count") int count, @Query("status") int status);

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
    @GET("api/service-depository/app/awardUser/listAvailable")
    Observable<BaseBean<List<CouponBean.Item>>> listAvailable(@Query("page") int page, @Query("count") int count, @Query("scenario") int scenario, @Query("bidId") String bidId, @Query("investAmount") int investAmount);

    /**
     * 资产分析-总资产
     */
    @GET("api/service-depository/app/transaction/assetInvest")
    Observable<BaseBean<AssetAnalyzeBean>> assetInvest();

    /**
     * 资产分析-收益
     */
    @GET("api/service-depository/app/transaction/assetProfit")
    Observable<BaseBean<AssetAnalyzeBean>> assetProfit();

    /**
     * 交易明细
     *
     * @param type 交易类型；1：充值，2：提现，3：收益，4：回款，5：现金奖励，6：投资
     * @param id
     * @return
     */
    @GET("api/service-depository/app/transaction/detail")
    Observable<BaseBean<TransactionRecordBean>> transactionDetail(@Query("orderId") String id, @Query("type") int type);

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
    @GET("api/service-depository/app/transaction/paginate")
    Observable<BaseBean<TransactionRecordList>> paginate(@Query("page") int page, @Query("count") int count, @Query("type") Integer type, @Query("year") Integer year, @Query("month") Integer month);

    /**
     * 交易记录
     *
     * @param id
     * @return
     */
    @FormUrlEncoded
    @POST("/api/service-depository/app/order/cancelTransfer")
    Observable<BaseBean> cancelTransfer(@Field("id") String id);

}
