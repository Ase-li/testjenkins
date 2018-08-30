package com.chd.chd56lc.entity;

public class BannerBean {

    /**
     * activityUrl	string
     * 活动链接
     * <p>
     * description	string
     * 活动描述
     * <p>
     * endTime	string($date-time)
     * 活动结束时间
     * <p>
     * id	integer($int64)
     * imgUrl	string
     * 图片链接
     * <p>
     * name	string
     * 名称
     * <p>
     * sort	integer($int32)
     * 排序号
     * <p>
     * status	integer($int32)
     * 0 下线,1 上线 , 2已结束
     */

    private String activityUrl;
    private String description;
    private String startTime;
    private String id;
    private String imgUrl;
    private String name;
    private int sort;
    private int status;

    public String getActivityUrl() {
        return activityUrl;
    }

    public void setActivityUrl(String activityUrl) {
        this.activityUrl = activityUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
