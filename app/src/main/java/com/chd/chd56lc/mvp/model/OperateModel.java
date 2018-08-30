package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.BannerOrFloatWindow;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.net.api.OperateApi;

import java.util.List;

import io.reactivex.Observable;

public class OperateModel {
    private OperateApi operateApi;

    public OperateModel() {
        if (operateApi == null)
            this.operateApi = BaseApplication.getAppComponent().retrofit().create(OperateApi.class);

    }

    public Observable<BaseBean<BannerOrFloatWindow>> listBanner() {
        return operateApi.listBanner();
    }

    public Observable<BaseBean<PageListBean<BannerBean>>> paginateActivity(int page, int count) {
        return operateApi.paginateActivity(page, count);
    }

}
