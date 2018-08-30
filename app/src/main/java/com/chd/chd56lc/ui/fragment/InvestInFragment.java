package com.chd.chd56lc.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.AssetInvestInBean;
import com.chd.chd56lc.mvp.presenter.AssetInvestPresenter;
import com.chd.chd56lc.mvp.view.IAssetInvestInView;
import com.chd.chd56lc.ui.activity.investment.OrderDetailActivity;
import com.chd.chd56lc.ui.adapter.InvestInAdapter;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * 我的投资在持
 */
public class InvestInFragment extends BaseFragment implements IAssetInvestInView {

    @BindView(R.id.elv_in_invest)
    ExpandableListView elvInInvest;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;

    @Inject
    AssetInvestPresenter assetInvestInPresenter;
    @Inject
    CustomToast toast;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private ArrayList<AssetInvestInBean> myInvestments;
    private InvestInAdapter investInAdapter;

    public InvestInFragment() {
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    protected void initView() {
        elvInInvest.setVisibility(View.GONE);
        llNodata.setVisibility(View.VISIBLE);
        tvNoData.setText("当前无在持标的");
        if (myInvestments == null)
            myInvestments = new ArrayList<>();
        myInvestments.clear();
        investInAdapter = new InvestInAdapter(myInvestments, context);
        elvInInvest.setAdapter(investInAdapter);
        elvInInvest.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                AssetInvestInBean myInvestment = myInvestments.get(groupPosition);
                if (myInvestment.isShow()) {
                    myInvestment.setShow(false);
                } else {
                    myInvestment.setShow(true);
                }
                investInAdapter.notifyDataSetChanged();
                return false;
            }
        });
        elvInInvest.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                toActivity(new Intent(context, OrderDetailActivity.class)
                        .putExtra(OrderDetailActivity.ORDER_ID, myInvestments.get(groupPosition).getArray().get(childPosition).getId()))
                ;
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                myInvestments.clear();
                loadData();
            }
        });
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.autoRefresh();
    }

    private void loadData() {
        assetInvestInPresenter.listMainCurrentOrder();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_invest_in;
    }


    /**
     * 设置我的资产持有
     *
     * @param assetInvestIns
     */
    @Override
    public void setAssetInvestIn(List<AssetInvestInBean> assetInvestIns) {
        if (assetInvestIns != null && assetInvestIns.size() > 0) {
            elvInInvest.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
            myInvestments.addAll(assetInvestIns);

        }
        if (myInvestments.size() <= 0) {
            elvInInvest.setVisibility(View.GONE);
            llNodata.setVisibility(View.VISIBLE);
        }
        investInAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (refreshLayout.isRefreshing()) {
            refreshLayout.finishRefresh();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (assetInvestInPresenter != null)
            assetInvestInPresenter.onUnsubscribe();
    }

}
