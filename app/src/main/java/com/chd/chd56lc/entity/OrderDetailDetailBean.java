package com.chd.chd56lc.entity;

public class OrderDetailDetailBean {

    /**
     * amount	number
     * 收益
     * <p>
     * createdAt	string($date-time)
     * 创建时间
     * <p>
     * interestType	integer($int32)
     * 收益类型；1：正常利息，2：募集利息，3：优惠券加息，4：平台加息
     * <p>
     * status	integer($int32)
     * 订单状态；1：发放中，2：成功，3：失败
     */

    private double amount;
    private String createdAt;
    private int interestType;
    private int status;

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getInterestType() {
        return interestType;
    }

    public void setInterestType(int interestType) {
        this.interestType = interestType;
    }

    public int getStatus() {
        return status;
    }

    public String getStatusString() {
        switch (interestType) {
            case 1:
                return "正常利息";
            case 2:
                return "募集利息";
            case 3:
                return "优惠券加息";
            case 4:
                return "平台加息";
        }
        return "";
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
