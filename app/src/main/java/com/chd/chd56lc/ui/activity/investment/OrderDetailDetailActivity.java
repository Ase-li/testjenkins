package com.chd.chd56lc.ui.activity.investment;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.OrderDetailDetailBean;
import com.chd.chd56lc.mvp.presenter.OrderDetailDetailPresenter;
import com.chd.chd56lc.mvp.view.IOrderDetailDetailView;
import com.chd.chd56lc.ui.adapter.EarningsDetailDetailAdapter;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.ui.fragment.TotalAssetFragment;
import com.chd.chd56lc.utils.DateToString;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.ScrollListView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class OrderDetailDetailActivity extends BaseActivity implements IOrderDetailDetailView {

    @BindView(R.id.chart)
    LineChart chart;
    @BindView(R.id.lly_income_trend)
    LinearLayout llyIncomeTrend;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_year_rate)
    TextView tv_year_rate;
    @BindView(R.id.rl_assign_debt)
    RelativeLayout rlAssignDebt;
    @BindView(R.id.lv_detail)
    ScrollListView lvDetail;
    @BindView(R.id.ll_nodata)
    LinearLayout llNoData;
    @BindView(R.id.sl_data)
    ScrollView slData;


    private String orderId;
    private EarningsDetailDetailAdapter earningsDetailDetailAdapter;
    private List<OrderDetailDetailBean> datas;

    @Inject
    OrderDetailDetailPresenter orderDetailDetailPresenter;

    @Override
    public void initDagger() {
        super.initDagger();
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build()
                .inject(this);
    }

    /**
     * 返回一个用于设置界面的布局id
     */
    @Override
    public int loadLayoutResID() {
        return R.layout.activity_earnings_detail_detail;
    }

    /**
     * 初始化View
     */
    public void initView() {
        setTitle("明细");
        orderId = getIntent().getStringExtra(OrderDetailActivity.ORDER_ID);
        initData();
    }


    /**
     * 初始化数据，并显示到界面上
     */
    public void initData() {
        if (datas == null) {
            datas = new ArrayList<>();
        }
        earningsDetailDetailAdapter = new EarningsDetailDetailAdapter(this, datas);
        lvDetail.setAdapter(earningsDetailDetailAdapter);
        if (!StringUtils.isEmpty(orderId))
            orderDetailDetailPresenter.orderDetailRecord(orderId);
    }

    private void setLineData() {
        ArrayList<Entry> entries = new ArrayList<>();
//        List<AssetAnalyzeBean.AssestForDateBoListBean> assestForDateBoList = assetAnalyzeBean.getAssestForDateBoList();
//        if (assestForDateBoList.size() == 0) {
//            return;
//        }
//        for (int i = 0; i < assestForDateBoList.size(); i++) {
//            entries.add(new Entry(getMonth(assestForDateBoList.get(i).getDate()), assestForDateBoList.get(i).getAmountInMonth()));
//        }
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(UIUtils.getColor(R.color.color_bffff1));
        lineDataSet.setColor(UIUtils.getColor(R.color.color_3ed2b1));
        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);
        chart.setData(lineData);
    }

    private int getMonth(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateToString.transStringToDate(dateString, "yyyy-MM"));
        return calendar.get(Calendar.MONTH) + 1;
    }

    @Override
    public void setOrderDetailDetail(List<OrderDetailDetailBean> orderDetailDetailBeans) {
        if (orderDetailDetailBeans != null && orderDetailDetailBeans.size() != 0) {
            llNoData.setVisibility(View.GONE);
            slData.setVisibility(View.VISIBLE);
            datas.addAll(orderDetailDetailBeans);
        } else {
            llNoData.setVisibility(View.VISIBLE);
            slData.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (orderDetailDetailPresenter != null)
            orderDetailDetailPresenter.onUnsubscribe();
    }
}
