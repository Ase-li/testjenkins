package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.entity.PageListBean;
/**
 * 消息列表
 */
public interface IMessageListView extends LoadingView{
    void updateMessageList(PageListBean<MessageBean> messageBeanPageListBean);
}
