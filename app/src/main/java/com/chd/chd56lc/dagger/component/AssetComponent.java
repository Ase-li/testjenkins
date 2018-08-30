package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.investment.InvestCouponActivity;
import com.chd.chd56lc.ui.activity.investment.InvestTransferableActivity;
import com.chd.chd56lc.ui.activity.investment.InvestmentTransVerifyActivity;
import com.chd.chd56lc.ui.activity.investment.MyInvestmentActivity;
import com.chd.chd56lc.ui.activity.investment.NoEnableCouponActivity;
import com.chd.chd56lc.ui.activity.investment.OrderDetailActivity;
import com.chd.chd56lc.ui.activity.investment.OrderDetailDetailActivity;
import com.chd.chd56lc.ui.activity.investment.TransactionRecordActivity;
import com.chd.chd56lc.ui.activity.investment.TransactionRecordListActivity;
import com.chd.chd56lc.ui.fragment.CashRedEnvelopeFragment;
import com.chd.chd56lc.ui.fragment.InvestInFragment;
import com.chd.chd56lc.ui.fragment.InvestOverFragment;
import com.chd.chd56lc.ui.fragment.InvestTransferFragment;
import com.chd.chd56lc.ui.fragment.OtherCouponFragment;
import com.chd.chd56lc.ui.fragment.RateCouponFragment;
import com.chd.chd56lc.ui.fragment.TotalAssetFragment;
import com.chd.chd56lc.ui.fragment.TotalEarnFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = AssetModule.class)
public interface AssetComponent {
    void inject(InvestInFragment investInFragment);

    void inject(InvestOverFragment investOverFragment);

    void inject(InvestTransferFragment investTransferFragment);

    void inject(InvestTransferableActivity investTransferableActivity);

    void inject(InvestmentTransVerifyActivity investmentTransVerifyActivity);

    void inject(OrderDetailActivity orderDetailActivity);

    void inject(OrderDetailDetailActivity orderDetailDetailActivity);

    void inject(RateCouponFragment rateCouponFragment);

    void inject(OtherCouponFragment otherCouponFragment);

    void inject(CashRedEnvelopeFragment cashRedEnvelopeFragment);

    void inject(NoEnableCouponActivity noEnableCouponActivity);

    void inject(TotalAssetFragment totalAssetFragment);

    void inject(TotalEarnFragment totalEarnFragment);

    void inject(TransactionRecordListActivity transactionRecordListActivity);

    void inject(TransactionRecordActivity transactionRecordActivity);

    void inject(InvestCouponActivity investCouponActivity);

    void inject(MyInvestmentActivity myInvestmentActivity);
}
