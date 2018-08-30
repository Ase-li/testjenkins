package com.chd.chd56lc.ui.activity.investment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.TransferableBean;
import com.chd.chd56lc.mvp.presenter.TransferPresenter;
import com.chd.chd56lc.mvp.view.ITransferableViewInView;
import com.chd.chd56lc.ui.adapter.InvestTransferableAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.CustomToast;
import com.chd.chd56lc.widget.NewTwoSideTextView;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;


public class InvestTransferableActivity extends BaseActivity implements ITransferableViewInView, InvestTransferableAdapter.TransferTotalMoney {

    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.cb_select_all)
    CheckBox cbSelectAll;
    @BindView(R.id.ntv_total_money)
    NewTwoSideTextView ntvTotalMoney;

    @Inject
    CustomToast toast;
    @Inject
    TransferPresenter transferPresenter;

    private InvestTransferableAdapter investTransferableAdapter;
    private List<TransferableBean> transferableBeans;
    private List<String> ids = new LinkedList<>();

    private void initData() {
        transferPresenter.listMainCurrentOrder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (transferPresenter != null)
            transferPresenter.onUnsubscribe();
    }

    public void initView() {
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build()
                .inject(this);
        setTitle("可转让列表");
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("当前无可转让标的");
        rcData.setLayoutManager(new LinearLayoutManager(activity));
        transferableBeans = new ArrayList<>();
        investTransferableAdapter = new InvestTransferableAdapter(activity, transferableBeans);
        investTransferableAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                TransferableBean investTransfer = transferableBeans.get(position);
                if (investTransfer.isShow) {
                    investTransfer.isShow = false;
                } else {
                    investTransfer.isShow = true;
                }
                chargeMoney(position, investTransfer.isShow);
                investTransferableAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        investTransferableAdapter.setTransferTotalMoney(this);
        rcData.setAdapter(investTransferableAdapter);
        //勾选全选
        cbSelectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (TransferableBean investTransfer : transferableBeans) {
                    investTransfer.isShow = isChecked;
                }
                investTransferableAdapter.notifyDataSetChanged();
            }
        });
        smartRefresh.setEnableLoadMore(false);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                transferableBeans.clear();
                initData();
            }
        });
        initData();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_invest_transferable;
    }

    @OnClick(R.id.tv_next)
    public void onClick() {
        boolean leastOne = false;
        for (TransferableBean investTransfer : transferableBeans) {
            if (investTransfer.isShow)
                leastOne = true;
        }
        if (!leastOne) {
            toast.setText("至少选择一项");
            return;
        }
        toActivity(new Intent(activity, InvestmentTransVerifyActivity.class)
                .putExtra(InvestmentTransVerifyActivity.IDS, new Gson().toJson(ids)));

    }

    /**
     * 设置可转让列表
     *
     * @param transferableList
     */
    @Override
    public void setTransferableList(List<TransferableBean> transferableList) {
        if (transferableList != null && transferableList.size() > 0) {
            llNodata.setVisibility(View.GONE);
            rcData.setVisibility(View.VISIBLE);
            transferableBeans.addAll(transferableList);
        }
        if (transferableList == null || transferableList.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        investTransferableAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isRefreshing()) {
            smartRefresh.finishRefresh();
        }
    }

    private double totalMoney;

    public void chargeMoney(int position, boolean isChecked) {
        if (transferableBeans != null) {
            if (isChecked) {
                totalMoney += transferableBeans.get(position).getCurrentAmount();
                ntvTotalMoney.setValueText(totalMoney + "元");
                ids.add(transferableBeans.get(position).getId());
            } else {
                totalMoney -= transferableBeans.get(position).getCurrentAmount();
                ntvTotalMoney.setValueText(totalMoney + "元");
                ids.remove(transferableBeans.get(position).getId());
            }
        }
    }
}
