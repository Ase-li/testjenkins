package com.chd.chd56lc.entity;

public class CommissionBean {

    /**
     * commission : 0
     * date : 0
     * dateF : string
     * successTime : 0
     * successTimeF : string
     */

    private Double commission;
    private long date;
    private String dateF;
    private long successTime;
    private String successTimeF;

    public Double getCommission() {
        return commission;
    }

    public void setCommission(Double commission) {
        this.commission = commission;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateF() {
        return dateF;
    }

    public void setDateF(String dateF) {
        this.dateF = dateF;
    }

    public long getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(long successTime) {
        this.successTime = successTime;
    }

    public String getSuccessTimeF() {
        return successTimeF;
    }

    public void setSuccessTimeF(String successTimeF) {
        this.successTimeF = successTimeF;
    }
}
