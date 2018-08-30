package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.PushSetBean;
/**
 * 推送设置
 */
public interface IPushSetView extends LoadingView {

    /**
     * 推送设置
     */
    void setPushSuccess();

    /**
     * 获取推送
     */
    void updatePush(PushSetBean pushSetBean);
}
