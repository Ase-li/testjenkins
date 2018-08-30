package com.chd.chd56lc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.chd.chd56lc.R;


/**
 * Created by Aesop on 2017/6/9.
 */

public class ProEarnDialog extends Dialog {
    private TextView title;
    private TextView tvMessage;
    private TextView tvOk;
    private String time;
    private String rate;

    public ProEarnDialog(@NonNull Context context, String time, String rate) {
        super(context, R.style.Dialog);
        this.time=time;
        this.rate=rate;
    }

    public ProEarnDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.transparency_menu_animation); // 添加动画效果
        setContentView(R.layout.pro_earm_dialog);
        setCanceledOnTouchOutside(true);
        title = (TextView) findViewById(R.id.title);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvOk = (TextView) findViewById(R.id.tv_ok);
        tvMessage.setText("预期收益按照最长天数"+time+"天，最高年化"+rate+"\n计息，不包含募集期利息和加息利息。");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface CustomDialogCallBack {
        /**
         * dialog点击确定回调
         */
        void clickOk();
    }
}
