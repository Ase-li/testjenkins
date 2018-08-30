package com.chd.chd56lc.entity;

import com.google.gson.annotations.SerializedName;

public class LoginResponseInfo {
    private int code;
    @SerializedName("userStatisticsResp")
    private UserInfoBean userInfo;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }
}
