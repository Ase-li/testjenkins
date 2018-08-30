package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IRiskAssessmentView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class RiskAssessmentPresenter extends NetPresenter {
    private UserServerModel userServerModel;
    private IRiskAssessmentView iRiskAssessmentView;

    public RiskAssessmentPresenter(IRiskAssessmentView iRiskAssessmentView, UserServerModel userServerModel) {
        this.userServerModel = userServerModel;
        this.iRiskAssessmentView = iRiskAssessmentView;
    }

    /**
     * 风险测评
     * @param score
     */
    public void setRaLevel(String score) {
        iRiskAssessmentView.showLoading();
        addSubscription(userServerModel.setRaLevel(score), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                iRiskAssessmentView.riskAssessmentResult(true);
            }

            @Override
            public void onFailure(int code, String msg) {
                iRiskAssessmentView.riskAssessmentResult(false);
            }

            @Override
            public void onFinish() {
                iRiskAssessmentView.dismissLoading();
            }
        });
    }
}
