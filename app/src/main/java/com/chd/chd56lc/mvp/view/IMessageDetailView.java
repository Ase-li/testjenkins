package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.MessageBean;
/**
 * 消息详情
 */
public interface IMessageDetailView extends LoadingView {
    void setMessageDetail(MessageBean messageDetail);
}
