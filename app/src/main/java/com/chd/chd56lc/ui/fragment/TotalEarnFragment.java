package com.chd.chd56lc.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerAssetComponent;
import com.chd.chd56lc.dagger.modules.AssetModule;
import com.chd.chd56lc.entity.AssetAnalyzeBean;
import com.chd.chd56lc.mvp.presenter.AssetAnalyzePresenter;
import com.chd.chd56lc.mvp.view.IAssetAnalyzeView;
import com.chd.chd56lc.ui.activity.investment.TransactionRecordListActivity;
import com.chd.chd56lc.ui.base.BaseFragment;
import com.chd.chd56lc.utils.DateToString;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.UIUtils;
import com.chd.chd56lc.widget.MyMarkerView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class TotalEarnFragment extends BaseFragment implements IAssetAnalyzeView {

    //    @BindView(R.id.pc_asset_rate)
//    PieChart pcEarnRate;
    @BindView(R.id.tv_due_earn)
    TextView tvDueEarn;
    @BindView(R.id.tv_been_earn)
    TextView tvBeenEarn;
    @BindView(R.id.lc_invest_trend)
    LineChart lcEarnTrend;
    @BindView(R.id.tv_total_earn)
    TextView tvTotalEarn;
    private AssetAnalyzeBean assetAnalyzeBean;
    private List<AssetAnalyzeBean.AssestForDateBoListBean> assestForDateBoList;

    @Inject
    AssetAnalyzePresenter assetAnalyzePresenter;

    public TotalEarnFragment() {
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    protected void initView() {
//        initPieChart();
        initLineChart();
        assetAnalyzePresenter.assetProfit();
    }

    private void initLineChart() {
        lcEarnTrend.getLegend().setEnabled(false);
        lcEarnTrend.getDescription().setText("");
        lcEarnTrend.getAxisRight().setEnabled(false);
        lcEarnTrend.setNoDataText("暂无收益信息");
        lcEarnTrend.setExtraOffsets(5, 5, 0, 0);

        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view1);
        mv.setChartView(lcEarnTrend); // 边界控制
        lcEarnTrend.setMarker(mv); // 把记号标在图表上

        YAxis axisLeft = lcEarnTrend.getAxisLeft();
        axisLeft.removeAllLimitLines();
        axisLeft.setAxisMinimum(0);
        axisLeft.setLabelCount(5, true);

        XAxis xAxis = lcEarnTrend.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyXAxisValueFormatter());
        xAxis.setDrawGridLines(false);

    }

    private int getMonth(String dateString) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateToString.transStringToDate(dateString, "yyyy-MM"));
        return calendar.get(Calendar.MONTH) + 1;
    }


    private void setLineData() {
        ArrayList<Entry> entries = new ArrayList<>();
        assestForDateBoList = assetAnalyzeBean.getAssestForDateBoList();
        if (assestForDateBoList.size() == 0) {
            return;
        }
        lcEarnTrend.getXAxis().setLabelCount(assestForDateBoList.size(), true);
        for (int i = 0; i < assestForDateBoList.size(); i++) {
            entries.add(new Entry(i, assestForDateBoList.get(i).getAmountInMonth()));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(UIUtils.getColor(R.color.color_183ed2b1));
        lineDataSet.setColor(UIUtils.getColor(R.color.color_3ed2b1));
        lineDataSet.setCircleColor(UIUtils.getColor(R.color.color_3ed2b1));
        lineDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);
        lcEarnTrend.setData(lineData);
        lcEarnTrend.invalidate();
    }

//    private void initPieChart() {
//        pcEarnRate.setDrawHoleEnabled(true);
//        pcEarnRate.setHoleColor(Color.WHITE);
//        pcEarnRate.setHoleRadius(60);
//        pcEarnRate.setDrawCenterText(false);
//        pcEarnRate.setUsePercentValues(true);
//        pcEarnRate.getLegend().setEnabled(false);
//        pcEarnRate.getDescription().setText("");
//        pcEarnRate.setExtraOffsets(10, 10, 10, 10);
//        pcEarnRate.setNoDataText("暂无收益信息");
//    }

//    private void setPieData() {
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        entries.add(new PieEntry(assetAnalyzeBean.getReceivedProfit()));
//        entries.add(new PieEntry(assetAnalyzeBean.getNotReceiveProfit()));
//        PieDataSet dataSet = new PieDataSet(entries, " ");
//        dataSet.setLabel("");
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        colors.add(UIUtils.getColor(R.color.color_bffff1));
//        colors.add(UIUtils.getColor(R.color.color_3ed2b1));
//        dataSet.setColors(colors);
//        dataSet.setSliceSpace(2f);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        PieData pieData = new PieData(dataSet);
//        pieData.setValueFormatter(new PercentFormatter());
//        pieData.setValueTextSize(10f);
//        pcEarnRate.setData(pieData);
//    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_total_earn;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateAssetData(AssetAnalyzeBean assetAnalyzeBean) {
        this.assetAnalyzeBean = assetAnalyzeBean;
        tvBeenEarn.setText(NumberFormalUtils.numberFormat(assetAnalyzeBean.getReceivedProfit(), 2, 2) + "元");
        tvDueEarn.setText(NumberFormalUtils.numberFormat(assetAnalyzeBean.getNotReceiveProfit(), 2, 2) + "元");
        tvTotalEarn.setText(NumberFormalUtils.numberFormat(assetAnalyzeBean.getTotalProfit(), 2, 2));
        setLineData();
//        setPieData();
    }

    @OnClick(R.id.tv_look_detail)
    public void onClick() {
        toActivity(new Intent(context, TransactionRecordListActivity.class).putExtra(TransactionRecordListActivity.SELECT_TYPE, 3));
    }

    /**
     * X轴坐标格式化
     */
    public class MyXAxisValueFormatter implements IAxisValueFormatter {

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            try {
                if (assestForDateBoList != null) {
                    String month = DateToString.formalDateString(assestForDateBoList.get((int) value % assestForDateBoList.size()).getDateTimestamp(), "M");
                    if ("1".equals(month))
                        return DateToString.formalDateString(assestForDateBoList.get((int) value % assestForDateBoList.size()).getDateTimestamp(), "yy年M月");
                    else
                        return DateToString.formalDateString(assestForDateBoList.get((int) value % assestForDateBoList.size()).getDateTimestamp(), "M月");
                }
            } catch (Exception e) {

            }
            return "";
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (assetAnalyzePresenter != null)
            assetAnalyzePresenter.onUnsubscribe();
    }
}
