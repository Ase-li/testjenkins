package com.chd.chd56lc.entity;

import com.chd.chd56lc.utils.NumberFormalUtils;

import java.io.Serializable;
import java.util.List;

/**
 * 每月结息详情
 * <p>
 * Created by li on 2018/2/9.
 */

public class OrderDetailInfo implements Serializable {

    /**
     * amount	number
     * 投资金额
     * <p>
     * annualRate	number
     * 当前年化
     * <p>
     * appInvestRecordLogRespList	[...]
     * couponCashBonus	number
     * 使用红包
     * <p>
     * couponInterestRate	number
     * 加息利率
     * <p>
     * expectInterest	number
     * 预期收益
     * <p>
     * holdingDays	integer($int32)
     * 最长天数
     * <p>
     * interestWay	integer($int32)
     * 付息方式
     * <p>
     * label	integer($int32)
     * 特点标签
     * <p>
     * leftLockDay	integer($int32)
     * 锁定期剩余时间
     * <p>
     * raiseInterest	number
     * 募集期补贴
     * <p>
     * sentInterest	number
     * 已下发累计收益（元）
     * <p>
     * type	integer($int32)
     * 产品类型，1：无忧产品，2：债权转让
     * couponInterestDay	integer($int32)
     * 优惠券加息天数
     * platformInterestDay	integer($int32)
     * 平台加息天数
     * raiseDay	integer($int32)
     * 募集期（天）
     */
    private String bidId;
    private double amount;
    private double annualRate;
    private int couponCashBonus;
    private double couponInterestRate;
    private double expectInterest;
    private int holdingDays;
    private int label;
    private double platformInterestRate;
    private int leftLockDay;
    private double raiseInterest;
    private double sentInterest;
    private int type;
    private List<BidStatusLogRespListBean> appInvestRecordLogRespList;
    private int interestWay;
    private int couponInterestDay;
    private int platformInterestDay;
    private int raiseDay;

    /**
     * 获取优惠券加息+天数
     *
     * @return
     */
    public String getCouponInterestRateString() {
        if (couponInterestDay != 0)
            return "优惠券加息：" + NumberFormalUtils.percentFormat(couponInterestRate, 2, 2) + " " + couponInterestDay + "天";
        else
            return "优惠券加息：" + NumberFormalUtils.percentFormat(couponInterestRate, 2, 2);
    }

    /**
     * 获取平台加息+天数
     *
     * @return
     */
    public String getPlatformInterestRateString() {
        if (platformInterestDay != 0)
            return "平台加息：" + NumberFormalUtils.percentFormat(platformInterestRate, 2, 2) + " " + platformInterestDay + "天";
        else
            return "平台加息：" + NumberFormalUtils.percentFormat(platformInterestRate, 2, 2);
    }

    /**
     * 获取平台加息+天数
     *
     * @return
     */
    public String getRaiseRateString() {
        if (raiseDay != 0)
            return "募集期补贴：" + NumberFormalUtils.percentFormat(raiseInterest, 2, 2) + " " + raiseDay + "天";
        else
            return "募集期补贴：" + NumberFormalUtils.percentFormat(raiseInterest, 2, 2);
    }

    public int getCouponInterestDay() {
        return couponInterestDay;
    }

    public void setCouponInterestDay(int couponInterestDay) {
        this.couponInterestDay = couponInterestDay;
    }

    public int getPlatformInterestDay() {
        return platformInterestDay;
    }

    public void setPlatformInterestDay(int platformInterestDay) {
        this.platformInterestDay = platformInterestDay;
    }

    public int getRaiseDay() {
        return raiseDay;
    }

    public void setRaiseDay(int raiseDay) {
        this.raiseDay = raiseDay;
    }

    public double getPlatformInterestRate() {
        return platformInterestRate;
    }

    public void setPlatformInterestRate(double platformInterestRate) {
        this.platformInterestRate = platformInterestRate;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public int getInterestWay() {
        return interestWay;
    }

    public void setInterestWay(int interestWay) {
        this.interestWay = interestWay;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public int getCouponCashBonus() {
        return couponCashBonus;
    }

    public void setCouponCashBonus(int couponCashBonus) {
        this.couponCashBonus = couponCashBonus;
    }

    public double getCouponInterestRate() {
        return couponInterestRate;
    }

    public void setCouponInterestRate(double couponInterestRate) {
        this.couponInterestRate = couponInterestRate;
    }

    public double getExpectInterest() {
        return expectInterest;
    }

    public void setExpectInterest(double expectInterest) {
        this.expectInterest = expectInterest;
    }

    public int getHoldingDays() {
        return holdingDays;
    }

    public void setHoldingDays(int holdingDays) {
        this.holdingDays = holdingDays;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getLeftLockDay() {
        return leftLockDay;
    }

    public void setLeftLockDay(int leftLockDay) {
        this.leftLockDay = leftLockDay;
    }

    public double getRaiseInterest() {
        return raiseInterest;
    }

    public void setRaiseInterest(double raiseInterest) {
        this.raiseInterest = raiseInterest;
    }

    public double getSentInterest() {
        return sentInterest;
    }

    public void setSentInterest(double sentInterest) {
        this.sentInterest = sentInterest;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<BidStatusLogRespListBean> getBidStatusLogRespList() {
        return appInvestRecordLogRespList;
    }

    public void setBidStatusLogRespList(List<BidStatusLogRespListBean> appInvestRecordLogRespList) {
        this.appInvestRecordLogRespList = appInvestRecordLogRespList;
    }

    public static class BidStatusLogRespListBean {
        /**
         * createdAt : 2018-07-07T02:41:12.114Z
         * createdTime : string
         * status : string
         */
        private String createdAt;
        private String createdTime;
        private String description;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedTime() {
            return createdTime;
        }

        public void setCreatedTime(String createdTime) {
            this.createdTime = createdTime;
        }

    }
}
