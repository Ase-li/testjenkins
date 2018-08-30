package com.chd.chd56lc.entity;

import java.util.List;

public class AssetInvestInBean {

    /**
     * array : [{"annualRate":0,"bidName":"string","createdAt":"2018-06-27T03:35:23.093Z","cumulativeIncome":0,"currentAmount":0,"debtName":"string","expireTime":"string","id":0,"investDay":0,"investTime":"string","label":0,"otherRate":0,"paymentTime":"string","raiseStage":0,"type":0,"updatedAt":"2018-06-27T03:35:23.093Z"}]
     * projectName : string
     * totalAmount : 0
     */
    private boolean show;
    private String projectName;
    private int totalAmount;
    private List<Item> array;

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
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
         * 特点标签；特点标签；1：银行处理中，2：募集中，3：签约中，
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
         * <p>
         * raiseOrder	integer($int32)
         * 募集顺序
         * <p>
         * transferNeedDay	integer($int32)
         * xx天后可以转让
         * <p>
         * interestWay 1：定期付息，2：到期付息
         * transferLabel 特点标签；1：可转让，2：x天后可转让，3：排队中：xx，4：不可转让
         */

        private double annualRate;
        private String bidName;
        private String createdAt;
        private double cumulativeIncome;
        private int currentAmount;
        private String expireTime;
        private String id;
        private int investDay;
        private String investTime;
        private int label;
        private int transferLabel;
        private double otherRate;
        private String paymentTime;
        private int raiseStage;
        private int type;
        private int raiseOrder;
        private int transferNeedDay;
        private String updatedAt;
        private int interestWay;

        public int getTransferLabel() {
            return transferLabel;
        }

        public void setTransferLabel(int transferLabel) {
            this.transferLabel = transferLabel;
        }

        public int getInterestWay() {
            return interestWay;
        }

        public void setInterestWay(int interestWay) {
            this.interestWay = interestWay;
        }

        public int getRaiseOrder() {
            return raiseOrder;
        }

        public void setRaiseOrder(int raiseOrder) {
            this.raiseOrder = raiseOrder;
        }

        public int getTransferNeedDay() {
            return transferNeedDay;
        }

        public void setTransferNeedDay(int transferNeedDay) {
            this.transferNeedDay = transferNeedDay;
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

        public int getCurrentAmount() {
            return currentAmount;
        }

        public void setCurrentAmount(int currentAmount) {
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

        public String getLabelString() {
            //特点标签；1：银行处理中，2：募集中，3：签约中，4：x天后可转让，5：排队中：xx，6：签约中，7：回款中
            switch (label) {
                case 1:
                    return "银行处理中";
                case 2:
                    return "募集中";
                case 3:
                    return "签约中";
                case 4:
                    return "回款中";
            }
            return "";
        }

        /**
         * 当label==4时，标签2 显示下面内容
         *
         * @return
         */
        public String getLabel2String() {
            //特点标签；1：可转让，2：x天后可转让，3：排队中：xx，4：不可转让
            if (label == 4) {
                switch (transferLabel) {
                    case 1:
                        return "可转让";
                    case 2:
                        return transferNeedDay + "天后可转让";
                    case 3:
                        return "排队中：" + raiseOrder;
                    case 4:
                        return "不可转让";
                }
            }
            return "";
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

        public int getRaiseStage() {
            return raiseStage;
        }

        public void setRaiseStage(int raiseStage) {
            this.raiseStage = raiseStage;
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
    }
}
