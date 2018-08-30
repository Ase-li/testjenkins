package com.chd.chd56lc.mvp.presenter;

import android.annotation.SuppressLint;

import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.BannerOrFloatWindow;
import com.chd.chd56lc.entity.HomeDataBean;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.mvp.model.OperateModel;
import com.chd.chd56lc.mvp.model.ProjectModel;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IHomeView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;


public class HomePresenter extends NetPresenter {
    private IHomeView homeView;
    private OperateModel operateModel;
    private ProjectModel projectModel;
    private UserServerModel userServerModel;

    @Inject
    public HomePresenter(IHomeView homeView, OperateModel operateModel, ProjectModel projectModel, UserServerModel userServerModel) {
        super();
        this.homeView = homeView;
        this.operateModel = operateModel;
        this.projectModel = projectModel;
        this.userServerModel = userServerModel;
    }

    /**
     * 获取首页聚合信息(消息数,新手任务首投标的等)
     *
     * @return
     */
    public void getHomePageData() {
        addSubscription(userServerModel.getHomePageData(), new ApiCallback<HomeDataBean>() {
            @Override
            public void onSuccess(HomeDataBean model) {
                homeView.homePageDate(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
            }
        });
    }

    @SuppressLint("CheckResult")
    public void home() {
        addSubscription(Observable.mergeArray(projectModel.homeRecommendBid(), operateModel.listBanner()), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object data) {
                if (data instanceof ProjectDetailBean) {
                    homeView.homeRecommendBid((ProjectDetailBean) data);
                } else if (data instanceof BannerOrFloatWindow) {
                    homeView.setBannerOrFloat((BannerOrFloatWindow) data);
                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                homeView.dismissLoading();
            }
        });
    }
}
