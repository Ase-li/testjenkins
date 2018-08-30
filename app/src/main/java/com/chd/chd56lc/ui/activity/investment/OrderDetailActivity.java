package com.chd.chd56lc.ui.activity.investment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.constant.Url;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.OrderDetailInfo;
import com.chd.chd56lc.mvp.presenter.OrderDetailPresenter;
import com.chd.chd56lc.mvp.view.IOrderDetailView;
import com.chd.chd56lc.ui.activity.web.CommonWebActivity;
import com.chd.chd56lc.ui.adapter.EarningsDetailAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.CustomToast;
import com.chd.chd56lc.widget.ScrollListView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class OrderDetailActivity extends BaseActivity implements IOrderDetailView {
    private static final int EARNING_TYPE_ZZ = 2; //债转
    private static final int EARNING_TYPE_NWY = 3;//年无忧
    private static final int EARNING_TYPE_JWY = 1;//季无忧

    public static final String ORDER_ID = "order_id";
    public static final String ORDER_TYPE = "order_type";
    @BindView(R.id.tv_cumulative_money)
    TextView tvCumulativeMoney;    // 债转与年无忧为 已下发累计收益  季无忧为 到期收益
    @BindView(R.id.tv1)
    TextView tvCumulativeMoneyType; // 债转与年无忧为 已下发累计收益  季无忧为 到期收益
    @BindView(R.id.tv_detail)
    TextView tvDetail;
    @BindView(R.id.tv_invest_money)
    TextView tvInvestMoney;         //投资金额
    @BindView(R.id.tv_expect_earnings)
    TextView tvExpectEarnings;      // 债转与年无忧为 预期收益  季无忧为 不显示
    @BindView(R.id.tv_text1)
    TextView tvText1;               // 变动内容
    @BindView(R.id.tv_text2)
    TextView tvText2;
    @BindView(R.id.tv_text3)
    TextView tvText3;
    @BindView(R.id.tv_text4)
    TextView tvText4;
    @BindView(R.id.tv_text5)
    TextView tvText5;
    @BindView(R.id.tv_text6)
    TextView tvText6;
    @BindView(R.id.tv_expect_earnings_hint)
    TextView tvExpectEarningsHint;  // 债转与年无忧为 预期收益  季无忧为 不显示
    @BindView(R.id.lv_detail)
    ScrollListView lvDetail;
    @BindView(R.id.vw_expect_earnings)
    View vwExpectEarnings;
    @BindView(R.id.ll_expect_earnings)
    LinearLayout llExpectEarnings;

    @Inject
    CustomToast toast;
    @Inject
    OrderDetailPresenter orderDetailPresenter;

    private String orderId;
    private List<OrderDetailInfo.BidStatusLogRespListBean> datas;
    private EarningsDetailAdapter earningsDetailAdapter;
    private String bidId;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orderDetailPresenter != null) {
            orderDetailPresenter.onUnsubscribe();
        }
    }

    /**
     * 返回一个用于设置界面的布局id
     */
    @Override
    public int loadLayoutResID() {
        return R.layout.activity_earnings_detail;
    }

    @Override
    public void initView() {
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
        initData();

    }

    /**
     * 初始化数据，并显示到界面上
     */
    public void initData() {
        orderId = getIntent().getStringExtra(ORDER_ID);
        setTitle("投资收益明细");
        setTitleBgColor(UIUtils.getColor(R.color.app_main_black));
        if (datas == null) {
            datas = new ArrayList<>();
        }
        earningsDetailAdapter = new EarningsDetailAdapter(this, datas);
        lvDetail.setAdapter(earningsDetailAdapter);
        loadData();
    }

    private void loadData() {
        orderDetailPresenter.bidDetail(orderId);
    }

    private void simpleText(TextView textView, String content) {
        SpannableString s = new SpannableString(content);
        s.setSpan(new ForegroundColorSpan(Color.parseColor("#F5A623")), content.indexOf("：") + 1, content.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(s);
    }

    @OnClick({R.id.tv_object_detail, R.id.tv_detail})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_object_detail:
                Intent productIntent = new Intent(activity, CommonWebActivity.class);
                productIntent.putExtra(CommonWebActivity.URL,
                        new StringBuilder(Url.BIDS_DETAIL)
                                .append("?bidId=").append(bidId)
                                .append("&orderId=").append(orderId).toString());
                startActivity(productIntent);
                break;
            case R.id.tv_detail:
                Intent intent = new Intent(activity, OrderDetailDetailActivity.class);
                intent.putExtra(ORDER_ID, orderId);
                startActivity(intent);
                break;

        }

    }

    @SuppressLint("SetTextI18n")
    public void setOrderDetail(OrderDetailInfo periodOrderDetailInfo) {
        bidId = periodOrderDetailInfo.getBidId();
        tvInvestMoney.setText(NumberFormalUtils.numberFormat(periodOrderDetailInfo.getAmount(), 2, 2) + "");
        simpleText(tvText1, "当前年化：" + NumberFormalUtils.percentFormat(periodOrderDetailInfo.getAnnualRate(), 2, 2));
        simpleText(tvText4, "使用红包：" + new BigDecimal(periodOrderDetailInfo.getCouponCashBonus()).setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
        switch (periodOrderDetailInfo.getType()) {
            case 2:
                tvText5.setVisibility(View.GONE);
                llExpectEarnings.setVisibility(View.VISIBLE);
                vwExpectEarnings.setVisibility(View.VISIBLE);
                tvCumulativeMoneyType.setText("已下发累计收益");
                simpleText(tvText2, "锁定期剩余时间：" + periodOrderDetailInfo.getLeftLockDay() + "天");
                tvExpectEarnings.setText(NumberFormalUtils.numberFormat(periodOrderDetailInfo.getExpectInterest(), 2, 2) + "");
                tvCumulativeMoney.setText(NumberFormalUtils.numberFormat(periodOrderDetailInfo.getSentInterest(), 2, 2) + "");
                simpleText(tvText3, periodOrderDetailInfo.getCouponInterestRateString());
                simpleText(tvText4, periodOrderDetailInfo.getPlatformInterestRateString());
                break;
            case 1:
                if (periodOrderDetailInfo.getInterestWay() == 1) {
                    llExpectEarnings.setVisibility(View.VISIBLE);
                    vwExpectEarnings.setVisibility(View.VISIBLE);
                    tvText5.setVisibility(View.VISIBLE);
                    tvCumulativeMoneyType.setText("已下发累计收益");
                    simpleText(tvText2, "锁定期剩余时间：" + periodOrderDetailInfo.getLeftLockDay() + "天");
                    tvExpectEarnings.setText(NumberFormalUtils.numberFormat(periodOrderDetailInfo.getExpectInterest(), 2, 2) + "");
                    tvCumulativeMoney.setText(NumberFormalUtils.numberFormat(periodOrderDetailInfo.getSentInterest(), 2, 2) + "");
                } else {
                    llExpectEarnings.setVisibility(View.GONE);
                    vwExpectEarnings.setVisibility(View.GONE);
                    tvText5.setVisibility(View.VISIBLE);
                    tvExpectEarningsHint.setText(UIUtils.getString(R.string.asset_expire_profit));
                    tvCumulativeMoneyType.setText(UIUtils.getString(R.string.asset_expire_profit1));
                    simpleText(tvText2, "锁定期剩余时间：--");
                    tvCumulativeMoney.setText(NumberFormalUtils.numberFormat(periodOrderDetailInfo.getExpectInterest(), 2, 2) + "");
                    tvDetail.setVisibility(View.GONE);
                }
                simpleText(tvText3, periodOrderDetailInfo.getRaiseRateString());
                simpleText(tvText5, periodOrderDetailInfo.getCouponInterestRateString());
                simpleText(tvText6, periodOrderDetailInfo.getPlatformInterestRateString());

                break;
        }
        if (periodOrderDetailInfo.getBidStatusLogRespList() != null && periodOrderDetailInfo.getBidStatusLogRespList().size() > 0) {
            datas.addAll(periodOrderDetailInfo.getBidStatusLogRespList());
            earningsDetailAdapter.notifyDataSetChanged();
        }

    }
}
