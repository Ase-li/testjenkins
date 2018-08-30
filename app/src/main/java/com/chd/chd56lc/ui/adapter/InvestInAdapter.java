package com.chd.chd56lc.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chd.chd56lc.R;
import com.chd.chd56lc.entity.AssetInvestInBean;
import com.chd.chd56lc.ui.activity.investment.OrderDetailActivity;
import com.chd.chd56lc.utils.NumberFormalUtils;
import com.chd.chd56lc.utils.StringUtils;
import com.chd.chd56lc.utils.TextViewSpanUtils;
import com.chd.chd56lc.utils.UIUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InvestInAdapter extends BaseExpandableListAdapter {

    private ArrayList<AssetInvestInBean> myInvestments;
    private Context mContext;

    public InvestInAdapter(ArrayList<AssetInvestInBean> myInvestments, Context mContext) {
        this.myInvestments = myInvestments;
        this.mContext = mContext;
    }

    /**
     * Gets the number of groups.
     *
     * @return the number of groups
     */
    @Override
    public int getGroupCount() {
        return myInvestments.size();
    }

    /**
     * Gets the number of children in a specified group.
     *
     * @param groupPosition the position of the group for which the children
     *                      count should be returned
     * @return the children count in the specified group
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        return myInvestments.get(groupPosition).getArray().size();
    }

    /**
     * Gets the data associated with the given group.
     *
     * @param groupPosition the position of the group
     * @return the data child for the specified group
     */
    @Override
    public AssetInvestInBean getGroup(int groupPosition) {
        return myInvestments.get(groupPosition);
    }

    /**
     * Gets the data associated with the given child within the given group.
     *
     * @param groupPosition the position of the group that the child resides in
     * @param childPosition the position of the child with respect to other
     *                      children in the group
     * @return the data of the child
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return myInvestments.get(groupPosition).getArray().get(childPosition);
    }

    /**
     * Gets the ID for the group at the given position. This group ID must be
     * unique across groups. The combined ID (see
     * {@link #getCombinedGroupId(long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group for which the ID is wanted
     * @return the ID associated with the group
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * Gets the ID for the given child within the given group. This ID must be
     * unique across all children within the group. The combined ID (see
     * {@link #getCombinedChildId(long, long)}) must be unique across ALL items
     * (groups and all children).
     *
     * @param groupPosition the position of the group that contains the child
     * @param childPosition the position of the child within the group for which
     *                      the ID is wanted
     * @return the ID associated with the child
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderGroup viewHolderGroup = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_invest_in_group, parent, false);
            viewHolderGroup = new ViewHolderGroup(convertView);
            convertView.setTag(viewHolderGroup);
        } else {
            viewHolderGroup = (ViewHolderGroup) convertView.getTag();
        }
        AssetInvestInBean item = myInvestments.get(groupPosition);
        if (item.isShow()) {
            viewHolderGroup.tvGroupIcon.setImageResource(R.mipmap.icon_item_right_sel);
        } else {
            viewHolderGroup.tvGroupIcon.setImageResource(R.mipmap.icon_item_right_nor);
        }
        viewHolderGroup.tvGroupMoney.setText(item.getTotalAmount() + "元");
        viewHolderGroup.tvGroupName.setText(item.getProjectName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolderChild viewHolderChild = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_invest_in, parent, false);
            viewHolderChild = new ViewHolderChild(convertView);
            convertView.setTag(viewHolderChild);
        } else {
            viewHolderChild = (ViewHolderChild) convertView.getTag();
        }
        final AssetInvestInBean.Item child = myInvestments.get(groupPosition).getArray().get(childPosition);
        viewHolderChild.ivStatus.setVisibility(View.GONE);
        viewHolderChild.tvDebtName.setText(child.getBidName());
        viewHolderChild.tvPossessMoney.setText(NumberFormalUtils.numberFormat(child.getCurrentAmount(), 0, 0));
        viewHolderChild.tvAccumulatedIncome.setText(NumberFormalUtils.numberFormat(child.getCumulativeIncome(), 2, 2));
        viewHolderChild.tvYearRate.setText(NumberFormalUtils.percentFormat(child.getAnnualRate(), 2, 2));
        TextViewSpanUtils.setAbsoluteSizeSpan(viewHolderChild.tvYearRate, "%", 12);
        if (child.getOtherRate() != 0) {
            viewHolderChild.tvOtherRate.setVisibility(View.VISIBLE);
            viewHolderChild.tvOtherRate.setText(NumberFormalUtils.percentFormat(child.getOtherRate(), 2, 2));
            TextViewSpanUtils.setAbsoluteSizeSpan(viewHolderChild.tvOtherRate, "%", 12);
        } else {
            viewHolderChild.tvOtherRate.setVisibility(View.GONE);
        }
        if (child.getType() == 2) {
            viewHolderChild.tvProgress2.setVisibility(View.GONE);
            viewHolderChild.tvProgressTime2.setVisibility(View.GONE);
        } else {
            viewHolderChild.tvProgress2.setVisibility(View.VISIBLE);
            viewHolderChild.tvProgressTime2.setVisibility(View.VISIBLE);
        }
        if (child.getInterestWay() == 2) {
            viewHolderChild.tvAccumulatedIncomeHint.setText("到期收益");
        } else {
            viewHolderChild.tvAccumulatedIncomeHint.setText("累计收益");
        }
        viewHolderChild.tvHint1.setText(child.getLabelString());
        if (child.getLabel() == 4) {
            viewHolderChild.tvHint1.setEnabled(false);
            viewHolderChild.tvHint2.setVisibility(View.VISIBLE);
            viewHolderChild.tvHint2.setText(child.getLabel2String());
            if (child.getTransferLabel() == 1) viewHolderChild.tvHint2.setEnabled(false);
            else viewHolderChild.tvHint2.setEnabled(true);
        } else {
            viewHolderChild.tvHint1.setEnabled(true);
            viewHolderChild.tvHint2.setVisibility(View.GONE);
        }
        viewHolderChild.tvProgressTime1.setText(StringUtils.replacePoint(child.getInvestTime()));
        viewHolderChild.tvProgressTime2.setText(StringUtils.replacePoint1(child.getPaymentTime()));
        if (child.getInvestDay() == 0)
            viewHolderChild.tvProgressTime3.setText("");
        else
            viewHolderChild.tvProgressTime3.setText(child.getInvestDay() + "天");
        viewHolderChild.tvProgressTime4.setText(StringUtils.replacePoint(child.getExpireTime()));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.startActivity(new Intent(mContext, OrderDetailActivity.class)
                        .putExtra(OrderDetailActivity.ORDER_ID, child.getId()))
                ;
            }
        });
        switch (child.getRaiseStage()) {
            case 1:
                viewHolderChild.pbProgress.setProgress(0);
                viewHolderChild.tvProgress2.setEnabled(false);
                viewHolderChild.tvProgress3.setEnabled(false);
                viewHolderChild.tvProgress4.setEnabled(false);
                break;
            case 2:
                viewHolderChild.pbProgress.setProgress(75);
                viewHolderChild.tvProgress2.setEnabled(true);
                viewHolderChild.tvProgress3.setEnabled(true);
                viewHolderChild.tvProgress4.setEnabled(false);
                break;
            case 3:
                viewHolderChild.pbProgress.setProgress(100);
                viewHolderChild.tvProgress2.setEnabled(true);
                viewHolderChild.tvProgress3.setEnabled(true);
                viewHolderChild.tvProgress4.setEnabled(true);
                break;
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolderGroup {
        @BindView(R.id.tv_group_name)
        TextView tvGroupName;
        @BindView(R.id.iv_group_icon)
        ImageView tvGroupIcon;
        @BindView(R.id.tv_group_money)
        TextView tvGroupMoney;

        ViewHolderGroup(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolderChild {
        @BindView(R.id.tv_possess_money)
        TextView tvPossessMoney;
        @BindView(R.id.iv_status)
        ImageView ivStatus;
        @BindView(R.id.tv_year_rate)
        TextView tvYearRate;
        @BindView(R.id.tv_other_rate)
        TextView tvOtherRate;
        @BindView(R.id.tv_accumulated_income_hint)
        TextView tvAccumulatedIncomeHint;
        @BindView(R.id.tv_debt_name)
        TextView tvDebtName;
        @BindView(R.id.tv_accumulated_income)
        TextView tvAccumulatedIncome;
        @BindView(R.id.pb_progress)
        ProgressBar pbProgress;
        @BindView(R.id.tv_progress_1)
        TextView tvProgress1;
        @BindView(R.id.tv_progress_time_1)
        TextView tvProgressTime1;
        @BindView(R.id.tv_progress_2)
        TextView tvProgress2;
        @BindView(R.id.tv_progress_time_2)
        TextView tvProgressTime2;
        @BindView(R.id.tv_progress_3)
        TextView tvProgress3;
        @BindView(R.id.tv_progress_time_3)
        TextView tvProgressTime3;
        @BindView(R.id.tv_progress_4)
        TextView tvProgress4;
        @BindView(R.id.tv_progress_time_4)
        TextView tvProgressTime4;
        @BindView(R.id.tv_hint1)
        TextView tvHint1;
        @BindView(R.id.tv_hint2)
        TextView tvHint2;

        ViewHolderChild(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
