package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.InvestmentClassroomBean;

/**
 * 投资课堂详情
 */
public interface IClassRoomDetailView extends LoadingView {
    void setClassRoomDetail(InvestmentClassroomBean classRoomBean);
}
