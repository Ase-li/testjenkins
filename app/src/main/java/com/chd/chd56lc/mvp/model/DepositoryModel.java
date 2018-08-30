package com.chd.chd56lc.mvp.model;

import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.BaseBean;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.DepositoryResultBean;
import com.chd.chd56lc.entity.SignDebtStatusBean;
import com.chd.chd56lc.net.api.DepositoryApi;

import io.reactivex.Observable;

public class DepositoryModel {
    private DepositoryApi depositoryResultApi;

    public DepositoryModel() {
        depositoryResultApi = BaseApplication.getAppComponent().retrofit().create(DepositoryApi.class);
    }

    /**
     * 投资成功重定向页面
     *
     * @param orderId
     * @return
     */
    public Observable<BaseBean> bidApplyPSuccessPage(String orderId) {
        return depositoryResultApi.bidApplyPSuccessPage(orderId);
    }

    /**
     * 购买债权成功重定向页面
     *
     * @param orderId
     * @return
     */
    public Observable<BaseBean> buyCreditPSuccessPage(String orderId) {
        return depositoryResultApi.buyCreditPSuccessPage(orderId);
    }

    /**
     * 提现预扣
     *
     * @param orderId
     * @return
     */
    public Observable<BaseBean<DepositoryResultBean>> preDeductLocalAmount(String orderId) {
        return depositoryResultApi.preDeductLocalAmount(orderId);
    }

    /**
     * 充值成功更新订单状态
     *
     * @param orderId
     * @return
     */
    public Observable<BaseBean<DepositoryResultBean>> updateOrderStatus(String orderId) {
        return depositoryResultApi.updateOrderStatus(orderId);
    }

    /**
     * 进行债券转让签约
     *
     * @return
     */
    public Observable<BaseBean<DepositLinkBean>> signTransferP() {
        return depositoryResultApi.signTransferP();
    }

    /**
     * 修改债转签约状态为处理中
     *
     * @return
     */
    public Observable<BaseBean> updateSignDebtStatus() {
        return depositoryResultApi.updateSignDebtStatus();
    }

    /**
     * 查询债转签约状态
     *
     * @return
     */
    public Observable<BaseBean<SignDebtStatusBean>> getSignDebtStatus() {
        return depositoryResultApi.getSignDebtStatus();
    }
}
