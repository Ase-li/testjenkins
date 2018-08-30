package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.ProjectListItem;
import com.chd.chd56lc.mvp.model.ProjectModel;
import com.chd.chd56lc.mvp.view.IProjectFragmentView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

public class ProjectFragmentPresenter extends NetPresenter {
    private IProjectFragmentView iProjectFragmentView;
    private ProjectModel projectModel;

    public ProjectFragmentPresenter(IProjectFragmentView iProjectFragmentView, ProjectModel projectModel) {
        this.iProjectFragmentView = iProjectFragmentView;
        this.projectModel = projectModel;
    }

    public void projectList(int page, int count, int type) {
        addSubscription(projectModel.projectList(page, count, type), new ApiCallback<ProjectListItem>() {
            @Override
            public void onSuccess(ProjectListItem model) {
                iProjectFragmentView.updateProjectList(model);
            }

            @Override
            public void onFailure(int code, String msg) {
                iProjectFragmentView.updateProjectList(null);
            }

            @Override
            public void onFinish() {
                iProjectFragmentView.dismissLoading();
            }
        });
    }

}
