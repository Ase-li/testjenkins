package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.AssetTransferBean;

import java.util.List;

/**
 * 转让确认view
 */
public interface IInvestmentTransVerifyView extends LoadingView {
    /**
     * 设置转让列表
     *
     * @param assetTransferBeans
     */
    void setTransferPageInfo(List<AssetTransferBean> assetTransferBeans);

    /**
     * 转让确认
     */
    void transferVerify();
}
