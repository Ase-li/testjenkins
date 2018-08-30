package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.InvestmentClassroomBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.model.AmbitusModel;
import com.chd.chd56lc.mvp.view.IClassRoomDetailView;
import com.chd.chd56lc.mvp.view.IClassRoomListView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class ClassRoomPresenter extends NetPresenter {
    private IClassRoomListView iClassRoomListView;
    private IClassRoomDetailView iClassRoomDetailView;
    private AmbitusModel ambitusModel;

    public ClassRoomPresenter(IClassRoomListView iClassRoomListView, AmbitusModel ambitusModel) {
        this.iClassRoomListView = iClassRoomListView;
        this.ambitusModel = ambitusModel;
    }

    public ClassRoomPresenter(IClassRoomDetailView iClassRoomDetailView, AmbitusModel ambitusModel) {
        this.iClassRoomDetailView = iClassRoomDetailView;
        this.ambitusModel = ambitusModel;
    }

    /**
     * 投资课堂分页
     *
     * @param page
     * @param count
     * @param type  类型 0-风险教育、1-知识普及
     * @return
     */
    public void paginateByReq(int page, int count, int type) {
//        iClassRoomListView.showLoading();
        addSubscription(ambitusModel.paginateByReq(page, count, type), new ApiCallback<PageListBean<InvestmentClassroomBean>>() {
            @Override
            public void onSuccess(PageListBean<InvestmentClassroomBean> model) {
                iClassRoomListView.updateClassRoomList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iClassRoomListView.updateClassRoomList(null);
            }

            @Override
            public void onFinish() {
                iClassRoomListView.dismissLoading();
            }
        });
    }

    public void getMessageById(String id) {
        iClassRoomDetailView.showLoading();
        addSubscription(ambitusModel.getMessageById(id), new ApiCallback<InvestmentClassroomBean>() {
            @Override
            public void onSuccess(InvestmentClassroomBean model) {
                iClassRoomDetailView.setClassRoomDetail(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iClassRoomDetailView.dismissLoading();
            }
        });
    }
}
