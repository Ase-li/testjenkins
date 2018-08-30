package com.chd.chd56lc.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 每月结息收益
 * Created by li on 2018/2/9.
 */

public class PeriodOrderIncomeInfo implements Serializable {

    /**
     * order_id : 151340348302152886
     * bids_id : 4
     * bids_name : 涨无忧
     * bids_company_id : 4
     * yearrate : 0.086
     * lock_remain_days : 30
     * total_income : 100
     * total_income_str : 100
     * invest_money : 10000
     * invest_money_str : 10,000.00
     * raise_yearrate : 0.08
     * red_money : 100
     * coupon_raise_rate : 0.01
     * increase_yearrate : 0.01
     * income_list : [{"income_name":"已收利息","income_time":"2018.01.01","income_time_name":"2018.01.01","income_money":10,"income_money_desc":"10.00元"},{"income_name":"已收利息","income_time":"2018.02.01","income_time_name":"2018.02.01","income_money":10,"income_money_desc":"10.00元"},{"income_name":"已收利息","income_time":"2018.03.01","income_time_name":"2018.03.01","income_money":10,"income_money_desc":"10.00元"},{"income_name":"已收利息","income_time":"2018.04.01","income_time_name":"2018.04.01","income_money":10,"income_money_desc":"10.00元"}]
     */

    private String order_id;
    private String bids_id;
    private String bids_name;
    private String bids_company_id;
    private String yearrate;
    private String lock_remain_days;
    private String total_income;
    private String total_income_str;
    private String invest_money;
    private String invest_money_str;
    private String raise_yearrate;
    private String red_money;
    private String coupon_raise_rate;
    private String increase_yearrate;
    private List<IncomeListBean> income_list;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBids_id() {
        return bids_id;
    }

    public void setBids_id(String bids_id) {
        this.bids_id = bids_id;
    }

    public String getBids_name() {
        return bids_name;
    }

    public void setBids_name(String bids_name) {
        this.bids_name = bids_name;
    }

    public String getBids_company_id() {
        return bids_company_id;
    }

    public void setBids_company_id(String bids_company_id) {
        this.bids_company_id = bids_company_id;
    }

    public String getYearrate() {
        return yearrate;
    }

    public void setYearrate(String yearrate) {
        this.yearrate = yearrate;
    }

    public String getLock_remain_days() {
        return lock_remain_days;
    }

    public void setLock_remain_days(String lock_remain_days) {
        this.lock_remain_days = lock_remain_days;
    }

    public String getTotal_income() {
        return total_income;
    }

    public void setTotal_income(String total_income) {
        this.total_income = total_income;
    }

    public String getTotal_income_str() {
        return total_income_str;
    }

    public void setTotal_income_str(String total_income_str) {
        this.total_income_str = total_income_str;
    }

    public String getInvest_money() {
        return invest_money;
    }

    public void setInvest_money(String invest_money) {
        this.invest_money = invest_money;
    }

    public String getInvest_money_str() {
        return invest_money_str;
    }

    public void setInvest_money_str(String invest_money_str) {
        this.invest_money_str = invest_money_str;
    }

    public String getRaise_yearrate() {
        return raise_yearrate;
    }

    public void setRaise_yearrate(String raise_yearrate) {
        this.raise_yearrate = raise_yearrate;
    }

    public String getRed_money() {
        return red_money;
    }

    public void setRed_money(String red_money) {
        this.red_money = red_money;
    }

    public String getCoupon_raise_rate() {
        return coupon_raise_rate;
    }

    public void setCoupon_raise_rate(String coupon_raise_rate) {
        this.coupon_raise_rate = coupon_raise_rate;
    }

    public String getIncrease_yearrate() {
        return increase_yearrate;
    }

    public void setIncrease_yearrate(String increase_yearrate) {
        this.increase_yearrate = increase_yearrate;
    }

    public List<IncomeListBean> getIncome_list() {
        return income_list;
    }

    public void setIncome_list(List<IncomeListBean> income_list) {
        this.income_list = income_list;
    }

    public static class IncomeListBean {
        /**
         * income_name : 已收利息
         * income_time : 2018.01.01
         * income_time_name : 2018.01.01
         * income_money : 10
         * income_money_desc : 10.00元
         */

        private String income_name;
        private String income_time;
        private String income_time_name;
        private String income_money;
        private String income_money_desc;
        private float income_yearrate;

        public float getIncome_yearrate() {
            return income_yearrate;
        }

        public void setIncome_yearrate(float income_yearrate) {
            this.income_yearrate = income_yearrate;
        }

        public String getIncome_name() {
            return income_name;
        }

        public void setIncome_name(String income_name) {
            this.income_name = income_name;
        }

        public String getIncome_time() {
            return income_time;
        }

        public void setIncome_time(String income_time) {
            this.income_time = income_time;
        }

        public String getIncome_time_name() {
            return income_time_name;
        }

        public void setIncome_time_name(String income_time_name) {
            this.income_time_name = income_time_name;
        }

        public String getIncome_money() {
            return income_money;
        }

        public void setIncome_money(String income_money) {
            this.income_money = income_money;
        }

        public String getIncome_money_desc() {
            return income_money_desc;
        }

        public void setIncome_money_desc(String income_money_desc) {
            this.income_money_desc = income_money_desc;
        }
    }
}
