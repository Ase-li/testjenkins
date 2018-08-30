package com.chd.chd56lc.entity;

public class FriendBean {
    /**
     * depositRegistTime	integer($int64)
     * 开户时间(时间戳)
     * <p>
     * depositRegistTimeF	string
     * 开户时间(格式化)
     * <p>
     * ifCommission	boolean
     * example: false
     * 是否计算佣金
     * <p>
     * name	string
     * 姓名
     * <p>
     * phone	string
     * 手机号
     */

    private long depositRegistTime;
    private boolean ifCommission;
    private String depositRegistTimeF;
    private String name;
    private String phone;

    public String getDepositRegistTimeF() {
        return depositRegistTimeF;
    }

    public void setDepositRegistTimeF(String depositRegistTimeF) {
        this.depositRegistTimeF = depositRegistTimeF;
    }

    public long getDepositRegistTime() {
        return depositRegistTime;
    }

    public void setDepositRegistTime(long depositRegistTime) {
        this.depositRegistTime = depositRegistTime;
    }

    public boolean isIfCommission() {
        return ifCommission;
    }

    public void setIfCommission(boolean ifCommission) {
        this.ifCommission = ifCommission;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
