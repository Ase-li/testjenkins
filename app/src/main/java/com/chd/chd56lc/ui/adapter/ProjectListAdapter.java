package com.chd.chd56lc.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.constant.UMConstants;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.manager.CheckDeposeManager;
import com.chd.chd56lc.ui.activity.investment.InvestmentActivity;
import com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;

import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.ID;
import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.INTEREST_WAY;
import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.PROJECT_ID;
import static com.chd.chd56lc.ui.activity.investment.InvestmentDetailActivity.TYPE;

/**
 * Created by li on 2018/4/7.
 */
public class ProjectListAdapter extends BaseAdapter {


    private Context context;
    private List<ProjectDetailBean> datas;
    private LayoutInflater inflater;

    public ProjectListAdapter(Context context, List<ProjectDetailBean> datas) {
        this.context = context;
        this.datas = datas;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas == null ? 10 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_debt_assign, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (datas == null) return convertView;
        final ProjectDetailBean projectDetailBean = datas.get(position);
        viewHolder.tvDebtName.setText(projectDetailBean.getName());
        String yearRate = NumberFormalUtils.percentFormat(projectDetailBean.getAnnualRate(), 0, 2);
        viewHolder.tvYearRate.setText(yearRate);
        TextViewSpanUtils.setAbsoluteSizeSpan(viewHolder.tvYearRate, "%", 18);
        if (projectDetailBean.getType() == 1) {
            viewHolder.tvLimitTime.setText(projectDetailBean.getTerm() + " 天");
            viewHolder.tvLimitTime.setTextColor(UIUtils.getColor(R.color.color_333333));
            TextViewSpanUtils.setAbsoluteSizeSpan(viewHolder.tvLimitTime, projectDetailBean.getTerm() + "", 18);
            viewHolder.tv_date_hint.setText("投资期限");
            viewHolder.tvRemainMoney.setTextSize(18);
        } else {
            viewHolder.tvLimitTime.setText(projectDetailBean.getExpiryTime() + "");
            viewHolder.tvLimitTime.setTextColor(UIUtils.getColor(R.color.color_999999));
            viewHolder.tvLimitTime.setTextSize(12);
            viewHolder.tv_date_hint.setText("到期日期");
            viewHolder.tvRemainMoney.setTextSize(24);
        }
        viewHolder.tvHint1.setText(projectDetailBean.getInterestWayString());
        viewHolder.tvHint2.setText(projectDetailBean.getCanTransferString());
        viewHolder.pbProgress.setProgress((int) (100 * projectDetailBean.getRaisedPercent()));
        viewHolder.tvRemainMoney.setText(NumberFormalUtils.numberFormat(projectDetailBean.getCanInvestAmount(), 0, 2));
        viewHolder.tvDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, InvestmentDetailActivity.class)
                        .putExtra(ID, projectDetailBean.getId())
                        .putExtra(INTEREST_WAY, projectDetailBean.getInterestWay())
                        .putExtra(TYPE, projectDetailBean.getType()));
            }
        });
        viewHolder.llToInvest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (projectDetailBean == null)
                    return;
                final Intent intent = new Intent(context, InvestmentActivity.class)
                        .putExtra(TYPE, projectDetailBean.getType())
                        .putExtra(ID, projectDetailBean.getId())
                        .putExtra(INTEREST_WAY, projectDetailBean.getInterestWay())
                        .putExtra(PROJECT_ID, projectDetailBean.getProjectId())
                        .putExtra(UMConstants.CHANNEL, "产品列表");
                CheckDeposeManager.getInstance().checkDeposeStatus(Observable.just(new Object()), context, new CheckDeposeManager.CheckResult() {
                    @Override
                    public void checkResult(boolean result) {
                        if (result)
                            context.startActivity(intent);
                    }
                });
            }
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_full)
        ImageView ivFull;
        @BindView(R.id.tv_debt_name)
        TextView tvDebtName;//标的名
        @BindView(R.id.tv_year_rate)
        TextView tvYearRate;//年化利率
        @BindView(R.id.tv_limit_time)
        TextView tvLimitTime;//债权转让：到期时间，无忧标：到期日期
        @BindView(R.id.tv_remain_money)
        TextView tvRemainMoney;//可投金额
        @BindView(R.id.tv_detail)
        TextView tvDetail;
        @BindView(R.id.tv_hint1)
        TextView tvHint1;
        @BindView(R.id.tv_hint2)
        TextView tvHint2;
        @BindView(R.id.tv_date_hint)
        TextView tv_date_hint;//债权转让：到期时间，无忧标：到期日期
        @BindView(R.id.ll_to_invest)
        RelativeLayout llToInvest;
        @BindView(R.id.pb_progress)
        ProgressBar pbProgress;//募集进度

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
