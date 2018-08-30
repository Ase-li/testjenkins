package com.chd.chd56lc.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.adapter.SelectDateAdapter;
import com.chd.chd56lc.utils.DateToString;
import com.chd.chd56lc.utils.UIUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SelectDateWindow extends PopupWindow implements View.OnClickListener {

    private Context context;
    private View contentView;
    private TextView tvYear;
    private ListView lvDate;
    private SelectTime selectTime;

    public SelectDateWindow(Context context, SelectTime selectTime) {
        super(context);
        this.context = context;
        this.selectTime = selectTime;
        initPopupWindow();
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.ui_trans_date, null);
        this.setContentView(contentView);
        this.setWidth(UIUtils.dip2px(170));
        this.setHeight(UIUtils.dip2px(400));
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.select_type_or_date));
        this.setAnimationStyle(R.style.transparency_menu_animation);
        contentView.findViewById(R.id.iv_year_subtract).setOnClickListener(this);
        contentView.findViewById(R.id.iv_year_plus).setOnClickListener(this);
        tvYear = contentView.findViewById(R.id.tv_year);
        lvDate = contentView.findViewById(R.id.lv_date);
        tvYear.setText(DateToString.getYMD("", "", Calendar.YEAR));
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            list.add(i + 1 + "月");
        }
        lvDate.setAdapter(new SelectDateAdapter(context, list));
        lvDate.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                BaseApplication.getAppComponent().customToast().setText("你选择了" + tvYear.getText() + "年" + (position + 1) + "月");
                selectTime.selectTime(tvYear.getText().toString(), list.get(position).replace("月", ""));
                dismiss();
            }
        });
    }

    public void showLocation(View view) {
        if (!this.isShowing()) {
            this.showAsDropDown(view, -UIUtils.dip2px(5), UIUtils.dip2px(5));
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        String year = tvYear.getText().toString();
        switch (v.getId()) {
            case R.id.iv_year_plus:
                if (Integer.parseInt(year) < 3000)
                    tvYear.setText(Integer.parseInt(year) + 1 + "");
                break;
            case R.id.iv_year_subtract:
                if (Integer.parseInt(year) > 0)
                    tvYear.setText(Integer.parseInt(year) - 1 + "");
                break;
        }
    }

    public interface SelectTime {
        void selectTime(String year, String month);
    }
}
