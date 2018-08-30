package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.TransferableBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;

import java.util.List;

public class InvestTransferableAdapter extends CommonAdapter<TransferableBean> {
    public InvestTransferableAdapter(Context context, List<TransferableBean> datas) {
        super(context, R.layout.item_invest_over, datas);
    }

    private TransferTotalMoney transferTotalMoney;

    public void setTransferTotalMoney(TransferTotalMoney transferTotalMoney) {
        this.transferTotalMoney = transferTotalMoney;
    }

    @Override
    protected void convert(ViewHolder holder, final TransferableBean investTransfer, final int position) {
        CheckBox cbSelect = holder.getView(R.id.cb_select);
        cbSelect.setVisibility(View.VISIBLE);
        cbSelect.setChecked(investTransfer.isShow);
        holder.getView(R.id.tv_hint1).setVisibility(View.GONE);
        holder.getView(R.id.tv_hint2).setVisibility(View.GONE);
        holder.getView(R.id.iv_status).setVisibility(View.GONE);
        holder.setText(R.id.tv_debt_name, investTransfer.getBidName());
        holder.setText(R.id.tv_possess_money, NumberFormalUtils.numberFormat(investTransfer.getCurrentAmount(), 0, 0));
        holder.setText(R.id.tv_accumulated_income, NumberFormalUtils.numberFormat(investTransfer.getCumulativeIncome(), 0, 2));
        holder.setText(R.id.tv_year_rate, NumberFormalUtils.percentFormat(investTransfer.getAnnualRate(), 2, 2));
        TextViewSpanUtils.setAbsoluteSizeSpan((TextView) holder.getView(R.id.tv_year_rate), "%", 12);
        if (investTransfer.getOtherRate() != 0) {
            holder.getView(R.id.tv_other_rate).setVisibility(View.VISIBLE);
            holder.setText(R.id.tv_other_rate, NumberFormalUtils.percentFormat(investTransfer.getOtherRate(), 2, 2));
        } else {
            holder.getView(R.id.tv_other_rate).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_progress_time_1, StringUtils.replacePoint(investTransfer.getInvestTime()));
        holder.setText(R.id.tv_progress_time_2, StringUtils.replacePoint1(investTransfer.getPaymentTime()));
        holder.setText(R.id.tv_progress_time_4, StringUtils.replacePoint(investTransfer.getExpireTime()));
        if (investTransfer.getInvestDay() == 0)
            holder.setText(R.id.tv_progress_time_3, "");
        else
            holder.setText(R.id.tv_progress_time_3, investTransfer.getInvestDay() + "天");
        switch (investTransfer.getRaiseStage()) {
            case 1:
                ((ProgressBar) holder.getView(R.id.pb_progress)).setProgress(20);
                holder.getView(R.id.tv_progress_2).setEnabled(true);
                holder.getView(R.id.tv_progress_3).setEnabled(false);
                holder.getView(R.id.tv_progress_4).setEnabled(false);
                break;
            case 2:
                ((ProgressBar) holder.getView(R.id.pb_progress)).setProgress(75);
                holder.getView(R.id.tv_progress_2).setEnabled(true);
                holder.getView(R.id.tv_progress_3).setEnabled(true);
                holder.getView(R.id.tv_progress_4).setEnabled(false);
                break;
            case 3:
                ((ProgressBar) holder.getView(R.id.pb_progress)).setProgress(100);
                holder.getView(R.id.tv_progress_2).setEnabled(true);
                holder.getView(R.id.tv_progress_3).setEnabled(true);
                holder.getView(R.id.tv_progress_4).setEnabled(true);
                break;
        }
    }

    public interface TransferTotalMoney {
        void chargeMoney(int position, boolean isChecked);
    }
}
