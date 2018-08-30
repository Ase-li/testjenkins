package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.entity.PageListBean;
import com.chd.chd56lc.mvp.model.AmbitusModel;
import com.chd.chd56lc.mvp.view.IMessageDetailView;
import com.chd.chd56lc.mvp.view.IMessageListView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class MessagePresenter extends NetPresenter {
    private IMessageListView iMessageListView;
    private IMessageDetailView iMessageDetailView;
    private AmbitusModel ambitusModel;

    public MessagePresenter(IMessageListView iMessageListView, AmbitusModel ambitusModel) {
        this.iMessageListView = iMessageListView;
        this.ambitusModel = ambitusModel;
    }

    public MessagePresenter(IMessageDetailView iMessageDetailView, AmbitusModel ambitusModel) {
        this.iMessageDetailView = iMessageDetailView;
        this.ambitusModel = ambitusModel;
    }

    public void paginageMsgList(int page, int count, int type) {
//        iMessageListView.showLoading();
        addSubscription(ambitusModel.paginageMsgList(page, count, type), new ApiCallback<PageListBean<MessageBean>>() {
            @Override
            public void onSuccess(PageListBean<MessageBean> model) {
                iMessageListView.updateMessageList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iMessageListView.updateMessageList(null);
            }

            @Override
            public void onFinish() {
                iMessageListView.dismissLoading();
            }
        });
    }

    public void getMessageById(String id) {
        iMessageDetailView.showLoading();
        addSubscription(ambitusModel.getMessageById(id), new ApiCallback<MessageBean>() {
            @Override
            public void onSuccess(MessageBean model) {
                iMessageDetailView.setMessageDetail(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iMessageDetailView.dismissLoading();
            }
        });
    }
}
