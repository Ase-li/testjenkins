package com.chd.chd56lc.ui.activity.circum;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.dagger.component.DaggerMessageOrClassRoomComponent;
import com.chd.chd56lc.dagger.modules.MessageOrClassRoomModule;
import com.chd.chd56lc.entity.MessageBean;
import com.chd.chd56lc.mvp.presenter.MessagePresenter;
import com.chd.chd56lc.mvp.view.IMessageDetailView;
import com.chd.chd56lc.ui.base.BaseActivity;
import com.chd.chd56lc.utils.Logger;
import com.chd.chd56lc.utils.StringUtils;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;

public class NotifyDetailActivity extends BaseActivity implements IMessageDetailView {
    public static final String ID = "id";
    @BindView(R.id.tv_notify_title)
    TextView tvNotifyTitle;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_notify_content)
    TextView tvNotifyContent;
    @BindView(R.id.tv_notify_time)
    TextView tvNotifyTime;
    private String id;

    @Named("MessageDetail")
    @Inject
    MessagePresenter messagePresenter;

    @Override
    public int loadLayoutResID() {
        return R.layout.activity_notify_detail;
    }


    @Override
    public void initView() {
        DaggerMessageOrClassRoomComponent.builder().appComponent(BaseApplication.getAppComponent())
                .messageOrClassRoomModule(new MessageOrClassRoomModule(this)).build().inject(this);
        id = getIntent().getStringExtra(ID);
        Logger.d("消息", id + "");
        if (StringUtils.isEmpty(id))
            return;

        messagePresenter.getMessageById(id);
    }

    @Override
    public void setMessageDetail(MessageBean messageDetail) {
        setTitle(messageDetail.getMsgTitle());
        tvNotifyTitle.setText(messageDetail.getMsgTitle());
        tvNotifyTime.setText(messageDetail.getPushTime());
        tvNotifyContent.setText(messageDetail.getMsgContext());
        if (!StringUtils.isEmpty(messageDetail.getImgUrl())) {
            Glide.with(this).load(messageDetail.getImgUrl()).into(ivPic);
        } else {
            ivPic.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroy() {
        if (messagePresenter != null)
            messagePresenter.onUnsubscribe();
        super.onDestroy();
    }
}
