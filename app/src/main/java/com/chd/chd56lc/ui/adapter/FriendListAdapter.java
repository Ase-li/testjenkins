package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.FriendBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.UIUtils;

import java.util.List;

public class FriendListAdapter extends CommonAdapter<FriendBean> {

    public FriendListAdapter(Context context, List<FriendBean> datas) {
        super(context, R.layout.item_friend, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FriendBean t, int position) {
        holder.setText(R.id.tv_friend_name, t.getName().substring(0,1)+"**");
        holder.setText(R.id.tv_status_name, t.isIfCommission() ? "佣金计提中" : "停止计提佣金");
        ((TextView)holder.getView(R.id.tv_status_name)).setTextColor(t.isIfCommission()? UIUtils.getColor(R.color.color_3ed2b1): UIUtils.getColor(R.color.color_ff1f1f));
        holder.setText(R.id.tv_tel, t.getPhone());
        holder.setText(R.id.tv_remark, t.getDepositRegistTimeF() + " 开户");
    }

}
