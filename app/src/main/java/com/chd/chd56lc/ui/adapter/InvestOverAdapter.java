package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.AssetInvestOverBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;

import java.util.List;

public class InvestOverAdapter extends CommonAdapter<AssetInvestOverBean.Item> {
    public InvestOverAdapter(Context context, List<AssetInvestOverBean.Item> datas) {
        super(context, R.layout.item_invest_over, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AssetInvestOverBean.Item t, int position) {
        holder.getView(R.id.tv_hint1).setEnabled(false);//标签1
        holder.getView(R.id.tv_hint2).setVisibility(View.GONE);//标签2
        holder.getView(R.id.tv_possess_money).setEnabled(false);
        holder.getView(R.id.tv_progress_1).setEnabled(false);//进度条4个位置详情看UI
        holder.getView(R.id.tv_progress_2).setEnabled(false);
        holder.getView(R.id.tv_progress_3).setEnabled(false);
        holder.getView(R.id.tv_progress_4).setEnabled(false);
        holder.getView(R.id.iv_status).setVisibility(View.VISIBLE);
        holder.getView(R.id.tv_hint1).setEnabled(false);
        if (t.getStatus() == 1) {
            holder.setImageResource(R.id.iv_status, R.mipmap.icon_wdtz_yhk);
            holder.setText(R.id.tv_hint1, "已回款");
            holder.setText(R.id.tv_progress_time_4, StringUtils.replacePoint(t.getExpiryTime()));
        } else {
            holder.setImageResource(R.id.iv_status, R.mipmap.icon_wdtz_yzr);
            holder.setText(R.id.tv_hint1, "已转让");
            holder.setText(R.id.tv_progress_time_4, StringUtils.replacePoint(t.getTransferTime()));
        }
        holder.getView(R.id.cb_select).setVisibility(View.GONE);
        ((TextView) holder.getView(R.id.tv_money_unit)).setTextColor(UIUtils.getColor(R.color.color_666666));
        holder.setText(R.id.tv_debt_name, t.getBidName());
        holder.setText(R.id.tv_possess_money, NumberFormalUtils.numberFormat(t.getCurrentAmount(), 0, 0));
        holder.setText(R.id.tv_accumulated_income, NumberFormalUtils.numberFormat(t.getCumulativeIncome(), 0, 2));
        holder.setText(R.id.tv_year_rate, NumberFormalUtils.percentFormat(t.getAnnualRate(), 0, 2));
        TextViewSpanUtils.setAbsoluteSizeSpan((TextView) holder.getView(R.id.tv_year_rate), "%", 12);
        if (t.getOtherRate() != 0) {
            holder.getView(R.id.tv_other_rate).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_other_rate, NumberFormalUtils.percentFormat(t.getOtherRate(), 0, 2));
            TextViewSpanUtils.setAbsoluteSizeSpan((TextView) holder.getView(R.id.tv_other_rate), "%", 12);
        } else {
            holder.getView(R.id.tv_other_rate).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_progress_time_1, StringUtils.replacePoint(t.getInvestTime()));
        holder.setText(R.id.tv_progress_time_2, StringUtils.replacePoint(t.getPaymentTime()));
        holder.getView(R.id.ll_progress_3).setVisibility(View.GONE);

        ((ProgressBar) holder.getView(R.id.pb_progress)).setProgressDrawable(UIUtils.getDrawable(R.drawable.progressbar_background_full));
    }
}
