package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.PageListBean;
/**
 * 主页发现
 */
public interface IFindView extends LoadingView {
    void updateActive(PageListBean<BannerBean> Activities);
}
