package com.chd.chd56lc.ui.activity.investment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.AssetTransferBean;
import com.chd.chd56lc.mvp.presenter.TransferPresenter;
import com.chd.chd56lc.mvp.view.IInvestmentTransVerifyView;
import com.chd.chd56lc.ui.adapter.InvestTransVerifyAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.MultiItemTypeAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.widget.CustomToast;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.OnClick;

public class InvestmentTransVerifyActivity extends BaseActivity implements IInvestmentTransVerifyView {
    public static final String IDS = "ids";
    @BindView(R.id.rc_data)
    RecyclerView rcData;
    @BindView(R.id.smart_refresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.cb_agree_protocol)
    CheckBox cbAgreeProtocol;
    @BindView(R.id.tv_no_data)
    TextView tvNoData;
    @BindView(R.id.ll_nodata)
    LinearLayout llNodata;
    @BindView(R.id.iv_nodata)
    ImageView ivNodata;

    @Named("transVerify")
    @Inject
    TransferPresenter transferPresenter;
    @Inject
    CustomToast toast;

    private String ids;
    private List<AssetTransferBean> transferableBeans;
    private InvestTransVerifyAdapter investTransVerifyAdapter;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (transferPresenter != null)
            transferPresenter.onUnsubscribe();
    }

    @Override
    public void initDagger() {
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build()
                .inject(this);
    }

    private void initData() {
        transferPresenter.transferPageInfo(ids);
    }

    public void initView() {
        setTitle("债转确认");
        ids = getIntent().getStringExtra(IDS);
        if (transferableBeans == null)
            transferableBeans = new ArrayList<>();
        rcData.setLayoutManager(new LinearLayoutManager(activity));
        investTransVerifyAdapter = new InvestTransVerifyAdapter(activity, transferableBeans);
        investTransVerifyAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                AssetTransferBean transferableBean = transferableBeans.get(position);
                if (transferableBean.isShow) {
                    transferableBean.isShow = false;
                } else {
                    transferableBean.isShow = true;
                }
                investTransVerifyAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        rcData.setAdapter(investTransVerifyAdapter);
        initData();
    }

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_investment_trans_verify;
    }

    @Override
    public void setTransferPageInfo(List<AssetTransferBean> assetTransferBeans) {
        if (assetTransferBeans != null && assetTransferBeans.size() > 0) {
            transferableBeans.addAll(assetTransferBeans);
            rcData.setVisibility(View.VISIBLE);
            llNodata.setVisibility(View.GONE);
        }
        if (transferableBeans.size() == 1) {
            transferableBeans.get(0).isShow = true;
        }
        if (transferableBeans.size() <= 0) {
            rcData.setVisibility(View.GONE);
            llNodata.setVisibility(View.VISIBLE);
        }
        investTransVerifyAdapter.notifyDataSetChanged();
    }

    /**
     * 转让确认
     */
    @Override
    public void transferVerify() {
        toast.setText("转让成功");
        toActivity(MyInvestmentActivity.class);
        finish();
    }

    @OnClick(R.id.tv_verify_transfer)
    public void onClick() {
        if (!cbAgreeProtocol.isChecked()) {
            toast.setText("请同意债权转让协议");
            return;
        }
        transferPresenter.transfer(ids);
    }
}
