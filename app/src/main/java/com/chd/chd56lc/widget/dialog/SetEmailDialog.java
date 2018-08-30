package com.chd.chd56lc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.utils.StringUtils;

/**
 * 用于某种动态情况的提醒，如更新停服
 * Created by li on 2018/3/24.
 */

public class SetEmailDialog extends Dialog implements View.OnClickListener {

    private LayoutInflater inflater;

    private ImageView btnClose;
    private TextView btnConfirm;
    private FrameLayout flDialogContent;
    private SetEmailCallBack callBack;
    private EditText etEmail;

    public SetEmailDialog(@NonNull Context context, SetEmailCallBack callBack) {
        super(context, R.style.Dialog);
        inflater = LayoutInflater.from(context);
        this.callBack = callBack;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.transparency_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_base_multe);
        initView();

    }

    public void initView() {
        btnClose = findViewById(R.id.iv_dialog_close);
        btnConfirm = findViewById(R.id.btn_dialog_confirm);
        btnConfirm = findViewById(R.id.btn_dialog_confirm);
        flDialogContent = findViewById(R.id.fl_invest_content);
        btnConfirm.setOnClickListener(this);
        btnClose.setOnClickListener(this);
        btnConfirm.setText("确定");
        View setEmail = inflater.inflate(R.layout.dialog_set_email, null);
        etEmail = setEmail.findViewById(R.id.et_email);
        flDialogContent.addView(setEmail);
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
                String email = etEmail.getText().toString();
                if (!StringUtils.isEmail(email)) {
                    BaseApplication.getAppComponent().customToast().setText("请输入正确的email格式");
                    return;
                }
                callBack.callBack(etEmail.getText().toString());
                dismiss();
                break;
            case R.id.iv_dialog_close:
                dismiss();
                break;
        }
    }

    public interface SetEmailCallBack {
        void callBack(String email);
    }
}
