package com.chd.chd56lc.entity;

import java.util.List;

public class AssetAnalyzeBean {

    /**
     * balance	number
     * 用户余额
     * <p>
     * currentAmount	number
     * 在持金额
     * <p>
     * notReceiveProfit	number
     * 待收收益
     * <p>
     * totalAmount	number
     * 总资产
     * receivedProfit	number
     * 已下发收益
     * <p>
     * totalProfit	number
     * 总收益
     */

    private float balance;
    private float currentAmount;
    private float notReceiveProfit;
    private float receivedProfit;
    private float totalAmount;
    private float totalProfit;
    private List<AssestForDateBoListBean> assestForDateBoList;

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }

    public float getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(float totalProfit) {
        this.totalProfit = totalProfit;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public float getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(float currentAmount) {
        this.currentAmount = currentAmount;
    }

    public float getNotReceiveProfit() {
        return notReceiveProfit;
    }

    public void setNotReceiveProfit(float notReceiveProfit) {
        this.notReceiveProfit = notReceiveProfit;
    }

    public float getReceivedProfit() {
        return receivedProfit;
    }

    public void setReceivedProfit(float receivedProfit) {
        this.receivedProfit = receivedProfit;
    }

    public List<AssestForDateBoListBean> getAssestForDateBoList() {
        return assestForDateBoList;
    }

    public void setAssestForDateBoList(List<AssestForDateBoListBean> assestForDateBoList) {
        this.assestForDateBoList = assestForDateBoList;
    }

    public static class AssestForDateBoListBean {
        /**
         * amountInMonth : 0
         * date : string
         */

        private float amountInMonth;
        private String date;
        private long dateTimestamp;

        public long getDateTimestamp() {
            return dateTimestamp;
        }

        public void setDateTimestamp(long dateTimestamp) {
            this.dateTimestamp = dateTimestamp;
        }

        public float getAmountInMonth() {
            return amountInMonth;
        }

        public void setAmountInMonth(float amountInMonth) {
            this.amountInMonth = amountInMonth;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }
}
