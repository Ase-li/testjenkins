package com.chd.chd56lc.entity;

//个人用户提现信息
public class WithDrawInfo {

    /**
     * amount	number
     * 提现金额
     * bondCount	integer($int32)
     * 提现券个数
     * freeWithdrawTimes	integer($int32)
     * 免费提现次数
     * withdrawFee	number
     * 提现服务费
     */

    private int amount;
    private int bondCount;
    private int freeWithdrawTimes;
    private int withdrawFee;
    private String withdrawDesc;

    public String getWithdrawDesc() {
        return withdrawDesc;
    }

    public void setWithdrawDesc(String withdrawDesc) {
        this.withdrawDesc = withdrawDesc;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getBondCount() {
        return bondCount;
    }

    public void setBondCount(int bondCount) {
        this.bondCount = bondCount;
    }

    public int getFreeWithdrawTimes() {
        return freeWithdrawTimes;
    }

    public void setFreeWithdrawTimes(int freeWithdrawTimes) {
        this.freeWithdrawTimes = freeWithdrawTimes;
    }

    public int getWithdrawFee() {
        return withdrawFee;
    }

    public void setWithdrawFee(int withdrawFee) {
        this.withdrawFee = withdrawFee;
    }
}
