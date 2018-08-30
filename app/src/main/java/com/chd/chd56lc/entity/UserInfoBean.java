package com.chd.chd56lc.entity;

/**
 * accumlatedProfit	number
 * 累计收益
 * <p>
 * balanceAvailable	number
 * 可用余额
 * <p>
 * bankCard	UserBankCardResp{...}
 * couponNum	integer($int32)
 * 加息券数量
 * <p>
 * debtNum	integer($int32)
 * 我的转让笔数
 * <p>
 * historyProfit	number
 * 历史收益
 * <p>
 * idcardNo	string
 * 身份证号
 * <p>
 * ifDepository	string
 * 是否开通存管
 * <p>
 * myInvestment	number
 * 我的投资
 * <p>
 * phone	string
 * 手机号
 * <p>
 * raLevel	integer($int32)
 * 风险测评等级 0-保守型 1-积极型 2-稳健型 3-激进型
 * <p>
 * realName	string
 * 真实姓名
 * <p>
 * redPackNum	number
 * 可用红包金额
 * <p>
 * settings	UserSettingsResp{...}
 * totalAssets	number
 * 总资产 = 累计收益 + 余额 + 我的投资
 * <p>
 * type	integer($int32)
 * 客户类型 (0 新用户 ， 1 普通用户 ， 2 VIP用户
 */
public class UserInfoBean {
    private double accumlatedProfit;
    private double balanceAvailable;
    private BankCardBean bankCard;
    private int couponNum;
    private int debtNum;
    private double historyProfit;
    private String idcardNo;
    private int depositStatus;//存管状态 0-未开通 1-未绑卡 2-未设置密码 3-存管信息完整 4-开通中
    private int myInvestment;
    private String phone;
    private int raLevel = -1;
    private String realName;
    private int redPackNum;
    private SettingsBean settings;
    private double totalAssets;
    private int type;
    private int beginnerTask = -1; //0-注册 1-开户 2-首投
    private String messagePushCode;

    public String getMessagePushCode() {
        return messagePushCode;
    }

    public void setMessagePushCode(String messagePushCode) {
        this.messagePushCode = messagePushCode;
    }

    public int getBeginnerTask() {
        return beginnerTask;
    }

    public void setBeginnerTask(int beginnerTask) {
        this.beginnerTask = beginnerTask;
    }

    public double getAccumlatedProfit() {
        return accumlatedProfit;
    }

    public void setAccumlatedProfit(double accumlatedProfit) {
        this.accumlatedProfit = accumlatedProfit;
    }

    public double getBalanceAvailable() {
        return balanceAvailable;
    }

    public void setBalanceAvailable(double balanceAvailable) {
        this.balanceAvailable = balanceAvailable;
    }

    public BankCardBean getBankCard() {
        return bankCard;
    }

    public void setBankCard(BankCardBean bankCard) {
        this.bankCard = bankCard;
    }

    public int getCouponNum() {
        return couponNum;
    }

    public void setCouponNum(int couponNum) {
        this.couponNum = couponNum;
    }

    public int getDebtNum() {
        return debtNum;
    }

    public void setDebtNum(int debtNum) {
        this.debtNum = debtNum;
    }

    public double getHistoryProfit() {
        return historyProfit;
    }

    public void setHistoryProfit(double historyProfit) {
        this.historyProfit = historyProfit;
    }

    public String getIdcardNo() {
        return idcardNo;
    }

    public void setIdcardNo(String idcardNo) {
        this.idcardNo = idcardNo;
    }

    public int getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(int depositStatus) {
        this.depositStatus = depositStatus;
    }

    public int getMyInvestment() {
        return myInvestment;
    }

    public void setMyInvestment(int myInvestment) {
        this.myInvestment = myInvestment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRaLevel() {
        return raLevel;
    }

    public void setRaLevel(int raLevel) {
        this.raLevel = raLevel;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getRedPackNum() {
        return redPackNum;
    }

    public void setRedPackNum(int redPackNum) {
        this.redPackNum = redPackNum;
    }

    public SettingsBean getSettings() {
        return settings;
    }

    public void setSettings(SettingsBean settings) {
        this.settings = settings;
    }

    public double getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(double totalAssets) {
        this.totalAssets = totalAssets;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public static class BankCardBean {
        /**
         * depositBankCardNo : string
         * depositBankName : string
         */

        private String depositBankCardNo;
        private String depositBankName;

        public String getDepositBankCardNo() {
            return depositBankCardNo;
        }

        public void setDepositBankCardNo(String depositBankCardNo) {
            this.depositBankCardNo = depositBankCardNo;
        }

        public String getDepositBankName() {
            return depositBankName;
        }

        public void setDepositBankName(String depositBankName) {
            this.depositBankName = depositBankName;
        }
    }

    public static class SettingsBean {
        /**
         * ifGesture : false
         * ifTouchId : false
         */

        private boolean ifGesture;
        private boolean ifTouchId;

        public boolean isIfGesture() {
            return ifGesture;
        }

        public void setIfGesture(boolean ifGesture) {
            this.ifGesture = ifGesture;
        }

        public boolean isIfTouchId() {
            return ifTouchId;
        }

        public void setIfTouchId(boolean ifTouchId) {
            this.ifTouchId = ifTouchId;
        }
    }
}
