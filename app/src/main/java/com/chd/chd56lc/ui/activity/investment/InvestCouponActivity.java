package com.chd.chd56lc.ui.activity.investment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.CouponBean;
import com.chd.chd56lc.mvp.presenter.CouponPresenter;
import com.chd.chd56lc.mvp.view.IInvestCouponView;
import com.chd.chd56lc.ui.adapter.MyCouponAdapter;
import com.chd.chd56lc.ui.adapter.UsableCouponAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class InvestCouponActivity extends BaseActivity implements IInvestCouponView {
    public static final String BID_ID = "bid_id";
    /**
     * 场景：1：购买无忧产品，2：购买债转
     */
    public static final String SCENARIO = "scenario";
    /**
     * 优惠券
     */
    public static final String COUPON = "coupon";

    /**
     * 选中的优惠券ID
     */
    public static final String SELECTED_COUPON_ID = "selected_coupon_id";

    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    private List<CouponBean.Item> couponBeans;

    private UsableCouponAdapter myCouponAdapter;
    private int page = 1;
    private String selectId;
    @Named("investCoupon")
    @Inject
    CouponPresenter couponPresenter;

    @Override
    public void initDagger() {
        super.initDagger();
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (couponPresenter!=null)
            couponPresenter.onUnsubscribe();
    }

    @Override
    public void initView() {
        setTitle("可用优惠券");
        selectId = getIntent().getStringExtra(SELECTED_COUPON_ID);
        rcData.setVisibility(View.GONE);
        llNodata.setVisibility(View.VISIBLE);
        tvNoData.setText("暂无可用优惠券");
        if (couponBeans == null)
            couponBeans = new ArrayList<>();
        couponBeans.clear();
        rcData.setLayoutManager(new LinearLayoutManager(activity));
        myCouponAdapter = new UsableCouponAdapter(activity, couponBeans, selectId);
        myCouponAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                CouponBean.Item item = couponBeans.get(position);
                Intent intent = new Intent();
                if (StringUtils.isEmpty(selectId) || !selectId.equals(item.getId())) {
                    intent.putExtra(COUPON, item);
                }
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rcData.setAdapter(myCouponAdapter);
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.setEnableRefresh(false);
        initData();
    }

    private void initData() {
        int scenario = getIntent().getIntExtra(SCENARIO, -1);
        String bidId = getIntent().getStringExtra(BID_ID);
        int investMoney = getIntent().getIntExtra(InvestmentActivity.INVEST_MONEY, -1);
        if (scenario != -1 && !StringUtils.isEmpty(bidId) && investMoney != -1)
            couponPresenter.listAvailable(page, 20, scenario, bidId, investMoney);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_invest_coupon;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateCouponList(List<CouponBean.Item> couponBean) {
        if (couponBean != null && couponBean.size() > 0) {
            rcData.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
            couponBeans.addAll(couponBean);
        }
        if (couponBeans.size() <= 0) {
            rcData.setVisibility(View.GONE);
            llNodata.setVisibility(View.VISIBLE);
        }
        myCouponAdapter.notifyDataSetChanged();
    }

}