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
import com.chd.chd56lc.entity.TransactionRecordBean;
import com.chd.chd56lc.entity.TransactionRecordList;
import com.chd.chd56lc.mvp.presenter.TransactionRecordPresenter;
import com.chd.chd56lc.mvp.view.IFriendView;
import com.chd.chd56lc.mvp.view.ITransactionRecordListView;
import com.chd.chd56lc.ui.adapter.TransactionRecordAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.widget.dialog.SelectDateWindow;
import com.chd.chd56lc.widget.dialog.SelectTypeWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class TransactionRecordListActivity extends BaseActivity implements ITransactionRecordListView {
    public static final String SELECT_TYPE = "select_type";

    @BindView(R.id.tv_select_type)
    TextView tvSelectType;
    @BindView(R.id.tv_select_month)
    TextView tvSelectMonth;
    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;

    @Named("TransactionRecordList")
    @Inject
    TransactionRecordPresenter transactionRecordPresenter;

    private SelectTypeWindow selectTypeWindow;
    private SelectDateWindow selectDateWindow;
    private TransactionRecordAdapter transactionRecordAdapter;
    private ArrayList<TransactionRecordBean> transactionRecords;

    private int page = 1;
    private int count = 20;
    private int type = 0;
    private Integer year = null;
    private Integer month = null;

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (transactionRecordPresenter != null)
            transactionRecordPresenter.onUnsubscribe();
    }

    public void initView() {
        setTitle("交易记录");
        type = getIntent().getIntExtra(SELECT_TYPE, 0);
        if (type == 3) {
            tvSelectType.setText("收益");
        }
        llNodata.setVisibility(View.VISIBLE);
        rcData.setVisibility(View.GONE);
        tvNoData.setText("暂无交易记录");
        if (transactionRecords == null)
            transactionRecords = new ArrayList<>();
        transactionRecordAdapter = new TransactionRecordAdapter(activity, transactionRecords);
        transactionRecordAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                if (StringUtils.isEmpty(transactionRecords.get(position).getOrderId()) ||
                        transactionRecords.get(position).getType() == 0) {
                    return;
                }
                toActivity(new Intent(activity, TransactionRecordActivity.class)
                        .putExtra(TransactionRecordActivity.ORDER_ID, transactionRecords.get(position).getOrderId())
                        .putExtra(TransactionRecordActivity.TYPE, transactionRecords.get(position).getType())
                );
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        smartRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                initData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                transactionRecords.clear();
                initData();
            }
        });
        rcData.setLayoutManager(new LinearLayoutManager(activity));
        rcData.setAdapter(transactionRecordAdapter);
        smartRefresh.autoRefresh();
    }

    private void initData() {
        transactionRecordPresenter.paginate(page, count, type, year, month);
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_transaction_record_list;
    }

    @OnClick({R.id.tv_select_type, R.id.tv_select_month})
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tv_select_type:
                selectTypeWindow = new SelectTypeWindow(this, new SelectTypeWindow.SelectType() {
                    @Override
                    public void select(int type, String name) {
                        TransactionRecordListActivity.this.type = type;
                        tvSelectType.setText(name);
                        page = 1;
                        transactionRecords.clear();
                        initData();
                    }
                });
                selectTypeWindow.showLocation(tvSelectType);
                break;
            case R.id.tv_select_month:
                selectDateWindow = new SelectDateWindow(this, new SelectDateWindow.SelectTime() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void selectTime(String year, String month) {
                        TransactionRecordListActivity.this.year = Integer.parseInt(year);
                        TransactionRecordListActivity.this.month = Integer.parseInt(month);
                        tvSelectMonth.setText(year + "年" + month + "月");
                        page = 1;
                        transactionRecords.clear();
                        initData();
                    }
                });
                selectDateWindow.showLocation(tvSelectMonth);
                break;
        }
    }

    @Override
    public void updateTransRecordList(TransactionRecordList transactionRecordList) {
        if (transactionRecordList != null && transactionRecordList.getArray() != null) {
            rcData.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
            transactionRecords.addAll(transactionRecordList.getArray());
            if (transactionRecordList.getPage() == transactionRecordList.getTotalPageNo()) {
                smartRefresh.setEnableLoadMore(false);
            } else {
                smartRefresh.setEnableLoadMore(true);
            }
        }
        if (transactionRecords.size() <= 0) {
            llNodata.setVisibility(View.VISIBLE);
            rcData.setVisibility(View.GONE);
        }
        transactionRecordAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissLoading() {
        super.dismissLoading();
        if (smartRefresh.isRefreshing()) {
            smartRefresh.finishRefresh();
        }
        if (smartRefresh.isLoading())
            smartRefresh.finishLoadMore();
    }
}
