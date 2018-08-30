package com.chd.chd56lc.mvp.view;

/**
 * 存管回调
 */
public interface IResultDepositView {
    /**
     * 充值回调
     *
     * @param statusCode 状态编码,1,成功,2.失败,4处理中
     */
    void rechargeResult(int statusCode);

    /**
     * 提现回调
     *
     * @param statusCode 状态编码,1,成功,2.失败,4处理中
     */
    void withDrawResult(int statusCode);

    /**
     * 投标回调
     *
     * @param statusCode 状态编码,1,成功,2.失败,4处理中
     */
    void investmentResult(int statusCode);

    /**
     * 购买债转回调
     *
     * @param statusCode 状态编码,1,成功,2.失败,4处理中
     */
    void transferResult(int statusCode);

    /**
     * 债转签约更改状态
     */
    void updateDebtStatus();

    /**
     * @param statusCode 签约状态,0 未签约,1 已签约,2 处理中
     */
    void debtStatus(int statusCode);
}
