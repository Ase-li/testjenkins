package com.chd.chd56lc.manager;

import com.chd.chd56lc.constant.PreferenceConstant;
import com.chd.chd56lc.entity.UserInfoBean;


/**
 * Created by li on 2018/1/3.
 */

public class UserManager {
    private static UserManager mInstance;
    private UserInfoBean userInfo;

    public static synchronized UserManager getInstance() {
        if (mInstance == null) {
            mInstance = new UserManager();
        }
        return mInstance;
    }

    private UserManager() {
    }

    public boolean isFirstOpenApp() {
        return PreferenceManager.getBoolean(PreferenceConstant.FIRST_TIME_USE, true);
    }

    public boolean openApp() {
        return PreferenceManager.saveValue(PreferenceConstant.FIRST_TIME_USE, false);
    }

    public boolean isLogin() {
        return PreferenceManager.getBoolean(PreferenceConstant.USER_IS_LOGIN, false);
    }

    public boolean saveLogin(boolean isLogin) {
        return PreferenceManager.saveValue(PreferenceConstant.USER_IS_LOGIN, isLogin);
    }

    public UserManager saveUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
        PreferenceManager.saveValue(PreferenceConstant.PERSON_INFO, userInfo);
        return this;
    }

    public UserInfoBean getCurrentUserInfo() {
        if (userInfo == null) {
            userInfo = PreferenceManager.getObject(PreferenceConstant.PERSON_INFO, UserInfoBean.class);
        }
        return userInfo;
    }

    /**
     * 存管状态 0-未开通 1-未绑卡 2-未设置密码 3-存管信息完整 4-开通中
     *
     * @return
     */
    public int getDepositoryStatus() {
        return getCurrentUserInfo().getDepositStatus();
    }

}
