package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.ProjectListItem;
/**
 * 项目列表
 */
public interface IProjectFragmentView extends LoadingView{
    void updateProjectList(ProjectListItem projectListItemBean);
}
