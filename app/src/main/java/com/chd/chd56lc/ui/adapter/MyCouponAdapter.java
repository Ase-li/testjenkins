package com.chd.chd56lc.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.CouponBean;
import com.chd.chd56lc.ui.adapter.baseAdapter.CommonAdapter;
import com.chd.chd56lc.ui.adapter.baseAdapter.base.ViewHolder;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;

import java.util.List;

public class MyCouponAdapter extends CommonAdapter<CouponBean.Item> {

    public MyCouponAdapter(Context context, List<CouponBean.Item> datas) {
        super(context, R.layout.item_my_coupon, datas);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(ViewHolder holder, CouponBean.Item item, int position) {
        RelativeLayout rlMyCoupon = holder.getView(R.id.rl_my_coupon); //item背景
        TextView tvCouponStatus = holder.getView(R.id.tv_coupon_status);//优惠券状态
        TextView tvCouponType = holder.getView(R.id.tv_coupon_type);//优惠券类型
        TextView tvCouponName = holder.getView(R.id.tv_coupon_name);//优惠券名称
        TextView tvRemark1 = holder.getView(R.id.tv_remark1);//优惠券备注1
        TextView tvRemark2 = holder.getView(R.id.tv_remark2);//优惠券备注2
        TextView tvCouponAmount = holder.getView(R.id.tv_coupon_amount);//红包金额
        TextView tvUnit = holder.getView(R.id.tv_unit);//单位 仅在红包时显示
        ImageView ivCouponIcon = holder.getView(R.id.iv_coupon_icon);//提现券图标
        TextView tvCouponValidity = holder.getView(R.id.tv_coupon_validity);//优惠券有效时间
        tvCouponStatus.setText(item.getUserStatusName());
        tvCouponStatus.setBackgroundResource(item.getUserStatusBackground());
        tvCouponName.setText(item.getCouponName());
        tvCouponValidity.setText("有效期至" + item.getEndTime());
        tvCouponType.setText(item.getCouponTypeName());
        switch (item.getCouponType()) {//1：提现卷，2：加息劵，3：现金红包，4：抵扣红包
            case 1:
                rlMyCoupon.setBackgroundResource(R.mipmap.img_yhq_bj_t);
                tvCouponType.setTextColor(UIUtils.getColor(R.color.color_ff9726));
                tvCouponType.setBackgroundResource(R.drawable.bg_coupon_type_carry_cash);
                tvUnit.setVisibility(View.GONE);
                tvRemark2.setVisibility(View.GONE);
                tvCouponAmount.setVisibility(View.GONE);
                ivCouponIcon.setVisibility(View.VISIBLE);
                tvRemark1.setText("抵扣一次提现手续费");
                tvRemark1.setTextColor(UIUtils.getColor(R.color.color_ff9726));
                break;
            case 2:
                rlMyCoupon.setBackgroundResource(R.mipmap.img_jxq_bj);
                StringBuilder projectNames = new StringBuilder();
                tvCouponType.setTextColor(UIUtils.getColor(R.color.color_6861ff));
                tvCouponType.setBackgroundResource(R.drawable.bg_coupon_type_rate);
                tvUnit.setVisibility(View.VISIBLE);
                tvCouponAmount.setVisibility(View.VISIBLE);
                ivCouponIcon.setVisibility(View.GONE);
                tvUnit.setText("%");
                tvUnit.setTextColor(UIUtils.getColor(R.color.color_6861ff));
                tvCouponAmount.setText(NumberFormalUtils.numberFormat(item.getAddInterestRate() * 100, 0, 2) + "");
                tvCouponAmount.setTextColor(UIUtils.getColor(R.color.color_6861ff));
                tvRemark1.setText("加息" + item.getAddRateDays() + "天");
                for (CouponBean.Item.ProjectListBean projectListBean : item.getProjectList()) {
                    projectNames.append(projectListBean.getProjectName() + "、");
                }
                if (item.getProjectList() != null && item.getProjectList().size() > 0) {
                    tvRemark2.setText("适用" + projectNames.toString().substring(0, projectNames.length() - 1) + "项目");
                    TextViewSpanUtils.setForegroundColor(tvRemark2, projectNames.toString().substring(0, projectNames.length() - 1), UIUtils.getColor(R.color.color_6861ff));
                }
                break;
            case 4:
                StringBuilder projectName = new StringBuilder();
                rlMyCoupon.setBackgroundResource(R.mipmap.img_xjhb_bj);
                tvCouponType.setTextColor(UIUtils.getColor(R.color.color_ff1f1f));
                tvUnit.setVisibility(View.VISIBLE);
                tvCouponAmount.setVisibility(View.VISIBLE);
                ivCouponIcon.setVisibility(View.GONE);
                tvCouponType.setBackgroundResource(R.drawable.bg_coupon_type_cash);
                tvUnit.setText("元");
                tvUnit.setTextColor(UIUtils.getColor(R.color.color_ff1f1f));
                tvCouponAmount.setText(NumberFormalUtils.numberFormat(item.getDiscountAmount(), 0, 2) + "");
                tvCouponAmount.setTextColor(UIUtils.getColor(R.color.color_ff1f1f));
                if (item.getDiscountAmount() > 999) {
                    tvCouponAmount.setTextSize(26);
                }
                tvRemark1.setText("投资额≥" + item.getLimitAmount());
                for (CouponBean.Item.ProjectListBean projectListBean : item.getProjectList()) {
                    projectName.append(projectListBean.getProjectName()).append("、");
                }
                if (item.getProjectList() != null && item.getProjectList().size() > 0) {
                    tvRemark2.setText("适用" + projectName.toString().substring(0, projectName.length() - 1) + "项目");
                    TextViewSpanUtils.setForegroundColor(tvRemark2, projectName.toString().substring(0, projectName.length() - 1), UIUtils.getColor(R.color.color_ff1f1f));
                }
                break;
        }
    }
}
