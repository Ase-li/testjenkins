package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.InvestmentClassroomBean;
import com.chd.chd56lc.entity.PageListBean;
/**
 * 投资课堂列表
 */
public interface IClassRoomListView extends LoadingView {
    void updateClassRoomList(PageListBean<InvestmentClassroomBean> classRoomBeanPageListBean);
}
