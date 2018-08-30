package com.chd.chd56lc.entity;

import java.util.List;

public class AssetInvestOverBean {

    /**
     * array : [{"bidName":"string","comprehensiveAnnualRate":0,"currentAmount":0,"investTime":"string","label":0,"otherRate":0,"paymentTime":"string","status":0,"transferTime":"string"}]
     * count : 0
     * extras : {}
     * offset : 0
     * page : 0
     * total : 0
     * totalPageNo : 0
     */

    private int count;
    private int offset;
    private int page;
    private int total;
    private int totalPageNo;
    private List<Item> array;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPageNo() {
        return totalPageNo;
    }

    public void setTotalPageNo(int totalPageNo) {
        this.totalPageNo = totalPageNo;
    }

    public List<Item> getArray() {
        return array;
    }

    public void setArray(List<Item> array) {
        this.array = array;
    }

    public static class Item {
        /**
         * annualRate	number
         * 协议约定年化利率
         * <p>
         * bidName	string
         * 标的名称
         * <p>
         * comprehensiveAnnualRate	number
         * createdAt	string($date-time)
         * cumulativeIncome	number
         * 累计收益
         * <p>
         * currentAmount	number
         * 在持金额
         * <p>
         * debtName	string
         * 债转名称
         * <p>
         * expiryDay	integer($int32)
         * 到期时间（天）
         * <p>
         * expiryTime	string($date)
         * 到期日
         * <p>
         * id	integer($int64)
         * investTime	string($date)
         * 投资日
         * <p>
         * label	integer($int32)
         * 特点标签
         * <p>
         * otherRate	number
         * 额外加息利率
         * <p>
         * paymentTime	string($date)
         * 放款日
         * <p>
         * status	integer($int32)
         * 状态；1：已回款，2：已转让
         * <p>
         * transferTime	string($date)
         * 转让日
         * <p>
         * type	integer($int32)
         * 产品类型，1：无忧产品，2：债权转让
         * <p>
         * updatedAt	string($date-time)
         */

        private double annualRate;
        private String bidName;
        private double comprehensiveAnnualRate;
        private String createdAt;
        private double cumulativeIncome;
        private double currentAmount;
        private String debtName;
        private int expiryDay;
        private String expiryTime;
        private String id;
        private String investTime;
        private int label;
        private double otherRate;
        private String paymentTime;
        private int status;
        private String transferTime;
        private int type;
        private String updatedAt;

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

        public double getComprehensiveAnnualRate() {
            return comprehensiveAnnualRate;
        }

        public void setComprehensiveAnnualRate(double comprehensiveAnnualRate) {
            this.comprehensiveAnnualRate = comprehensiveAnnualRate;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
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

        public String getDebtName() {
            return debtName;
        }

        public void setDebtName(String debtName) {
            this.debtName = debtName;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTransferTime() {
            return transferTime;
        }

        public void setTransferTime(String transferTime) {
            this.transferTime = transferTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
        /**
         * annualRate	number
         * 协议约定年化利率
         * <p>
         * bidName	string
         * 标的名称
         * <p>
         * createdAt	string($date-time)
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
         */

    }


}
