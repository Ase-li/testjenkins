package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.SelectBar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectBarAdapter extends BaseAdapter {
    private Context context;
    private SelectBar[] selectBars;
    private LayoutInflater inflater;


    public SelectBarAdapter(Context context, SelectBar[] selectBars) {
        this.context = context;
        this.selectBars = selectBars;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return selectBars.length;
    }

    @Override
    public Object getItem(int position) {
        return selectBars[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_select_bar, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivSelect.setBackgroundResource(selectBars[position].iconResourceID);
        viewHolder.tvSelect.setText(selectBars[position].name);
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_select)
        ImageView ivSelect;
        @BindView(R.id.tv_select)
        TextView tvSelect;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
