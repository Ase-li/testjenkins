package com.chd.chd56lc.entity;

import java.util.List;

public class TransactionRecordList {

    /**
     * array : [{"amount":0,"ifIncrease":false,"orderId":0,"status":0,"timestamp":0,"type":0}]
     * count : 0
     * extras : {}
     * offset : 0
     * page : 0
     * total : 0
     * totalPageNo : 0
     */

    private int count;
    private ExtrasBean extras;
    private int offset;
    private int page;
    private int total;
    private int totalPageNo;
    private List<TransactionRecordBean> array;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ExtrasBean getExtras() {
        return extras;
    }

    public void setExtras(ExtrasBean extras) {
        this.extras = extras;
    }

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

    public List<TransactionRecordBean> getArray() {
        return array;
    }

    public void setArray(List<TransactionRecordBean> array) {
        this.array = array;
    }

    public static class ExtrasBean {
    }

    public static class ArrayBean {
    }
}
