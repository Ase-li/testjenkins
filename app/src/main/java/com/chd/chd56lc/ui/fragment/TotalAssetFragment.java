package com.chd.chd56lc.ui.fragment;

import android.annotation.SuppressLint;
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

public class TotalAssetFragment extends BaseFragment implements IAssetAnalyzeView {

    //    @BindView(R.id.pc_asset_rate)
//    PieChart pcAssetRate;
    @BindView(R.id.tv_amount)
    TextView tvAmount;
    @BindView(R.id.tv_revenue)
    TextView tvRevenue;
    @BindView(R.id.tv_balance)
    TextView tvBalance;
    @BindView(R.id.lc_invest_trend)
    LineChart lcInvestTrend;
    @BindView(R.id.tv_total_asset)
    TextView tvTotalAsset;
    private AssetAnalyzeBean assetAnalyzeBean;
    private List<AssetAnalyzeBean.AssestForDateBoListBean> assestForDateBoList;
    @Inject
    AssetAnalyzePresenter assetAnalyzePresenter;

    public TotalAssetFragment() {
        // Required empty public constructor
        DaggerAssetComponent.builder().appComponent(BaseApplication.getAppComponent())
                .assetModule(new AssetModule(this)).build().inject(this);
    }

    @Override
    protected void initView() {
//        initPieChart();
        initLineChart();
        assetAnalyzePresenter.assetInvest();
    }

    private void initLineChart() {
        lcInvestTrend.getLegend().setEnabled(false); //关闭说明
        lcInvestTrend.getDescription().setText("");  //设置描述
        lcInvestTrend.getAxisRight().setEnabled(false); //设置右边纵坐标
        lcInvestTrend.setNoDataText("暂无投资信息");      //无数据时显示
        lcInvestTrend.setExtraOffsets(5, 5, 0, 0);

        MyMarkerView mv = new MyMarkerView(context, R.layout.custom_marker_view);
        mv.setChartView(lcInvestTrend); // 边界控制
        lcInvestTrend.setMarker(mv); // 把记号标在图表上

        YAxis axisLeft = lcInvestTrend.getAxisLeft(); //左纵坐标
        axisLeft.removeAllLimitLines(); //取消限制线
        axisLeft.setAxisMinimum(0);     //纵坐标最小值
        axisLeft.setLabelCount(5, true); //强制设置5个刻度

        axisLeft.setGridColor(UIUtils.getColor(R.color.color_f2f2f2)); //网格线颜色
        XAxis xAxis = lcInvestTrend.getXAxis();//横坐标
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//显示在下面
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new MyXAxisValueFormatter());//设置刻度值格式

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
        lcInvestTrend.getXAxis().setLabelCount(assestForDateBoList.size(), true);
        for (int i = 0; i < assestForDateBoList.size(); i++) {
            entries.add(new Entry(i, assestForDateBoList.get(i).getAmountInMonth()));
        }
        LineDataSet lineDataSet = new LineDataSet(entries, "");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setDrawFilled(true);
        lineDataSet.setFillColor(UIUtils.getColor(R.color.color_18FF4E03));
        lineDataSet.setColor(UIUtils.getColor(R.color.color_ff4e03));
        lineDataSet.setCircleColor(UIUtils.getColor(R.color.color_ff4e03));
        lineDataSet.setDrawValues(false);

        ArrayList<ILineDataSet> iLineDataSets = new ArrayList<>();
        iLineDataSets.add(lineDataSet);
        LineData lineData = new LineData(iLineDataSets);
        lcInvestTrend.setData(lineData);
        lcInvestTrend.invalidate();
    }

//    private void initPieChart() {
//        pcAssetRate.setDrawHoleEnabled(true);
//        pcAssetRate.setHoleColor(Color.WHITE);
//        pcAssetRate.setHoleRadius(60);
//        pcAssetRate.setDrawCenterText(false);
//        pcAssetRate.setUsePercentValues(true);
//        pcAssetRate.getLegend().setEnabled(false);
//        pcAssetRate.getDescription().setText("");
//        pcAssetRate.setExtraOffsets(10, 10, 10, 10);
//        pcAssetRate.setNoDataText("暂无投资信息");
//    }

//    private void setPieData() {
//
//        ArrayList<PieEntry> entries = new ArrayList<>();
//        ArrayList<Integer> colors = new ArrayList<Integer>();
//        if (assetAnalyzeBean.getCurrentAmount() != 0) {
//            entries.add(new PieEntry(assetAnalyzeBean.getCurrentAmount()));
//            colors.add(UIUtils.getColor(R.color.color_ff814b));
//        }
//        if (assetAnalyzeBean.getNotReceiveProfit() != 0) {
//            entries.add(new PieEntry(assetAnalyzeBean.getNotReceiveProfit()));
//            colors.add(UIUtils.getColor(R.color.color_ff4e03));
//        }
//        if (assetAnalyzeBean.getBalance() != 0) {
//            entries.add(new PieEntry(assetAnalyzeBean.getBalance()));
//            colors.add(UIUtils.getColor(R.color.color_ffc1a6));
//        }
//        PieDataSet dataSet = new PieDataSet(entries, " ");
//        dataSet.setLabel("");
//        dataSet.setColors(colors);
//        dataSet.setSliceSpace(2f);
//        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
//        PieData pieData = new PieData(dataSet);
//        pieData.setValueFormatter(new PercentFormatter());
//        pieData.setValueTextSize(10);
//        pcAssetRate.setData(pieData);
//    }

    @Override
    public int loadLayoutResID() {
        return R.layout.fragment_total_asset;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void updateAssetData(AssetAnalyzeBean assetAnalyzeBean) {
        this.assetAnalyzeBean = assetAnalyzeBean;
        tvAmount.setText(NumberFormalUtils.numberFormat(assetAnalyzeBean.getCurrentAmount(), 2, 2) + "元");
        tvBalance.setText(NumberFormalUtils.numberFormat(assetAnalyzeBean.getBalance(), 2, 2) + "元");
        tvTotalAsset.setText(NumberFormalUtils.numberFormat(assetAnalyzeBean.getTotalAmount(), 2, 2));
        setLineData();
    }

    @OnClick(R.id.tv_look_detail)
    public void onClick() {
        toActivity(TransactionRecordListActivity.class);
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
