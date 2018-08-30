package com.chd.chd56lc.entity;

public class CouponMsgBean {

    /**
     * desc	string
     * 描述
     * <p>
     * type	integer($int32)
     * 类型,2 加息券,4 抵扣红包
     */

    private String desc;
    private int type;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
