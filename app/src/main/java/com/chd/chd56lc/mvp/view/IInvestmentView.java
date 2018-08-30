package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.CouponNum;
import com.chd.chd56lc.entity.DepositLinkBean;
import com.chd.chd56lc.entity.InvestDetail;

/**
 * 投资页
 */
public interface IInvestmentView extends LoadingView {
    /**
     * 投资页详情
     *
     * @param investDetail
     */
    void updateInvestDetail(InvestDetail investDetail);

    /**
     * 投资回调
     *
     * @param depositLinkBean
     */
    void investment(DepositLinkBean depositLinkBean);

    /**
     * 优惠券数量
     *
     * @param couponNum
     */
    void couponNum(CouponNum couponNum);
}
