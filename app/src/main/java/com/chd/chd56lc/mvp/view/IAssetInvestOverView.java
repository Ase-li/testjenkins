package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.AssetInvestOverBean;

/**
 * 持有资产转让
 */
public interface IAssetInvestOverView extends LoadingView {
    /**
     * 设置我的资产已完成
     *
     * @param assetInvestOverBean
     */
    void setAssetInvestOver(AssetInvestOverBean assetInvestOverBean);

}
