package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.AssetAnalyzeBean;

/**
 * 资产分析
 */
public interface IAssetAnalyzeView extends LoadingView {
    void updateAssetData(AssetAnalyzeBean assetAnalyzeBean);

}
