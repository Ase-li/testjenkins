package com.chd.chd56lc.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 跳转存管页面实体类
 */
public class DepositLinkBean implements Parcelable {

    /**
     * failUrl : string
     * orderId : string
     * successUrl : string
     * url : string
     */

    private String failUrl;
    private int depositStatus;//存管状态 0-未开通 1-未绑卡 2-未设置密码 3-存管信息完整 4-开通中
    private String successUrl;
    private String url;
    private String orderId;

    protected DepositLinkBean(Parcel in) {
        failUrl = in.readString();
        depositStatus = in.readInt();
        successUrl = in.readString();
        url = in.readString();
        orderId = in.readString();
    }

    public static final Creator<DepositLinkBean> CREATOR = new Creator<DepositLinkBean>() {
        @Override
        public DepositLinkBean createFromParcel(Parcel in) {
            return new DepositLinkBean(in);
        }

        @Override
        public DepositLinkBean[] newArray(int size) {
            return new DepositLinkBean[size];
        }
    };

    public String getFailUrl() {
        return failUrl;
    }

    public void setFailUrl(String failUrl) {
        this.failUrl = failUrl;
    }

    public int getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(int depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getSuccessUrl() {
        return successUrl;
    }

    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(failUrl);
        dest.writeInt(depositStatus);
        dest.writeString(successUrl);
        dest.writeString(url);
        dest.writeString(orderId);
    }
}
