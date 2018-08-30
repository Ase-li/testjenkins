package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.AssetTransferBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.NumberFormalUtils;

import java.util.List;

public class InvestTransVerifyAdapter extends CommonAdapter<AssetTransferBean> {

    public InvestTransVerifyAdapter(Context context, List<AssetTransferBean> datas) {
        super(context, R.layout.item_trans_verify, datas);
    }

    @Override
    protected void convert(ViewHolder holder, AssetTransferBean investVerify, int position) {
        ImageView ivGroupIcon = holder.getView(R.id.iv_group_icon);
        if (investVerify.isShow) {
            holder.getView(R.id.ll_trans_verify_detail).setVisibility(View.VISIBLE);
            ivGroupIcon.setImageResource(R.mipmap.icon_item_right_nor);
        } else {
            ivGroupIcon.setImageResource(R.mipmap.icon_item_right_sel);
            holder.getView(R.id.ll_trans_verify_detail).setVisibility(View.GONE);
        }
        holder.setText(R.id.tv_group_money, investVerify.getTransferAmount() + "元");
        holder.setText(R.id.tv_group_name, investVerify.getName());
        holder.setText(R.id.tv_settled_rate, NumberFormalUtils.percentFormat(investVerify.getAnnualRate(), 2, 2));
        holder.setText(R.id.tv_procedure_rate, NumberFormalUtils.percentFormat(investVerify.getTransferRate(), 2, 2));
        holder.setText(R.id.tv_invest_time, investVerify.getInvestTime());
        holder.setText(R.id.tv_queue_num, investVerify.getQueueOrderCount() + "");
    }
}
