package com.chd.chd56lc.entity;

import java.util.List;

public class ProjectListItem {

    /**
     * queueAmount : 0
     * respPager : {"array":[{"amount":0,"bidName":"string","comprehensiveAnnualRate":0,"expiryTime":"string","label":0}],"count":0,"extras":{},"offset":0,"page":0,"total":0,"totalPageNo":0}
     */

    private int queueAmount;
    private List<ProjectDetailBean> array;
    private int offset;
    private int page;
    private int total;
    private int totalPageNo;
    private int count;

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(int totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getQueueAmount() {
        return queueAmount;
    }

    public void setQueueAmount(int queueAmount) {
        this.queueAmount = queueAmount;
    }

    public List<ProjectDetailBean> getArray() {
        return array;
    }

    public void setArray(List<ProjectDetailBean> array) {
        this.array = array;
    }
}
