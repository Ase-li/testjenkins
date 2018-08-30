package com.chd.chd56lc.entity;

public class SignDebtStatusBean {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * 签约状态,0 未签约,1 已签约,2 处理中
     */
    public String getStatusString() {
        switch (status) {
            case 0:
                return "未签约";
            case 1:
                return "已签约";
            case 2:
                return "处理中";
        }
        return "";
    }
}
