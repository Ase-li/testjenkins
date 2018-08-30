package com.chd.chd56lc.mvp.presenter;

import com.chd.chd56lc.entity.CouponMsgBean;
import com.chd.chd56lc.entity.UserInfoBean;
import com.chd.chd56lc.manager.UserManager;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.view.IPersonCenterView;
import com.chd.chd56lc.net.ApiCallback;
import com.chd.chd56lc.net.NetPresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class PersonCenterPresenter extends NetPresenter {
    private IPersonCenterView iPersonCenterView;
    private UserServerModel personInfoModel;

    @Inject
    public PersonCenterPresenter(IPersonCenterView iPersonCenterView, UserServerModel personInfoModel) {
        this.iPersonCenterView = iPersonCenterView;
        this.personInfoModel = personInfoModel;
    }

    /**
     * 获取用户信息
     */
    public void getUserInfo() {
//        iPersonCenterView.showLoading();
        addSubscription(personInfoModel.getUserStatistics(), new ApiCallback<UserInfoBean>() {
            @Override
            public void onSuccess(UserInfoBean model) {
                if (model != null) {
                    UserManager.getInstance().saveUserInfo(model);
                }
                iPersonCenterView.updateUserInfo(model);
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {
                iPersonCenterView.dismissLoading();
            }
        });
    }

    public void getPersonCenterInfo() {
        addSubscription(Observable.mergeArray(personInfoModel.getUserStatistics(), personInfoModel.getExpiredCoupon()), new ApiCallback<Object>() {
            @Override
            public void onSuccess(Object model) {
                try {
                    if (model instanceof UserInfoBean) {
                        if (model != null) {
                            UserManager.getInstance().saveUserInfo((UserInfoBean) model);
                        }
                        iPersonCenterView.updateUserInfo((UserInfoBean) model);
                    } else if (model instanceof List) {
                        if (model != null && ((List) model).size() != 0)
                            iPersonCenterView.updateCouponMsg((CouponMsgBean) ((List) model).get(0));
                        else iPersonCenterView.updateCouponMsg(null);
                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int code, String msg) {

            }

            @Override
            public void onFinish() {

            }
        });
    }

}
