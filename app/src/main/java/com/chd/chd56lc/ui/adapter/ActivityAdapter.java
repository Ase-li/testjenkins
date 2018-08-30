package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.BannerBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;

import java.util.List;

public class ActivityAdapter extends CommonAdapter<BannerBean> {
    RequestManager manager;

    public ActivityAdapter(Context context, List<BannerBean> datas) {
        super(context, R.layout.item_activity, datas);
        manager = Glide.with(context);
    }

    @Override
    protected void convert(ViewHolder holder, BannerBean notifyBean, int position) {
        manager.load(notifyBean.getImgUrl()).into((ImageView) holder.getView(R.id.iv_activity_icon));
        holder.setText(R.id.tv_activity_title, notifyBean.getName());
        holder.setText(R.id.tv_activity_date, notifyBean.getStartTime());
    }
}
