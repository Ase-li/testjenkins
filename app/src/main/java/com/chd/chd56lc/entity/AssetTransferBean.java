package com.chd.chd56lc.entity;

/**
 * 转让确定列表Item
 * <p>
 * annualRate	number
 * 今日转让结算利率
 * <p>
 * id	integer($int64)
 * investTime	string($date)
 * 投资时间
 * <p>
 * name	string
 * 名称
 * <p>
 * queueOrderCount	integer($int32)
 * 当前债转排队订单
 * <p>
 * transferAmount	number
 * 转出金额
 * <p>
 * transferRate	number
 * 转出手续费率
 */
public class AssetTransferBean {

    public boolean isShow;
    private double annualRate;
    private String id;
    private String investTime;
    private String name;
    private int queueOrderCount;
    private double transferAmount;
    private double transferRate;

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQueueOrderCount() {
        return queueOrderCount;
    }

    public void setQueueOrderCount(int queueOrderCount) {
        this.queueOrderCount = queueOrderCount;
    }

    public double getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(double transferAmount) {
        this.transferAmount = transferAmount;
    }

    public double getTransferRate() {
        return transferRate;
    }

    public void setTransferRate(double transferRate) {
        this.transferRate = transferRate;
    }
}
