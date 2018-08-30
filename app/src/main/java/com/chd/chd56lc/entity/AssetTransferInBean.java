package com.chd.chd56lc.entity;

/**
 * 转让中
 */
public class AssetTransferInBean {

    /**
     * bidName	string
     * 标的名称
     * <p>
     * createdAt	string($date-time)
     * 创建时间
     * <p>
     * currentAmount	number
     * 在持金额
     * <p>
     * id	integer($int64)
     * leftTransferDay	integer($int32)
     * 剩余转让天数
     * <p>
     * lockAmount	number
     * 交易处理中金额
     * <p>
     * status	integer($int32)
     * 特点标签；1：转让中，0：排队中
     * <p>
     * successTransferAmount	number
     * 已转让金额
     * <p>
     * transferOrder	integer($int32)
     * 排队显示顺序
     * <p>
     * transferPercent	number
     * 转让进度
     * <p>
     * updatedAt	string($date-time)
     * 修改时间
     */

    private String bidName;
    private String createdAt;
    private int currentAmount;
    private String id;
    private int leftTransferDay;
    private int lockAmount;
    private int status;
    private int successTransferAmount;
    private int transferOrder;
    private double transferPercent;
    private String updatedAt;

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLeftTransferDay() {
        return leftTransferDay;
    }

    public void setLeftTransferDay(int leftTransferDay) {
        this.leftTransferDay = leftTransferDay;
    }

    public int getLockAmount() {
        return lockAmount;
    }

    public void setLockAmount(int lockAmount) {
        this.lockAmount = lockAmount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSuccessTransferAmount() {
        return successTransferAmount;
    }

    public void setSuccessTransferAmount(int successTransferAmount) {
        this.successTransferAmount = successTransferAmount;
    }

    public int getTransferOrder() {
        return transferOrder;
    }

    public void setTransferOrder(int transferOrder) {
        this.transferOrder = transferOrder;
    }

    public double getTransferPercent() {
        return transferPercent;
    }

    public void setTransferPercent(double transferPercent) {
        this.transferPercent = transferPercent;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
