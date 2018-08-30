package com.chd.chd56lc.event;

/**
 * 主页跳转事件
 */
public class MainJumpEvent {
    private int jumpTab;
    private boolean isMain;//是否为主页的某个fragment

    public MainJumpEvent(int jumpTab,boolean isMain) {
        this.jumpTab = jumpTab;
        this.isMain=isMain;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public int getJumpTab() {
        return jumpTab;
    }

    public void setJumpTab(int jumpTab) {
        this.jumpTab = jumpTab;
    }
}
