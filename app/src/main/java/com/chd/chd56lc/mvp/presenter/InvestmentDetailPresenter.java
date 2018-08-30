package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.mvp.model.ProjectModel;
import com.chd.chd56lc.mvp.view.IInvestmentDetailView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class InvestmentDetailPresenter extends NetPresenter {
    private IInvestmentDetailView iInvestmentView;
    private ProjectModel projectModel;

    public InvestmentDetailPresenter(IInvestmentDetailView iInvestmentView, ProjectModel projectModel) {
        this.iInvestmentView = iInvestmentView;
        this.projectModel = projectModel;
    }

    public void projectDetail(String id, int type) {
        addSubscription(projectModel.projectDetail(id, type), new ApiCallback<ProjectDetailBean>() {
            @Override
            public void onSuccess(ProjectDetailBean model) {
                iInvestmentView.updateProjectDetail(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

}
