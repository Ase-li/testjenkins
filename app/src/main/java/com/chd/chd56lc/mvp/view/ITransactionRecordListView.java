package com.chd.chd56lc.mvp.view;

import com.chd.chd56lc.entity.TransactionRecordList;

/**
 * 交易记录列表
 */
public interface ITransactionRecordListView extends LoadingView {
    void updateTransRecordList(TransactionRecordList transactionRecordList);
}
