package com.chd.chd56lc.entity;

//项目详情

/**
 * annualRate	number
 * 预期年化利率
 * <p>
 * canInvestAmount	number
 * 可投金额
 * <p>
 * createdAt	string($date-time)
 * 创建时间
 * <p>
 * expiryDay	integer($int32)
 * 到期时间（天）
 * <p>
 * expiryTime	string($date)
 * 到期时间（日期）
 * <p>
 * id	integer($int64)
 * interestCycle	integer($int32)
 * 派息周期
 * <p>
 * interestWay	integer($int32)
 * 派息方式，1：定期付息，2：到期付息
 * <p>
 * investmentAmount	number
 * 起投额（元）
 * <p>
 * labelFirst	string
 * 标签1
 * <p>
 * labelSecond	string
 * 标签2
 * <p>
 * labelThird	string
 * 标签3
 * <p>
 * loanAmount	number
 * 融资金额
 * <p>
 * lockDay	integer($int32)
 * 锁定期限（天）
 * <p>
 * name	string
 * 名称
 * <p>
 * projectId	integer($int64)
 * 项目id
 * <p>
 * projectName	string
 * 项目名称
 * <p>
 * raiseRate	number
 * 募集期利率
 * <p>
 * raisedAmount	number
 * 已募集金额
 * <p>
 * raisedPercent	number
 * 已募集
 * <p>
 * term	integer($int32)
 * 最大投资期限（天）
 * <p>
 * transferDay	integer($int32)
 * 该天数后可转让
 * <p>
 * type	integer($int32)
 * 产品类型，1：无忧产品，2：债权转让
 * <p>
 * updatedAt	string($date-time)
 * 修改时间
 * ifTransfer
 * 是否能转让；false：否，ture：是
 */
public class ProjectDetailBean {
    private double annualRate;
    private double canInvestAmount;
    private int expiryDay;
    private String expiryTime;
    private String id;
    private double investmentAmount;
    private double loanAmount;
    private int lockDay;
    private String name;
    private String projectId;
    private double raiseRate;
    private double raisedAmount;
    private double raisedPercent;
    private int transferDay;
    private int type;
    private int term; //期限
    private double userBalanceAmount;
    private String labelFirst;
    private String labelSecond;
    private String labelThird;
    private int interestWay;//派息方式，1：定期付息，2：到期付息 作为年无忧季无忧的判断依据
    private int interestCycle;//派息周期
    private boolean ifTransfer; //false：否，ture：是

    /**
     * @return 付息方式
     */
    public String getInterestWayString() {
        switch (interestWay) {
            case 1:
                return "定期付息";
            case 2:
                return "到期付息";
        }
        return "";
    }

    /**
     * @return 是否可转
     */
    public String getCanTransferString() {
        if (ifTransfer) return transferDay + "天后可转";
        else return "不可转让";
    }

    public boolean isIfTransfer() {
        return ifTransfer;
    }

    public void setIfTransfer(boolean ifTransfer) {
        this.ifTransfer = ifTransfer;
    }

    public int getInterestWay() {
        return interestWay;
    }

    public void setInterestWay(int interestWay) {
        this.interestWay = interestWay;
    }

    public String getLabelFirst() {
        return labelFirst;
    }

    public void setLabelFirst(String labelFirst) {
        this.labelFirst = labelFirst;
    }

    public int getInterestCycle() {
        return interestCycle;
    }

    public void setInterestCycle(int interestCycle) {
        this.interestCycle = interestCycle;
    }

    public String getLabelSecond() {
        return labelSecond;
    }

    public void setLabelSecond(String labelSecond) {
        this.labelSecond = labelSecond;
    }

    public String getLabelThird() {
        return labelThird;
    }

    public void setLabelThird(String labelThird) {
        this.labelThird = labelThird;
    }

    public double getUserBalanceAmount() {
        return userBalanceAmount;
    }

    public void setUserBalanceAmount(double userBalanceAmount) {
        this.userBalanceAmount = userBalanceAmount;
    }

    public int getTerm() {
        return term;
    }

    public void setTerm(int term) {
        this.term = term;
    }

    public double getAnnualRate() {
        return annualRate;
    }

    public void setAnnualRate(double annualRate) {
        this.annualRate = annualRate;
    }

    public int getExpiryDay() {
        return expiryDay;
    }

    public void setExpiryDay(int expiryDay) {
        this.expiryDay = expiryDay;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public int getLockDay() {
        return lockDay;
    }

    public void setLockDay(int lockDay) {
        this.lockDay = lockDay;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public double getRaiseRate() {
        return raiseRate;
    }

    public void setRaiseRate(double raiseRate) {
        this.raiseRate = raiseRate;
    }

    public double getRaisedAmount() {
        return raisedAmount;
    }

    public void setRaisedAmount(double raisedAmount) {
        this.raisedAmount = raisedAmount;
    }

    public double getRaisedPercent() {
        return raisedPercent;
    }

    public void setRaisedPercent(double raisedPercent) {
        this.raisedPercent = raisedPercent;
    }

    public int getTransferDay() {
        return transferDay;
    }

    public void setTransferDay(int transferDay) {
        this.transferDay = transferDay;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCanInvestAmount() {
        return (int) canInvestAmount;
    }

    public void setCanInvestAmount(double canInvestAmount) {
        this.canInvestAmount = canInvestAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getInvestmentAmount() {
        return investmentAmount;
    }

    public void setInvestmentAmount(double investmentAmount) {
        this.investmentAmount = investmentAmount;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
