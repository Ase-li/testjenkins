package com.chd.chd56lc.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.Window;

import com.chd.chd56lc.R;


/**
 * Created by Aesop on 2017/6/9.
 */

public class UpdateVersionDialog extends Dialog {

    public UpdateVersionDialog(@NonNull Context context) {
        super(context, R.style.Dialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
//        window.setWindowAnimations(R.style.transparency_menu_animation); // 添加动画效果
        setContentView(R.layout.dialog_update_version);
        setCanceledOnTouchOutside(false);
    }


    @Override
    public void onBackPressed() {
    }
}
