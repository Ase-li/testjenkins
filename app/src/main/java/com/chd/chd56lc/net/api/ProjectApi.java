package com.chd.chd56lc.net.api;

import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.CouponNum;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.InvestDetail;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.entity.ProjectListItem;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ProjectApi {
    //首页推荐
    @GET("api/service-depository/app/project/getHomeRecommendBid")
    Observable<BaseBean<ProjectDetailBean>> homeRecommendBid();

    //项目详情
    @GET("api/service-depository/app/project/detail")
    Observable<BaseBean<ProjectDetailBean>> projectDetail(@Query("id") String id, @Query("type") long type);

    /**
     * 项目列表
     *
     * @param page  页码
     * @param count 页数
     * @param type  类型；1：无忧投，2：债权转让
     * @return
     */
    @GET("api/service-depository/app/project/list")
    Observable<BaseBean<ProjectListItem>> projectList(@Query("page") int page, @Query("count") int count, @Query("type") int type);

    /**
     * 投资页-详情
     *
     * @param id
     * @return
     */
    @GET("api/service-depository/app/project/detailInvest")
    Observable<BaseBean<InvestDetail>> detailInvest(@Query("id") String id, @Query("type") int type);


    /**
     * 投资人投标申请 (对应，季无忧与年无忧的投资)
     * 玩蛇
     *
     * @param bidId
     * @param couponId
     * @param amount
     * @param orderComefrom 订单来源；1：android，2：ios，3：web
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-depository/app/invest/bidApplyP")
    Observable<BaseBean<DepositLinkBean>> bidApplyP(@Field("bidId") String bidId, @Field("couponId") String couponId, @Field("amount") double amount, @Field("orderComefrom") int orderComefrom);

    /**
     * 购买债权
     * 玩蛇
     *
     * @return
     */
    /**
     * @param debtId
     * @param couponId
     * @param amount
     * @param orderComefrom 订单来源；1：android，2：ios，3：web
     * @return
     */
    @FormUrlEncoded
    @POST("api/service-depository/app/invest/buyCreditP")
    Observable<BaseBean<DepositLinkBean>> buyCreditP(@Field("debtId") String debtId, @Field("couponId") String couponId, @Field("amount") double amount, @Field("orderComefrom") int orderComefrom);

    /**
     * 获取优惠券数量
     *
     * @return
     */
    @GET("api/service-depository/app/project/getCouponCount")
    Observable<BaseBean<CouponNum>> getCouponCount(@Query("investAmount") double couponAmount, @Query("scenario") int scenario, @Query("bidId") String bidId);

}
