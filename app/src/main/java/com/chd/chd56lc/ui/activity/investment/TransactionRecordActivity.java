package com.chd.chd56lc.ui.activity.investment;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.TransactionRecordBean;
import com.chd.chd56lc.mvp.presenter.TransactionRecordPresenter;
import com.chd.chd56lc.mvp.view.ITransactionRecordDetailView;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.NewTwoSideTextView;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class TransactionRecordActivity extends BaseActivity implements ITransactionRecordDetailView {

    public static final String TYPE = "type";
    public static final String ORDER_ID = "order_id";

    @Named("transactionRecordDetail")
    @Inject
    TransactionRecordPresenter transactionRecordPresenter;
    @BindView(R.id.ntv_trans_type)
    NewTwoSideTextView ntvTransType;
    @BindView(R.id.ntv_trans_object)
    NewTwoSideTextView ntvTransObject;
    @BindView(R.id.ntv_trans_money)
    NewTwoSideTextView ntvTransMoney;
    @BindView(R.id.ntv_trans_time)
    NewTwoSideTextView ntvTransTime;
    @BindView(R.id.ntv_verify_time)
    NewTwoSideTextView ntvVerifyTime;
    @BindView(R.id.ntv_trans_status)
    NewTwoSideTextView ntvTransStatus;

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_transaction_record;
    }

    @Override
    public void initView() {
        setTitle("交易记录");
        transactionRecordPresenter.transactionDetail(getIntent().getStringExtra(ORDER_ID),
                getIntent().getIntExtra(TYPE, 0));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (transactionRecordPresenter!=null)
            transactionRecordPresenter.onUnsubscribe();
    }

    @Override
    public void updateTransRecordDetail(TransactionRecordBean transactionRecordBean) {
        ntvTransType.setValueText(transactionRecordBean.getTypeNameDetail());
        ntvTransObject.setValueText(transactionRecordBean.getTransactionObejt());
        ntvTransMoney.setValueText(transactionRecordBean.getAmount() + "元");
        ntvTransTime.setValueText(transactionRecordBean.getTransactionTime());
        ntvVerifyTime.setValueText(transactionRecordBean.getSuccessTime());
        ntvTransStatus.setValueText(transactionRecordBean.getStatusString2());
        ntvTransStatus.setValueTextColor(transactionRecordBean.getStatusColor());

    }

}
