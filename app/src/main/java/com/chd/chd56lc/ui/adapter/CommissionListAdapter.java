package com.chd.chd56lc.ui.adapter;

import android.content.Context;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.CommissionBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.DateToString;

import java.util.List;

public class CommissionListAdapter extends CommonAdapter<CommissionBean> {

    public CommissionListAdapter(Context context, List<CommissionBean> datas) {
        super(context, R.layout.item_commission_detail, datas);
    }

    @Override
    protected void convert(ViewHolder holder, CommissionBean t, int position) {
        holder.setText(R.id.tv_commission_money, t.getCommission() + "");
        holder.setText(R.id.tv_date, t.getSuccessTimeF());
        holder.setText(R.id.tv_Commission_name, DateToString.formalDateString(t.getSuccessTime(), "yyyy-M") + " 计提佣金");
    }

}
