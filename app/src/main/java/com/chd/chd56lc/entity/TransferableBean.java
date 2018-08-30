package com.chd.chd56lc.entity;

public class TransferableBean {

    /**
     * annualRate	number
     * 协议约定年化利率
     * <p>
     * bidName	string
     * 标的名称
     * <p>
     * createdAt	string($date-time)
     * 创建时间
     * <p>
     * cumulativeIncome	number
     * 累计收益
     * <p>
     * currentAmount	number
     * 在持金额
     * <p>
     * debtName	string
     * 债转名称
     * <p>
     * expireTime	string($date)
     * 到期日
     * <p>
     * id	integer($int64)
     * investDay	integer($int32)
     * 投资天数
     * <p>
     * investTime	string($date)
     * 投资日
     * <p>
     * label	integer($int32)
     * 特点标签；1：银行处理中，2：在持，3：可转让，4：x天后可转让，5：排队中：xx
     * <p>
     * otherRate	number
     * 额外加息利率
     * <p>
     * paymentTime	string($date)
     * 放款日
     * <p>
     * raiseStage	integer($int32)
     * 募集阶段；1：未放款，2：已放款，3：已到期
     * <p>
     * type	integer($int32)
     * 产品类型，1：无忧产品，2：债权转让
     * <p>
     * updatedAt	string($date-time)
     * 修改时间
     */
    public boolean isShow;
    private double annualRate;
    private String bidName;
    private double cumulativeIncome;
    private double currentAmount;
    private String expireTime;
    private String id;
    private int investDay;
    private String investTime;
    private int label;
    private double otherRate;
    private String paymentTime;
    private String projectName;
    private int type;
    private int raiseStage;

    public int getRaiseStage() {
        return raiseStage;
    }

    public void setRaiseStage(int raiseStage) {
        this.raiseStage = raiseStage;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public String getBidName() {
        return bidName;
    }

    public void setBidName(String bidName) {
        this.bidName = bidName;
    }

    public double getCumulativeIncome() {
        return cumulativeIncome;
    }

    public void setCumulativeIncome(double cumulativeIncome) {
        this.cumulativeIncome = cumulativeIncome;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInvestDay() {
        return investDay;
    }

    public void setInvestDay(int investDay) {
        this.investDay = investDay;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public double getOtherRate() {
        return otherRate;
    }

    public void setOtherRate(double otherRate) {
        this.otherRate = otherRate;
    }

    public String getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(String paymentTime) {
        this.paymentTime = paymentTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
