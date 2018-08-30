package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.AssetTransferInBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;

import java.util.List;

public class InvestTransAdapter extends CommonAdapter<AssetTransferInBean> {
    private OnclickCancelListener onclickCancelListener;

    public InvestTransAdapter(Context context, List<AssetTransferInBean> datas, OnclickCancelListener onclickCancelListener) {
        super(context, R.layout.item_invest_trans, datas);
        this.onclickCancelListener = onclickCancelListener;
    }

    @Override
    protected void convert(ViewHolder holder, final AssetTransferInBean t, final int position) {
        holder.setText(R.id.tv_debt_name, t.getBidName());
        holder.setText(R.id.tv_remark, "剩余转让天数 " + t.getLeftTransferDay() + "天");
        holder.setText(R.id.tv_transferred, "已转让 " + NumberFormalUtils.percentFormat(t.getTransferPercent(), 0, 2));
        holder.setText(R.id.tv_possess_money, NumberFormalUtils.numberFormat(t.getCurrentAmount(), 0, 2) + "元");
        holder.setText(R.id.tv_transferred_amount, NumberFormalUtils.numberFormat(t.getSuccessTransferAmount(), 0, 2) + "元");
        holder.setProgress(R.id.pb_progress, (int) (t.getTransferPercent() * 100));
        TextViewSpanUtils.setAbsoluteSizeSpan((TextView) holder.getView(R.id.tv_possess_money), "元", 12);
        TextViewSpanUtils.setAbsoluteSizeSpan((TextView) holder.getView(R.id.tv_transferred_amount), "元", 12);
        holder.getView(R.id.tv_cancel_trans).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onclickCancelListener.cancelTransfer(t.getId());
            }
        });
        switch (t.getStatus()) {
            case 1:
                holder.setText(R.id.tv_hint1, "转让中");
                break;
            case 0:
                holder.setText(R.id.tv_hint1, "排队中：" + t.getTransferOrder());
                break;
        }
    }

    public interface OnclickCancelListener {
        void cancelTransfer(String id);
    }
}
