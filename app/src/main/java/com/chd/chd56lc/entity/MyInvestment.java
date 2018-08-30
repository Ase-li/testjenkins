package com.chd.chd56lc.entity;

import java.util.ArrayList;

public class MyInvestment {
    public boolean isShow;
    public String groupName;
    private ArrayList<Object> objects;

    public MyInvestment() {
        this.isShow = false;
        this.groupName = "年无忧";
        objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            objects.add(new Object());
        }
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public void setObjects(ArrayList<Object> objects) {
        this.objects = objects;
    }
}
