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
import com.chd.chd56lc.entity.AssetInvestOverBean;
import com.chd.chd56lc.mvp.presenter.AssetInvestPresenter;
import com.chd.chd56lc.mvp.view.IAssetInvestOverView;
import com.chd.chd56lc.ui.activity.investment.OrderDetailActivity;
import com.chd.chd56lc.ui.adapter.InvestOverAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的投资已完成
 */
public class InvestOverFragment extends BaseFragment implements IAssetInvestOverView {


    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    @Named("investOver")
    @Inject
    AssetInvestPresenter assetInvestPresenter;
    @Inject
    CustomToast toast;
    @BindView(R.id.tv_select_type)
    TextView tvSelectType;
    @BindView(R.id.tv_select_month)
    TextView tvSelectMonth;

    private List<AssetInvestOverBean.Item> assetOverItems;
    private InvestOverAdapter investOverAdapter;
    private int type;
    private int count;
    private int page = 1;

    public InvestOverFragment() {
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this))
                .build().inject(this);
    }


    @Override
    protected void initView() {
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("当前无已完成标的");
        if (assetOverItems == null)
            assetOverItems = new ArrayList<>();
        assetOverItems.clear();
        rcData.setLayoutManager(new LinearLayoutManager(context));
        investOverAdapter = new InvestOverAdapter(context, assetOverItems);
        investOverAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                toActivity(new Intent(context, OrderDetailActivity.class)
                        .putExtra(OrderDetailActivity.ORDER_ID, assetOverItems.get(position).getId()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rcData.setAdapter(investOverAdapter);
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout smartRefresh) {
                page++;
                loadData();
            }

            @Override
            public void onRefresh(RefreshLayout smartRefresh) {
                page = 1;
                assetOverItems.clear();
                loadData();
            }
        });
        smartRefresh.autoRefresh();
    }

    private void loadData() {
        assetInvestPresenter.listFinishOrder(page, 10, type);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_invest_over;
    }

    /**
     * 设置我的资产持有
     *
     * @param assetInvestOverBean
     */
    @Override
    public void setAssetInvestOver(AssetInvestOverBean assetInvestOverBean) {
        if (assetInvestOverBean != null && assetInvestOverBean.getArray() != null && assetInvestOverBean.getArray().size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            assetOverItems.addAll(assetInvestOverBean.getArray());
            if (assetInvestOverBean.getPage() == assetInvestOverBean.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            } else {
                smartRefresh.setEnableLoadMore(true);
            }
        }
        if (assetOverItems.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
            smartRefresh.setEnableLoadMore(false);
        } else {
            smartRefresh.setEnableLoadMore(true);
        }
        investOverAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isRefreshing()) {
            smartRefresh.finishRefresh();
        }
        if (smartRefresh.isLoading()) {
            smartRefresh.finishLoadMore();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (assetInvestPresenter != null)
            assetInvestPresenter.onUnsubscribe();
    }

    @OnClick({R.id.tv_select_type, R.id.tv_select_month})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_select_type:
//                new SelectTypeWindow(context).showLocation(tvSelectType);
                break;
            case R.id.tv_select_month:
//                new SelectDateWindow(context).showLocation(tvSelectMonth);
                break;
        }
    }
}
