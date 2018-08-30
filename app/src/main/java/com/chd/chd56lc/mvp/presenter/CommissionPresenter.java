package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.CommissionBean;
import com.chd.chd56lc.entity.CommissionInfo;
import com.chd.chd56lc.entity.FriendBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.ICommissionDetailView;
import com.chd.chd56lc.mvp.view.ICommissionInfoView;
import com.chd.chd56lc.mvp.view.IFriendView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

/**
 * 尊享俱乐部
 */
public class CommissionPresenter extends NetPresenter {
    private ICommissionInfoView iCommissionInfoView;
    private ICommissionDetailView iCommissionDetailView;
    private IFriendView iFriendView;
    private UserServerModel userServerModel;

    public CommissionPresenter(ICommissionInfoView iCommissionInfoView, UserServerModel userServerModel) {
        this.iCommissionInfoView = iCommissionInfoView;
        this.userServerModel = userServerModel;
    }

    public CommissionPresenter(ICommissionDetailView iCommissionDetailView, UserServerModel userServerModel) {
        this.iCommissionDetailView = iCommissionDetailView;
        this.userServerModel = userServerModel;
    }

    public CommissionPresenter(IFriendView iFriendView, UserServerModel userServerModel) {
        this.iFriendView = iFriendView;
        this.userServerModel = userServerModel;
    }

    /**
     * 查询佣金及在持信息
     *
     * @return
     */
    public void getCommissionAndHoldInfo() {
        iCommissionInfoView.showLoading();
        addSubscription(userServerModel.getCommissionAndHoldInfo(), new ApiCallback<CommissionInfo>() {
            @Override
            public void onSuccess(CommissionInfo model) {
                iCommissionInfoView.setCommissionInfo(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iCommissionInfoView.dismissLoading();
            }
        });
    }

    /**
     * 查询佣金及在持信息
     *
     * @return
     */
    public void listCommissionDetail() {
        iCommissionDetailView.showLoading();
        addSubscription(userServerModel.listCommissionDetail(), new ApiCallback<List<CommissionBean>>() {
            @Override
            public void onSuccess(List<CommissionBean> model) {
                iCommissionDetailView.setCommissionBeans(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iCommissionDetailView.dismissLoading();
            }
        });
    }

    /**
     * 查询佣金及在持信息
     *
     * @return
     */
    public void paginateInvitee(int page, int count) {
        iFriendView.showLoading();
        addSubscription(userServerModel.paginateInvitee(page, count), new ApiCallback<PageListBean<FriendBean>>() {
            @Override
            public void onSuccess(PageListBean<FriendBean> model) {
                iFriendView.setFriendBeans(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iFriendView.dismissLoading();
            }
        });
    }
}
