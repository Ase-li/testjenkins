package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.CouponNum;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.InvestDetail;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.entity.ProjectListItem;
import com.chd.chd56lc.net.api.ProjectApi;

import io.reactivex.Observable;

public class ProjectModel {

    private ProjectApi projectApi;

    public ProjectModel() {
        projectApi = BaseApplication.getAppComponent().retrofit().create(ProjectApi.class);
    }

    /**
     * 项目列表
     *
     * @param page
     * @param count
     * @param type  类型；1：无忧投，2：债权转让
     * @return
     */
    public Observable<BaseBean<ProjectListItem>> projectList(int page, int count, int type) {
        return projectApi.projectList(page, count, type);
    }

    /**
     * 项目详情
     *
     * @param id 订单ID
     * @return
     */
    public Observable<BaseBean<ProjectDetailBean>> projectDetail(String id, int type) {
        return projectApi.projectDetail(id, type);
    }

    /**
     * 首页推荐
     *
     * @return
     */
    public Observable<BaseBean<ProjectDetailBean>> homeRecommendBid() {
        return projectApi.homeRecommendBid();
    }

    /**
     * 投资页-详情
     *
     * @param id 订单ID
     * @return
     */
    public Observable<BaseBean<InvestDetail>> detailInvest(String id, int type) {
        return projectApi.detailInvest(id, type);
    }


    /**
     * 投资人投标申请 (对应，季无忧与年无忧的投资)
     * 玩蛇
     *
     * @param bidId    订单id
     * @param couponId 优惠券id
     * @param amount   金额
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> bidApplyP(String bidId, String couponId, double amount) {
        return projectApi.bidApplyP(bidId, couponId, amount, 1);
    }


    /**
     * 购买债权
     * 玩蛇
     *
     * @param bidId    订单id
     * @param couponId 优惠券id
     * @param amount   金额
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> buyCreditP(String bidId, String couponId, double amount) {
        return projectApi.buyCreditP(bidId, couponId, amount, 1);
    }

    /**
     * 获取优惠券数量
     *
     * @param couponAmount 投资金额
     * @return
     */
    public Observable<BaseBean<CouponNum>> getCouponCount(double couponAmount, int scenario, String bidId) {
        return projectApi.getCouponCount(couponAmount, scenario, bidId);
    }


}
