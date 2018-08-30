package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.FriendBean;
import com.chd.chd56lc.entity.PageListBean;

/**
 * 好友列表
 */
public interface IFriendView extends LoadingView {

    /**
     * 好友列表
     *
     * @param friendBeans
     */
    void setFriendBeans(PageListBean<FriendBean> friendBeans);
}
