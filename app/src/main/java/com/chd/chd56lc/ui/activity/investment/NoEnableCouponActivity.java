package com.chd.chd56lc.ui.activity.investment;

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
import com.chd.chd56lc.mvp.view.ICouponView;
import com.chd.chd56lc.ui.adapter.MyCouponAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class NoEnableCouponActivity extends BaseActivity implements ICouponView {

    public static final String TYPE = "type";
    private int type;   // 1:已使用优惠券，2：已过期

    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    @Named("Coupon")
    @Inject
    CouponPresenter couponPresenter;

    private List<CouponBean.Item> couponBeans;
    private MyCouponAdapter myCouponAdapter;
    private int page = 1;
    private int useStatus;

    @Override
    public void initDagger() {
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    public void initView() {
        rcData.setVisibility(View.GONE);
        llNodata.setVisibility(View.VISIBLE);

        type = getIntent().getIntExtra(TYPE, 0);
        if (type == 2) {
            setTitle("已过期优惠券");
            tvNoData.setText("暂无已过期优惠券");
            useStatus = 2;
        } else {
            setTitle("已使用优惠券");
            tvNoData.setText("暂无已使用优惠券");
            useStatus = 1;
        }
        if (couponBeans == null)
            couponBeans = new ArrayList<>();
        couponBeans.clear();
        rcData.setLayoutManager(new LinearLayoutManager(activity));
        myCouponAdapter = new MyCouponAdapter(activity, couponBeans);
        rcData.setAdapter(myCouponAdapter);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                couponBeans.clear();
                initData();
            }
        });
        smartRefresh.autoRefresh();
    }

    private void initData() {
        couponPresenter.paginateByStatus(page, 20, useStatus);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_no_enable_coupon;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (couponPresenter != null)
            couponPresenter.onUnsubscribe();
    }

    @Override
    public void updateCouponList(CouponBean couponBean) {
        if (couponBean != null && couponBean.getArray() != null && couponBean.getArray().size() > 0) {
            rcData.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
            couponBeans.addAll(couponBean.getArray());
            if (couponBean.getPage() == couponBean.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            } else {
                smartRefresh.setEnableLoadMore(true);
            }
        }
        if (couponBeans.size() <= 0) {
            rcData.setVisibility(View.GONE);
            llNodata.setVisibility(View.VISIBLE);
            smartRefresh.setEnableLoadMore(false);
        } else {
            smartRefresh.setEnableLoadMore(true);
        }
        myCouponAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isRefreshing())
            smartRefresh.finishRefresh();
        if (smartRefresh.isLoading())
            smartRefresh.finishLoadMore();
    }
}
