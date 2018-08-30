package com.chd.chd56lc.ui.base;

import android.graphics.Rect;
import android.view.View;
import android.widget.LinearLayout;

import com.chd.chd56lc.R;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.SoftKeyBroadManager;

import butterknife.BindView;

public abstract class KeyBoardBaseActivity extends BaseActivity {
    @BindView(R.id.base_view)
    LinearLayout baseView;
    private View btn;

    @Override
    public void initView() {
        btn = findViewById(getBtnId());
        initKeyBroad();
    }

    private void initKeyBroad() {
        SoftKeyBroadManager softKeyBroadManager = new SoftKeyBroadManager(findViewById(R.id.base_view));
        softKeyBroadManager.addSoftKeyboardStateListener(new SoftKeyBroadManager.SoftKeyboardStateListener() {
            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                Rect rect = new Rect();
                //获取root在窗体的可视区域
                baseView.getWindowVisibleDisplayFrame(rect);
                //若不可视区域高度大于100，则键盘显示
                int[] location = new int[2];
                //获取scrollToView在窗体的坐标
                btn.getLocationInWindow(location);
                //计算root滚动高度，使scrollToView在可见区域的底部
                int srollHeight = (location[1] + btn.getHeight()) - rect.bottom;
                baseView.scrollTo(0, srollHeight);
                Logger.d("键盘", srollHeight + "--");
            }

            @Override
            public void onSoftKeyboardClosed() {
                Logger.d("键盘", "消失");
                baseView.scrollTo(0, 0);
            }
        });
    }

    public abstract int getBtnId();
}
