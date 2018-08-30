package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.TransferableBean;

import java.util.List;

/**
 * 可转让列表view
 */
public interface ITransferableViewInView extends LoadingView {
    /**
     * 设置可转让列表
     *
     * @param transferableList
     */
    void setTransferableList(List<TransferableBean> transferableList);


}
