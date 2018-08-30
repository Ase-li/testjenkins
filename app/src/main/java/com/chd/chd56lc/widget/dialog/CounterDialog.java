package com.chd.chd56lc.widget.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.ProjectDetailBean;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;

/**
 * Created by li on 2018/3/24.
 */
@SuppressLint("SetTextI18n")
public class CounterDialog extends Dialog implements View.OnClickListener {
    private EditText et_invest_money;
    private ImageView iv_subtract;
    private ImageView iv_plus;
    private TextView tv_total_earn;
    private TextView tv_year_rate;
    private TextView btn_dialog_confirm;
    private ProjectDetailBean info;
    private Callback callback;

    public CounterDialog(@NonNull Context context, ProjectDetailBean info, Callback callback) {
        super(context, R.style.Dialog);
        this.info = info;
        this.callback = callback;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.transparency_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_invest_counter);
        if (info == null) {
            dismiss();
            return;
        }
        initView();

    }

    public void initView() {
        et_invest_money = (EditText) findViewById(R.id.et_invest_money);
        iv_subtract = (ImageView) findViewById(R.id.iv_subtract);
        iv_plus = (ImageView) findViewById(R.id.iv_plus);
        tv_total_earn = (TextView) findViewById(R.id.tv_total_earn);
        tv_year_rate = (TextView) findViewById(R.id.tv_year_rate);
        btn_dialog_confirm = (TextView) findViewById(R.id.btn_dialog_confirm);
        btn_dialog_confirm.setOnClickListener(this);
        iv_plus.setOnClickListener(this);
        iv_subtract.setOnClickListener(this);
        findViewById(R.id.iv_dialog_close).setOnClickListener(this);
        et_invest_money.addTextChangedListener(new MyTextWatcher());
        tv_year_rate.setText("预期收益率：" + NumberFormalUtils.percentFormat(info.getAnnualRate(), 0, 2));
        switch (info.getType()) {
            case 1:
                et_invest_money.setText("10000");
                break;
            case 2:
                et_invest_money.setText(info.getCanInvestAmount() + "");
                break;
        }
        if (info.getCanInvestAmount() > 0) {
            btn_dialog_confirm.setEnabled(true);
        } else {
            btn_dialog_confirm.setEnabled(false);
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_dialog_confirm:
                callback.confirm(et_invest_money.getText().toString());
                dismiss();
                break;
            case R.id.iv_dialog_close:
                dismiss();
                break;
            case R.id.iv_plus:
                int plus = Integer.parseInt(et_invest_money.getText().toString()) + 1000;
                if (plus > 0)
                    et_invest_money.setText(plus + "");
                break;
            case R.id.iv_subtract:
                int subtract = Integer.parseInt(et_invest_money.getText().toString()) - 1000;
                if (subtract > 0)
                    et_invest_money.setText(subtract + "");
                break;
        }
    }

    class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }


        @Override
        public void afterTextChanged(Editable s) {
            if (StringUtils.isEmpty(s.toString())) {
                et_invest_money.setText("0");
                return;
            }
            int investMoney = Integer.parseInt(s.toString());
            if (s.length() > 1 && s.toString().indexOf("0") == 0) {
                et_invest_money.setText(investMoney + "");
            }
            tv_total_earn.setText(NumberFormalUtils.numberFormat(investMoney * (info.getAnnualRate() / 360 * info.getTerm()), 0, 2));

        }
    }

    public interface Callback {
        void confirm(String investMoney);
    }
}
