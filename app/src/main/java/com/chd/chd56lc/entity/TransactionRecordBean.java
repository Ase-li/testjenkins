package com.chd.chd56lc.entity;

import com.chd.chd56lc.R;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;

public class TransactionRecordBean {

    /**
     * amount	number
     * 交易金额
     * <p>
     * ifIncrease	boolean
     * example: false
     * false：减少，true：增加
     * <p>
     * orderId	integer($int64)
     * 订单id
     * <p>
     * status	integer($int32)
     * 交易状态；1：处理中，2：成功，3：失败
     * <p>
     * timestamp	integer($int64)
     * 时间戳
     * <p>
     * type	integer($int32)
     * 交易类型；1：充值，2：提现，3：收益，4：回款，5：现金奖励，6：投资
     */

    private double amount;
    private boolean ifIncrease;
    private String orderId;
    private int status;
    private long timestamp;
    private int type;
    private String successTime;
    private String transactionObejt;
    private String transactionTime;
    private String transactionDesc;

    public String getTransactionDesc() {
        return transactionDesc;
    }

    public void setTransactionDesc(String transactionDesc) {
        this.transactionDesc = transactionDesc;
    }

    public String getSuccessTime() {
        return successTime;
    }

    public void setSuccessTime(String successTime) {
        this.successTime = successTime;
    }

    public String getTransactionObejt() {
        return transactionObejt;
    }

    public void setTransactionObejt(String transactionObejt) {
        this.transactionObejt = transactionObejt;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isIfIncrease() {
        return ifIncrease;
    }

    public void setIfIncrease(boolean ifIncrease) {
        this.ifIncrease = ifIncrease;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getType() {
        return type;
    }

    public String getTypeNameDetail() {
        StringBuilder content = new StringBuilder();
        switch (type) {
            case 1:
                content.append("充值");
                break;
            case 2:
                content.append("提现");
                break;
            case 3:
                content.append("收益");
                break;
            case 4:
                content.append("回款");
                break;
            case 5:
                content.append("现金奖励");
                break;
            case 6:
                content.append("投资");
                break;
        }
        return content.toString();
    }

    public String getTypeName() {
        StringBuilder content = new StringBuilder();
        switch (type) {
            case 1:
                content.append("充值");
                break;
            case 2:
                content.append("提现");
                break;
            case 3:
                content.append("收益");
                break;
            case 4:
                content.append("回款");
                break;
            case 5:
                content.append("现金奖励");
                break;
            case 6:
                content.append("投资");
                break;
        }
        if (!StringUtils.isEmpty(transactionDesc))
            return content.append("-").append(transactionDesc).toString();
        else return content.toString();
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIncreaseIcon() {
        if (ifIncrease) {
            return R.mipmap.icon_jyjl_sjt;
        } else {
            return R.mipmap.icon_jyjl_xjt;
        }
    }

    public int getIncreaseColor() {
        if (ifIncrease) {
            return UIUtils.getColor(R.color.color_ff4e03);
        } else {
            return UIUtils.getColor(R.color.color_3ed2b1);
        }
    }

    public String getStatusString() {
        switch (status) {
            case 1:
                return "(处理中)";
            case 2:
                return "(成功)";
            case 3:
                return "(失败)";
        }
        return "";
    }

    public String getStatusString2() {
        switch (status) {
            case 1:
                return "处理中";
            case 2:
                return "成功";
            case 3:
                return "失败";
        }
        return "";
    }

    public int getStatusColor() {
        switch (status) {
            case 1:
                return UIUtils.getColor(R.color.color_333333);
            case 2:
                return UIUtils.getColor(R.color.color_3ed2b1);
            case 3:
                return UIUtils.getColor(R.color.color_ff4e03);
        }
        return UIUtils.getColor(R.color.color_3ed2b1);
    }
}
