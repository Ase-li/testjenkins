package com.chd.chd56lc.dagger.modules;

import com.chd.chd56lc.mvp.model.AmbitusModel;
import com.chd.chd56lc.mvp.presenter.ClassRoomPresenter;
import com.chd.chd56lc.mvp.presenter.MessagePresenter;
import com.chd.chd56lc.mvp.view.IClassRoomDetailView;
import com.chd.chd56lc.mvp.view.IClassRoomListView;
import com.chd.chd56lc.mvp.view.IMessageDetailView;
import com.chd.chd56lc.mvp.view.IMessageListView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MessageOrClassRoomModule {
    private IMessageListView iMessageListView;
    private IMessageDetailView iMessageDetailView;
    private IClassRoomListView iClassRoomListView;
    private IClassRoomDetailView iClassRoomDetailView;

    public MessageOrClassRoomModule(IMessageListView iMessageListView) {
        this.iMessageListView = iMessageListView;
    }

    public MessageOrClassRoomModule(IMessageDetailView iMessageDetailView) {
        this.iMessageDetailView = iMessageDetailView;
    }

    public MessageOrClassRoomModule(IClassRoomDetailView iClassRoomDetailView) {
        this.iClassRoomDetailView = iClassRoomDetailView;
    }

    public MessageOrClassRoomModule(IClassRoomListView iClassRoomListView) {
        this.iClassRoomListView = iClassRoomListView;
    }

    @Provides
    public AmbitusModel provideAmbitusModel() {
        return new AmbitusModel();
    }


    @Named("MessageList")
    @Provides
    public MessagePresenter provideMessagePresenter(AmbitusModel ambitusModel) {
        return new MessagePresenter(iMessageListView, ambitusModel);
    }

    @Named("MessageDetail")
    @Provides
    public MessagePresenter provideMessageDetailPresenter(AmbitusModel ambitusModel) {
        return new MessagePresenter(iMessageDetailView, ambitusModel);
    }

    @Named("ClassRoomList")
    @Provides
    public ClassRoomPresenter provideClassRoomPresenter(AmbitusModel ambitusModel) {
        return new ClassRoomPresenter(iClassRoomListView, ambitusModel);
    }

    @Named("ClassRoomDetail")
    @Provides
    public ClassRoomPresenter provideClassRoomDetailPresenter(AmbitusModel ambitusModel) {
        return new ClassRoomPresenter(iClassRoomDetailView, ambitusModel);
    }
}
