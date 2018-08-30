package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.DepositoryResultBean;
import com.chd.chd56lc.entity.SignDebtStatusBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DepositoryApi {
    //提现成功预扣
    @FormUrlEncoded
    @POST("api/service-depository/app/bankroll/preDeductLocalAmount")
    Observable<BaseBean<DepositoryResultBean>> preDeductLocalAmount(@Field("orderId") String orderId);

    //充值成功,更新订单状态
    @FormUrlEncoded
    @POST("api/service-depository/app/bankroll/updateOrderStatus")
    Observable<BaseBean<DepositoryResultBean>> updateOrderStatus(@Field("orderId") String orderId);

    //购买债权成功重定向页面
    @FormUrlEncoded
    @POST("api/service-depository/app/invest/buyCreditPSuccessPage")
    Observable<BaseBean> buyCreditPSuccessPage(@Field("orderId") String orderId);

    //投资成功重定向页面
    @FormUrlEncoded
    @POST("api/service-depository/app/invest/bidApplyPSuccessPage")
    Observable<BaseBean> bidApplyPSuccessPage(@Field("orderId") String orderId);

    //进行债券转让签约
    @POST("api/service-depository/app/invest/signTransferP")
    Observable<BaseBean<DepositLinkBean>> signTransferP();

    //修改债转签约状态为处理中
    @POST("api/service-depository/app/invest/updateSignDebtStatus")
    Observable<BaseBean> updateSignDebtStatus();

    /**
     * 查询债转签约状态
     *
     * @return
     */
    @GET("api/service-depository/app/invest/getSignDebtStatus")
    Observable<BaseBean<SignDebtStatusBean>> getSignDebtStatus();

}
