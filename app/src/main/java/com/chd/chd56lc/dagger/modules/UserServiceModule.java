package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.LoginInfoModel;
import com.chd.chd56lc.mvp.model.UserServerModel;
import com.chd.chd56lc.mvp.presenter.CaptchaPresenter;
import com.chd.chd56lc.mvp.presenter.ClubPresenter;
import com.chd.chd56lc.mvp.presenter.CommissionPresenter;
import com.chd.chd56lc.mvp.presenter.GesturePresenter;
import com.chd.chd56lc.mvp.presenter.ModifyPasswordPresenter;
import com.chd.chd56lc.mvp.presenter.ModifyPhonePresenter;
import com.chd.chd56lc.mvp.presenter.PersonCenterPresenter;
import com.chd.chd56lc.mvp.presenter.PersonSetPresenter;
import com.chd.chd56lc.mvp.presenter.PushSetPresenter;
import com.chd.chd56lc.mvp.presenter.RiskAssessmentPresenter;
import com.chd.chd56lc.mvp.view.IClubView;
import com.chd.chd56lc.mvp.view.ICommissionDetailView;
import com.chd.chd56lc.mvp.view.ICommissionInfoView;
import com.chd.chd56lc.mvp.view.ICreateGestureView;
import com.chd.chd56lc.mvp.view.IFriendView;
import com.chd.chd56lc.mvp.view.ILoginGestureView;
import com.chd.chd56lc.mvp.view.IModifyLoginPassword;
import com.chd.chd56lc.mvp.view.IPersonCenterView;
import com.chd.chd56lc.mvp.view.IPersonSetView;
import com.chd.chd56lc.mvp.view.IPushSetView;
import com.chd.chd56lc.mvp.view.IRiskAssessmentView;
import com.chd.chd56lc.mvp.view.ISafeVerifyView;
import com.chd.chd56lc.mvp.view.ISetPhoneView;
import com.chd.chd56lc.mvp.view.LoadingView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class UserServiceModule {
    private IModifyLoginPassword iModifyLoginPassword;
    private IPersonCenterView iPersonCenterView;
    private IPersonSetView iPersonSetView;
    private IPushSetView iPushSetView;
    private ISetPhoneView iSetPhoneView;
    private LoadingView loadingView;
    private ILoginGestureView iLoginGestureView;
    private ICreateGestureView iCreateGestureView;
    private IRiskAssessmentView iRiskAssessmentView;
    private ISafeVerifyView iSafeVerifyView;
    private ICommissionInfoView iCommissionInfoView;
    private ICommissionDetailView iCommissionDetailView;
    private IFriendView iFriendView;
    private IClubView iClubView;

    public UserServiceModule(IModifyLoginPassword iModifyLoginPassword) {
        this.iModifyLoginPassword = iModifyLoginPassword;
        this.loadingView = iModifyLoginPassword;
    }

    public UserServiceModule(ISetPhoneView iSetPhoneView) {
        this.iSetPhoneView = iSetPhoneView;
        this.loadingView = iSetPhoneView;
    }

    public UserServiceModule(IPushSetView iPushSetView) {
        this.iPushSetView = iPushSetView;
    }

    public UserServiceModule(IPersonCenterView iPersonCenterView) {
        this.iPersonCenterView = iPersonCenterView;
    }

    public UserServiceModule(IPersonSetView iPersonSetView) {
        this.iPersonSetView = iPersonSetView;
    }

    public UserServiceModule(LoadingView loadingView) {
        this.loadingView = loadingView;
    }

    public UserServiceModule(ILoginGestureView iLoginGestureView) {
        this.iLoginGestureView = iLoginGestureView;
    }

    public UserServiceModule(ICreateGestureView iCreateGestureView) {
        this.iCreateGestureView = iCreateGestureView;
    }

    public UserServiceModule(IRiskAssessmentView iRiskAssessmentView) {
        this.iRiskAssessmentView = iRiskAssessmentView;
    }

    public UserServiceModule(ISafeVerifyView iSafeVerifyView) {
        this.iSafeVerifyView = iSafeVerifyView;
        this.loadingView = iSafeVerifyView;
    }

    public UserServiceModule(ICommissionInfoView iCommissionInfoView) {
        this.iCommissionInfoView = iCommissionInfoView;
    }

    public UserServiceModule(ICommissionDetailView iCommissionDetailView) {
        this.iCommissionDetailView = iCommissionDetailView;
    }

    public UserServiceModule(IFriendView iFriendView) {
        this.iFriendView = iFriendView;
    }

    public UserServiceModule(IClubView iClubView) {
        this.iClubView = iClubView;
    }

    @Provides
    public UserServerModel providePersonInfoModel() {
        return new UserServerModel();
    }

    @Provides
    public LoginInfoModel provideLoginInfoModel() {
        return new LoginInfoModel();
    }


    @Provides
    public CaptchaPresenter provideCaptchaPresenter(Retrofit retrofit) {
        return new CaptchaPresenter(loadingView, retrofit);
    }

    @Provides
    public PersonCenterPresenter providePersonCenterPresenter(UserServerModel personInfoModel) {
        return new PersonCenterPresenter(iPersonCenterView, personInfoModel);
    }

    @Provides
    public PersonSetPresenter providePersonSetPresenter(UserServerModel personInfoModel, LoginInfoModel loginInfoModel) {
        return new PersonSetPresenter(iPersonSetView, personInfoModel, loginInfoModel);
    }

    @Provides
    public PushSetPresenter providePushSetPresenter(UserServerModel personInfoModel) {
        return new PushSetPresenter(iPushSetView, personInfoModel);
    }

    @Named("resetPhoneStepTwo")
    @Provides
    public ModifyPhonePresenter provideModifyPhonePresenterTwo(UserServerModel personInfoModel) {
        return new ModifyPhonePresenter(iSetPhoneView, personInfoModel);
    }

    @Named("resetPhoneStepOne")
    @Provides
    public ModifyPhonePresenter provideModifyPhonePresenterOne(UserServerModel personInfoModel) {
        return new ModifyPhonePresenter(iSafeVerifyView, personInfoModel);
    }

    @Named("createGesture")
    @Provides
    public GesturePresenter provideGesturePresenter(UserServerModel userServerModel) {
        return new GesturePresenter(iCreateGestureView, userServerModel);
    }

    @Named("loginGesture")
    @Provides
    public GesturePresenter provideLoginGesturePresenter(UserServerModel userServerModel) {
        return new GesturePresenter(iLoginGestureView, userServerModel);
    }

    @Provides
    public RiskAssessmentPresenter provideRiskAssessmentPresenter(UserServerModel userServerModel) {
        return new RiskAssessmentPresenter(iRiskAssessmentView, userServerModel);
    }

    @Provides
    public ModifyPasswordPresenter provideModifyPasswordPresenter(UserServerModel userServerModel) {
        return new ModifyPasswordPresenter(iModifyLoginPassword, userServerModel);
    }

    @Named("commissionInfo")
    @Provides
    public CommissionPresenter provideCommissionInfoPresenter(UserServerModel userServerModel) {
        return new CommissionPresenter(iCommissionInfoView, userServerModel);
    }

    @Named("CommissionDetail")
    @Provides
    public CommissionPresenter provideCommissionDetailPresenter(UserServerModel userServerModel) {
        return new CommissionPresenter(iCommissionDetailView, userServerModel);
    }

    @Named("FriendView")
    @Provides
    public CommissionPresenter provideFriendViewPresenter(UserServerModel userServerModel) {
        return new CommissionPresenter(iFriendView, userServerModel);
    }

    @Provides
    public ClubPresenter provideClubPresenter(UserServerModel userServerModel) {
        return new ClubPresenter(iClubView, userServerModel);
    }
}
