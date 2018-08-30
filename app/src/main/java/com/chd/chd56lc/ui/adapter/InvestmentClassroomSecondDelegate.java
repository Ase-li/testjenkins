package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.InvestmentClassroomBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ItemViewDelegate;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;

public class InvestmentClassroomSecondDelegate implements ItemViewDelegate<InvestmentClassroomBean> {
    private Context context;

    public InvestmentClassroomSecondDelegate(Context context) {
        this.context = context;
    }

    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_investment_classroom_second;
    }

    @Override
    public boolean isForViewType(InvestmentClassroomBean item, int position) {
        return position != 0;
    }

    @Override
    public void convert(ViewHolder holder, InvestmentClassroomBean investmentClassroomBean, int position) {
        holder.setText(R.id.tv_classroom_name, investmentClassroomBean.getTitle());
        holder.setText(R.id.tv_classroom_time, investmentClassroomBean.getCreateDate());
        Glide.with(context).load(investmentClassroomBean.getImg()).into((ImageView) holder.getView(R.id.iv_classroom_pic));
    }
}
