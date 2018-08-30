package com.chd.chd56lc.ui.fragment;


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
import com.chd.chd56lc.entity.AssetTransferInBean;
import com.chd.chd56lc.mvp.presenter.AssetInvestPresenter;
import com.chd.chd56lc.mvp.view.IAssetTransferInView;
import com.chd.chd56lc.ui.activity.investment.OrderDetailActivity;
import com.chd.chd56lc.ui.adapter.InvestTransAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

/**
 * 我的资产转让中
 */
public class InvestTransferFragment extends BaseFragment implements IAssetTransferInView, InvestTransAdapter.OnclickCancelListener {


    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    @Named("transferIn")
    @Inject
    AssetInvestPresenter assetInvestPresenter;
    @Inject
    CustomToast toast;


    private InvestTransAdapter investTransAdapter;
    private List<AssetTransferInBean> assetTransferItems;

    public InvestTransferFragment() {
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }


    @Override
    protected void initView() {
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("当前无转让标的");
        if (assetTransferItems == null)
            assetTransferItems = new ArrayList<>();
        assetTransferItems.clear();
        rcData.setLayoutManager(new LinearLayoutManager(context));
        investTransAdapter = new InvestTransAdapter(context, assetTransferItems, this);
        investTransAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                toActivity(new Intent(context, OrderDetailActivity.class)
                        .putExtra(OrderDetailActivity.ORDER_ID, assetTransferItems.get(position).getId()));

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rcData.setAdapter(investTransAdapter);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                assetTransferItems.clear();
                loadData();
            }
        });
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.autoRefresh();
    }

    private void loadData() {
        assetInvestPresenter.listTransferingOrder();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_invest_transfer;
    }

    /**
     * 设置我的资产转让中
     *
     * @param assetTranferInBean
     */
    @Override
    public void setAssetTransferIn(List<AssetTransferInBean> assetTranferInBean) {
        if (assetTranferInBean != null && assetTranferInBean.size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            assetTransferItems.addAll(assetTranferInBean);
        }
        if (assetTransferItems.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        investTransAdapter.notifyDataSetChanged();
    }

    /**
     * 取消转让结果
     *
     * @param result
     */
    @Override
    public void cancelResult(boolean result) {
        if (result) {
            toast.setText("取消成功");
            smartRefresh.autoRefresh();
        }
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isRefreshing()) {
            smartRefresh.finishRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (assetInvestPresenter != null)
            assetInvestPresenter.onUnsubscribe();
    }

    @Override
    public void cancelTransfer(String id) {
        assetInvestPresenter.cancelTransfer(id);
    }
}
