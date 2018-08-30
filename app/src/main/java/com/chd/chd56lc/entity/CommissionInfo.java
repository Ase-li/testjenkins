package com.chd.chd56lc.entity;

public class CommissionInfo {
    /**
     * accumulateCommission	number
     * 累计佣金收益
     * <p>
     * currentAvgDailyHold	number
     * 当前日均在持资金
     * <p>
     * nextRoundCommission	number
     * 下期预计收益
     * <p>
     * inviteesAmount	Intege
     * 好友人数
     */

    private double accumulateCommission;
    private double currentAvgDailyHold;
    private double nextRoundCommission;
    private int inviteesAmount;

    public int getInviteesAmount() {
        return inviteesAmount;
    }

    public void setInviteesAmount(int inviteesAmount) {
        this.inviteesAmount = inviteesAmount;
    }

    public double getAccumulateCommission() {
        return accumulateCommission;
    }

    public void setAccumulateCommission(double accumulateCommission) {
        this.accumulateCommission = accumulateCommission;
    }

    public double getCurrentAvgDailyHold() {
        return currentAvgDailyHold;
    }

    public void setCurrentAvgDailyHold(double currentAvgDailyHold) {
        this.currentAvgDailyHold = currentAvgDailyHold;
    }

    public double getNextRoundCommission() {
        return nextRoundCommission;
    }

    public void setNextRoundCommission(double nextRoundCommission) {
        this.nextRoundCommission = nextRoundCommission;
    }
}
