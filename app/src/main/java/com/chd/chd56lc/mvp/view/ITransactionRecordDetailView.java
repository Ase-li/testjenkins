package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.TransactionRecordBean;

/**
 * 交易记录详情
 */
public interface ITransactionRecordDetailView extends LoadingView {
    void updateTransRecordDetail(TransactionRecordBean transactionRecordBean);
}
