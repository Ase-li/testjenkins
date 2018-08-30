package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.entity.BannerOrFloatWindow;
import com.chd.chd56lc.entity.HomeDataBean;
import com.chd.chd56lc.entity.ProjectDetailBean;

import java.util.List;

/**
 * 首页
 */
public interface IHomeView extends LoadingView {
    /**
     * 首页banner图与悬浮窗
     *
     * @param bannerOrFloatWindow
     */
    void setBannerOrFloat(BannerOrFloatWindow bannerOrFloatWindow);

    /**
     * 首页推荐标的
     *
     * @param projectDetailBean
     */
    void homeRecommendBid(ProjectDetailBean projectDetailBean);

    /**
     * 首页信息数，新手指引
     *
     * @param homeDataBean
     */
    void homePageDate(HomeDataBean homeDataBean);
}
