package com.chd.chd56lc.entity;

//推送设置
public class PushSetBean {

    /**
     * channel : 0
     * debt : false
     * dividend : false
     * email : string
     * invest : false
     * recharge : false
     * remit : false
     * withdraw : false
     */

    private int channel;
    private boolean debt;
    private boolean dividend;
    private String email;
    private boolean invest;
    private boolean recharge;
    private boolean remit;
    private boolean withdraw;

    public PushSetBean() {
    }

    public PushSetBean(int channel, boolean debt, boolean dividend, String email, boolean invest, boolean recharge, boolean remit, boolean withdraw) {
        this.channel = channel;
        this.debt = debt;
        this.dividend = dividend;
        this.email = email;
        this.invest = invest;
        this.recharge = recharge;
        this.remit = remit;
        this.withdraw = withdraw;
    }

    public int getChannel() {
        return channel;
    }

    public void setChannel(int channel) {
        this.channel = channel;
    }

    public boolean isDebt() {
        return debt;
    }

    public void setDebt(boolean debt) {
        this.debt = debt;
    }

    public boolean isDividend() {
        return dividend;
    }

    public void setDividend(boolean dividend) {
        this.dividend = dividend;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isInvest() {
        return invest;
    }

    public void setInvest(boolean invest) {
        this.invest = invest;
    }

    public boolean isRecharge() {
        return recharge;
    }

    public void setRecharge(boolean recharge) {
        this.recharge = recharge;
    }

    public boolean isRemit() {
        return remit;
    }

    public void setRemit(boolean remit) {
        this.remit = remit;
    }

    public boolean isWithdraw() {
        return withdraw;
    }

    public void setWithdraw(boolean withdraw) {
        this.withdraw = withdraw;
    }
}
