package com.chd.chd56lc.entity;

//提现充值时，个人银行信息与处理中订单金额
public class BankInfoOrBalanceBean {

    /**
     * balance : 0
     * bankCardNo : string
     * bankCode : string
     * bankName : string
     * dayLimit : 0
     * processAmount : 0
     * singleLimit : 0
     */

    private double balance;
    private String bankCardNo;
    private String bankCode;
    private String bankName;
    private double dayLimit;
    private double processAmount;
    private double singleLimit;
    private String withdrawDesc;

    public String getWithdrawDesc() {
        return withdrawDesc;
    }

    public void setWithdrawDesc(String withdrawDesc) {
        this.withdrawDesc = withdrawDesc;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getBankCardNo() {
        return bankCardNo;
    }

    public void setBankCardNo(String bankCardNo) {
        this.bankCardNo = bankCardNo;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public double getDayLimit() {
        return dayLimit;
    }

    public void setDayLimit(double dayLimit) {
        this.dayLimit = dayLimit;
    }

    public double getProcessAmount() {
        return processAmount;
    }

    public void setProcessAmount(double processAmount) {
        this.processAmount = processAmount;
    }

    public double getSingleLimit() {
        return singleLimit;
    }

    public void setSingleLimit(double singleLimit) {
        this.singleLimit = singleLimit;
    }
}
