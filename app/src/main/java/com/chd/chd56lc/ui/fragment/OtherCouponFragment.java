package com.chd.chd56lc.ui.fragment;


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
import com.chd.chd56lc.ui.base.BaseFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class OtherCouponFragment extends BaseFragment implements ICouponView {


    @BindView(R.id.tv_remark)
    TextView tvRemark;
    @BindView(R.id.tv_increase_money)
    TextView tvIncreaseMoney;
    @BindView(R.id.ll_remark)
    LinearLayout llRemark;
    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    private List<CouponBean.Item> couponBeans;
    private MyCouponAdapter myCouponAdapter;

    private int page = 1;

    @Named("Coupon")
    @Inject
    CouponPresenter couponPresenter;

    public OtherCouponFragment() {
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }


    @Override
    protected void initView() {
        llRemark.setVisibility(View.GONE);
        rcData.setVisibility(View.GONE);
        llNodata.setVisibility(View.VISIBLE);
        tvNoData.setText("暂无其他优惠券");
        if (couponBeans == null)
            couponBeans = new ArrayList<>();
        couponBeans.clear();
        rcData.setLayoutManager(new LinearLayoutManager(context));
        myCouponAdapter = new MyCouponAdapter(context, couponBeans);
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
//        initData();
        smartRefresh.autoRefresh();
    }

    private void initData() {
        couponPresenter.getCouponList(page, 20, 3);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_base_coupon;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (couponPresenter != null)
            couponPresenter.onUnsubscribe();
    }
}
