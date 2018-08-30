package com.chd.chd56lc.widget.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.adapter.SelectTypeAdapter;
import com.chd.chd56lc.utils.UIUtils;

public class SelectTypeWindow extends PopupWindow {

    private Context context;
    private View contentView;
    private ListView lvType;
    private String[] types;
    private SelectType selectType;

    public SelectTypeWindow(Context context, SelectType selectType) {
        super(context);
        this.context = context;
        types = context.getResources().getStringArray(R.array.transfer_record_select);
        this.selectType = selectType;
        initPopupWindow();
    }

    private void initPopupWindow() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.ui_trans_type, null);
        this.setContentView(contentView);
        this.setWidth(UIUtils.dip2px(170));
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(UIUtils.getDrawable(R.drawable.select_type_or_date));
        this.setAnimationStyle(R.style.transparency_menu_animation);
        lvType = contentView.findViewById(R.id.lv_type);
        lvType.setAdapter(new SelectTypeAdapter(context, types));
        lvType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                BaseApplication.getAppComponent().customToast().setText("你选择了：" + types.get(position));
                selectType.select(position, types[position]);
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

    public interface SelectType {
        void select(int type, String name);
    }
}
