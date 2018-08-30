package com.chd.chd56lc.entity;

public class FloatWindowBean {

    /**
     * activityUrl	string
     * 活动链接
     * <p>
     * floatIcon	string
     * 悬浮窗icon url
     * <p>
     * imgUrl	string
     * 图片链接
     * <p>
     * name	string
     * 名称
     */

    private String activityUrl;
    private String floatIcon;
    private String imgUrl;
    private String name;

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getFloatIcon() {
        return floatIcon;
    }

    public void setFloatIcon(String floatIcon) {
        this.floatIcon = floatIcon;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
