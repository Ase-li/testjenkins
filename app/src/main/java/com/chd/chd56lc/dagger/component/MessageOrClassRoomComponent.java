package com.chd.chd56lc.dagger.component;

import com.chd.chd56lc.dagger.modules.MessageOrClassRoomModule;
import com.chd.chd56lc.dagger.scopes.PerActivity;
import com.chd.chd56lc.ui.activity.circum.NotifyDetailActivity;
import com.chd.chd56lc.ui.fragment.KnowledgeFragment;
import com.chd.chd56lc.ui.fragment.MyMessageFragment;
import com.chd.chd56lc.ui.fragment.NotifyFragment;
import com.chd.chd56lc.ui.fragment.RiskEducationFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = AppComponent.class, modules = MessageOrClassRoomModule.class)
public interface MessageOrClassRoomComponent {
    void inject(NotifyFragment notifyFragment);

    void inject(MyMessageFragment myMessageFragment);

    void inject(NotifyDetailActivity notifyDetailActivity);

    void inject(RiskEducationFragment riskEducationFragment);

    void inject(KnowledgeFragment knowledgeFragment);
}
