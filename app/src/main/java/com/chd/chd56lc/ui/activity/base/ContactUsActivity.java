package com.chd.chd56lc.ui.activity.base;

import com.chd.chd56lc.R;
import com.chd.chd56lc.ui.base.BaseActivity;

public class ContactUsActivity extends BaseActivity {

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_contact_us;
    }

    @Override
    public void initView() {
        setTitle("联系我们");
    }
}
