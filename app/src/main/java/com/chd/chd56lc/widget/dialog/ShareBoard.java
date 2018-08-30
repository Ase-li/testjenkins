package com.chd.chd56lc.widget.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.chd.chd56lc.R;
import com.chd.chd56lc.common.BaseApplication;
import com.chd.chd56lc.entity.SocialShareData;
import com.chd.chd56lc.utils.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * @param
 * @author shulan 作者 E-mail:
 * 创建时间：2016-7-1
 * 类说明
 */
public class ShareBoard extends PopupWindow implements OnClickListener {


    private Context mContext;
    private ShareResponse mShareResponse;
    private Activity mActivity;
    private SocialShareData data;

    private View wechat;
    private View wechat_circle;
    private View qq;


    //活动分享
    public ShareBoard(Activity activity, SocialShareData data, ShareResponse shareResponse) {
        super(activity);
        this.mActivity = activity;
        mContext = activity;
        mShareResponse = shareResponse;
        this.data = data;

        //replaceBlank();

        initView(activity);
    }

    @SuppressWarnings("deprecation")
    private void initView(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.pop_share_board, null);
        wechat = rootView.findViewById(R.id.wechat);
        wechat.setOnClickListener(this);
        wechat_circle = rootView.findViewById(R.id.wechat_circle);
        wechat_circle.setOnClickListener(this);
        qq = rootView.findViewById(R.id.qq);
        qq.setOnClickListener(this);
        rootView.findViewById(R.id.tv_cancel).setOnClickListener(this);

        setContentView(rootView);
        setWidth(LayoutParams.MATCH_PARENT);
        setHeight(LayoutParams.WRAP_CONTENT);
        //设置SelectPicPopupWindow弹出窗体动画效果
        setAnimationStyle(R.style.mypopwindow_anim_style);
        setFocusable(true);

        setBackgroundDrawable(new BitmapDrawable());

        setTouchable(true);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.wechat:
                performShare(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.wechat_circle:
                performShare(SHARE_MEDIA.WEIXIN_CIRCLE);
                break;
            case R.id.qq:
                performShare(SHARE_MEDIA.QQ);
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void reSet() {
        if (wechat != null && wechat_circle != null && qq != null) {
            wechat.setEnabled(true);
            qq.setEnabled(true);
            wechat_circle.setEnabled(true);
            dismiss();
        }
    }

    private void performShare(SHARE_MEDIA platform) {
        wechat.setEnabled(false);
        qq.setEnabled(false);
        wechat_circle.setEnabled(false);
        UMWeb umWeb = new UMWeb(data.webpageUrl, data.title, data.content, new UMImage(mContext, data.thumImage));
        new ShareAction(mActivity)
                .setPlatform(platform)//传入平台
                .withMedia(umWeb)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Logger.e("分享开始回调");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Logger.e("平台分享成功");
                        if (mShareResponse != null) {
                            mShareResponse.setRet(1);
                        }
                        reSet();
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        if (throwable.getMessage().contains("没有安装应用")) {
                            switch (share_media) {
                                case QQ:
                                    BaseApplication.getAppComponent().customToast().setText("请检测是否安装QQ");
                                    break;
                                case WEIXIN:
                                    BaseApplication.getAppComponent().customToast().setText("请检测是否安装微信");
                                    break;
                                case WEIXIN_CIRCLE:
                                    BaseApplication.getAppComponent().customToast().setText("请检测是否安装微信");
                                    break;
                            }
                        }

                        if (mShareResponse != null) {
                            mShareResponse.setRet(0);
                        }
                        reSet();
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Logger.d("分享取消");
                        reSet();
                    }
                })//回调监听器
                .share();
    }

    public void popupDismiss() {
        dismiss();
    }

    public interface ShareResponse {
        void setRet(int ret);
    }

}
