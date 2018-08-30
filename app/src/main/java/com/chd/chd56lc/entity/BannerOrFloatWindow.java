package com.chd.chd56lc.entity;

import java.util.List;

public class BannerOrFloatWindow {
    private FloatWindowBean activityManageFloatWinResp;
    private List<BannerBean> appActivityBannerResps;

    public FloatWindowBean getActivityManageFloatWinResp() {
        return activityManageFloatWinResp;
    }

    public void setActivityManageFloatWinResp(FloatWindowBean activityManageFloatWinResp) {
        this.activityManageFloatWinResp = activityManageFloatWinResp;
    }

    public List<BannerBean> getAppActivityBannerResps() {
        return appActivityBannerResps;
    }

    public void setAppActivityBannerResps(List<BannerBean> appActivityBannerResps) {
        this.appActivityBannerResps = appActivityBannerResps;
    }
}
