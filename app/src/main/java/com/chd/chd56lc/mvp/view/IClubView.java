package com.chd.chd56lc.mvp.view;

import java.util.List;

/**
 * 申请VIP
 */
public interface IClubView extends LoadingView {
    /**
     * 申请结果
     *
     * @param result
     */
    void applyResult(boolean result);

    /**
     * 状态
     *
     * @param status
     */
    void userFinancialPlannerStatus(List<Integer> status);
}
