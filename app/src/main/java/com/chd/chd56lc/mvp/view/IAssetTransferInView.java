package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.AssetTransferInBean;

import java.util.List;

/**
 * 持有资产转让view
 */
public interface IAssetTransferInView extends LoadingView {

    /**
     * 设置我的资产转让中
     *
     * @param assetTranferInBean
     */
    void setAssetTransferIn(List<AssetTransferInBean> assetTranferInBean);

    /**
     * 取消转让结果
     *
     * @param result
     */
    void cancelResult(boolean result);
}
