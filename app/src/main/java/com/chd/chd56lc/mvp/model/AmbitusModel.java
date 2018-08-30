package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.InvestmentClassroomBean;
import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.net.api.AmbitusApi;

import io.reactivex.Observable;

public class AmbitusModel {
    private AmbitusApi ambitusApi;

    public AmbitusModel() {
        if (ambitusApi == null)
            this.ambitusApi = BaseApplication.getAppComponent().retrofit().create(AmbitusApi.class);

    }

    public Observable<BaseBean<PageListBean<MessageBean>>> paginageMsgList(int page, int count, int type) {
        return ambitusApi.paginageMsgList(page, count, type);
    }

    public Observable<BaseBean<PageListBean<InvestmentClassroomBean>>> paginateByReq(int page, int count, int type) {
        return ambitusApi.paginateByReq(page, count, type);
    }

    public Observable<BaseBean<MessageBean>> getMessageById(String id) {
        return ambitusApi.getMessageById(id);
    }

}
