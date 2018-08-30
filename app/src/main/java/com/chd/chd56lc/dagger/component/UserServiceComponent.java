package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.UserServiceModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.base.MainActivity;
import com.chd.chd56lc.ui.activity.personCenter.SetPasswordActivity;
import com.chd.chd56lc.ui.activity.personCenter.PushSetActivity;
import com.chd.chd56lc.ui.activity.personCenter.ClubActivity;
import com.chd.chd56lc.ui.activity.personCenter.ClubDetailActivity;
import com.chd.chd56lc.ui.activity.personCenter.CreateGestureActivity;
import com.chd.chd56lc.ui.activity.personCenter.GestureLoginActivity;
import com.chd.chd56lc.ui.activity.personCenter.PersonSetActivity;
import com.chd.chd56lc.ui.activity.personCenter.RiskAssessmentActivity;
import com.chd.chd56lc.ui.activity.personCenter.SafeVerifyActivity;
import com.chd.chd56lc.ui.activity.personCenter.SetPhoneActivity;
import com.chd.chd56lc.ui.fragment.CommissionDetailFragment;
import com.chd.chd56lc.ui.fragment.FriendListFragment;
import com.chd.chd56lc.ui.fragment.base.PersonCenterFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = UserServiceModule.class)
public interface UserServiceComponent {
    void inject(PersonCenterFragment personCenterFragment);

    void inject(PersonSetActivity personSetActivity);

    void inject(PushSetActivity pushSetActivity);

    void inject(SafeVerifyActivity safeVerifyActivity);

    void inject(SetPhoneActivity setPhoneActivity);

    void inject(CreateGestureActivity createGestureActivity);

    void inject(GestureLoginActivity gestureLoginActivity);

    void inject(RiskAssessmentActivity riskAssessmentActivity);

    void inject(MainActivity mainActivity);

    void inject(SetPasswordActivity setPasswordActivity);

    void inject(ClubDetailActivity clubDetailActivity);

    void inject(CommissionDetailFragment commissionDetailFragment);

    void inject(FriendListFragment friendListFragment);

    void inject(ClubActivity clubActivity);

}
