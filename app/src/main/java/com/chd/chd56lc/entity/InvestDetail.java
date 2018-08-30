package com.chd.chd56lc.entity;

/**
 * amount	number
 * 可用余额
 * <p>
 * annualRate	number
 * 协议约定年化利率
 * <p>
 * balanceAmount	number
 * 可投金额
 * <p>
 * bankCardName	string
 * 银行名称
 * <p>
 * bankCardNo	string
 * 卡号后四位
 * <p>
 * lockDay	integer($int32)
 * 锁定期限（天）
 * <p>
 * platformInterestRate	number
 * 平台加息利率
 * <p>
 * term	integer($int32)
 * 最大投资期限（天）
 * <p>
 * 投资详情
 */
public class InvestDetail {

    private double userBalanceAmount;
    private double annualRate;
    private double canInvestAmount;
    private String bankCardName;
    private String bankCardNo;
    private int lockDay;
    private double platformInterestRate;
    private int term;
    private String projectName;
    private int platformInterestDays;

    public int getPlatformInterestDays() {
        return platformInterestDays;
    }

    public void setPlatformInterestDays(int platformInterestDays) {
        this.platformInterestDays = platformInterestDays;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public double getUserBalanceAmount() {
        return userBalanceAmount;
    }

    public void setUserBalanceAmount(double amount) {
        this.userBalanceAmount = userBalanceAmount;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public double getCanInvestAmount() {
        return canInvestAmount;
    }

    public void setCanInvestAmount(double canInvestAmount) {
        this.canInvestAmount = canInvestAmount;
    }

    public String getBankCardName() {
        return bankCardName;
    }

    public void setBankCardName(String bankCardName) {
        this.bankCardName = bankCardName;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public int getLockDay() {
        return lockDay;
    }

    public void setLockDay(int lockDay) {
        this.lockDay = lockDay;
    }

    public double getPlatformInterestRate() {
        return platformInterestRate;
    }

    public void setPlatformInterestRate(double platformInterestRate) {
        this.platformInterestRate = platformInterestRate;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }
}
