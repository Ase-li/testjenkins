package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IClubView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

public class ClubPresenter extends NetPresenter {
    private IClubView iClubView;
    private UserServerModel userServerModel;

    public ClubPresenter(IClubView iClubView, UserServerModel userServerModel) {
        this.iClubView = iClubView;
        this.userServerModel = userServerModel;
    }

    /**
     * 获取用户理财师状态
     *
     * @return
     */
    public void listUserFinancialPlannerStatus() {
        iClubView.showLoading();
        addSubscription(userServerModel.listUserFinancialPlannerStatus(), new ApiCallback<List<Integer>>() {
            @Override
            public void onSuccess(List<Integer> model) {
                iClubView.userFinancialPlannerStatus(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iClubView.dismissLoading();
            }
        });
    }

    /**
     * 申请成为理财师
     *
     * @return
     */
    public void applyForFinancialPlanner() {
        iClubView.showLoading();
        addSubscription(userServerModel.applyForFinancialPlanner(), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                UserManager.getInstance().getCurrentUserInfo().setType(2);
                UserManager.getInstance().saveUserInfo(UserManager.getInstance().getCurrentUserInfo());
                iClubView.applyResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iClubView.dismissLoading();
            }
        });
    }

}
