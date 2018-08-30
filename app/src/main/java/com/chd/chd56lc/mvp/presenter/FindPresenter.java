package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.model.OperateModel;
import com.chd.chd56lc.mvp.view.IFindView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import javax.inject.Inject;


public class FindPresenter extends NetPresenter {
    private IFindView iFindView;
    private OperateModel operateModel;

    @Inject
    public FindPresenter(IFindView iFindView, OperateModel operateModel) {
        super();
        this.iFindView = iFindView;
        this.operateModel = operateModel;
    }

    /**
     * 首页banner
     *
     * @return
     */
    public void paginateActivity(int page, int count) {
//        iFindView.showLoading();
        addSubscription(operateModel.paginateActivity(page, count), new ApiCallback<PageListBean<BannerBean>>() {
            @Override
            public void onSuccess(PageListBean<BannerBean> model) {
                iFindView.updateActive(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iFindView.updateActive(null);
            }

            @Override
            public void onFinish() {
                iFindView.dismissLoading();
            }
        });
    }
}
