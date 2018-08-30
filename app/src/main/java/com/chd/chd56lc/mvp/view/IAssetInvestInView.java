package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.AssetInvestInBean;

import java.util.List;

/**
 * 持有资产view
 */
public interface IAssetInvestInView extends LoadingView {
    /**
     * 设置我的资产持有
     *
     * @param assetInvestIn
     */
    void setAssetInvestIn(List<AssetInvestInBean> assetInvestIn);


}
