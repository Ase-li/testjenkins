package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.UIUtils;

import java.util.List;

public class NotifyAdapter extends CommonAdapter<MessageBean> {
    RequestManager manager;

    public NotifyAdapter(Context context, List<MessageBean> datas) {
        super(context, R.layout.item_notify, datas);
        manager = Glide.with(context);
    }

    @Override
    protected void convert(ViewHolder holder, MessageBean notifyBean, int position) {
        holder.setText(R.id.tv_notify_title, notifyBean.getMsgTitle());
        holder.setText(R.id.tv_notify_content, notifyBean.getMsgContext());
        holder.setText(R.id.tv_notify_time, notifyBean.getPushTime());
        if (notifyBean.isReadFlag()) {
            ((TextView) holder.getView(R.id.tv_notify_title)).setTextColor(UIUtils.getColor(R.color.color_666666));
            holder.getView(R.id.vw1).setBackgroundColor(UIUtils.getColor(R.color.color_cccccc));
        } else {
            ((TextView) holder.getView(R.id.tv_notify_title)).setTextColor(UIUtils.getColor(R.color.color_333333));
            holder.getView(R.id.vw1).setBackgroundColor(UIUtils.getColor(R.color.color_6861ff));
        }
    }
}
