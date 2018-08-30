package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.DepositLinkBean;
/**
 * 个人设置
 */
public interface IPersonSetView extends LoadingView {
    /**
     * 退出登录结果
     *
     * @param result
     */
    void loginOutResult(boolean result);

    /**
     * 设置交易密码
     *
     * @param depositLinkBean
     */
    void resetPasswordResult(DepositLinkBean depositLinkBean);

    /**
     * 开启手势结果
     *
     * @param result
     */
    void openGestureResult(boolean result);

    /**
     * 开启指纹
     *
     * @param result
     */
    void openTouchIdResult(boolean result);

}
