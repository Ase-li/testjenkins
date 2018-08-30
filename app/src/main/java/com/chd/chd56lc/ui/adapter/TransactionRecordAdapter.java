package com.chd.chd56lc.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.TransactionRecordBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.DateToString;
import com.chd.chd56lc.utils.UIUtils;

import java.util.Calendar;
import java.util.List;

public class TransactionRecordAdapter extends CommonAdapter<TransactionRecordBean> {
    private List<TransactionRecordBean> datas;
    private Calendar section;

    public TransactionRecordAdapter(Context context, List<TransactionRecordBean> datas) {
        super(context, R.layout.item_transaction_record, datas);
        this.datas = datas;
        section = Calendar.getInstance();
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(ViewHolder holder, TransactionRecordBean transactionRecord, int position) {
        ImageView ivRecordIcon = holder.getView(R.id.iv_record_icon);
        TextView tvDate = holder.getView(R.id.tv_date);
        TextView tvTransMoney = holder.getView(R.id.tv_trans_money);
        TextView tvTransDate = holder.getView(R.id.tv_trans_date);
        TextView tvRecordType = holder.getView(R.id.tv_record_type);
        section.setTimeInMillis(transactionRecord.getTimestamp());
        tvDate.setText(DateToString.formalDateString(transactionRecord.getTimestamp(), "yyyy.MM"));
        if (position == getPositionForSection()) {
            tvDate.setVisibility(View.VISIBLE);
        } else {
            tvDate.setVisibility(View.GONE);
        }
        ivRecordIcon.setImageResource(transactionRecord.getIncreaseIcon());
        tvTransMoney.setTextColor(transactionRecord.getIncreaseColor());
        tvTransMoney.setText(transactionRecord.getAmount() + "元");
        tvTransMoney.append(transactionRecord.getStatusString());
        tvRecordType.setText(transactionRecord.getTypeName());
        tvTransDate.setText(DateToString.formalDateString(transactionRecord.getTimestamp(), "yyyy.MM.dd HH:mm:ss"));

    }

    public int getPositionForSection() {
        for (int i = 0; i < datas.size(); i++) {
            TransactionRecordBean transactionRecordBean = datas.get(i);
            long createdAt = transactionRecordBean.getTimestamp();
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(createdAt);
            if (calendar.get(Calendar.YEAR) == section.get(Calendar.YEAR)
                    && calendar.get(Calendar.MONTH) == section.get(Calendar.MONTH)) {
                return i;
            }
        }
        return 0;
    }
}
