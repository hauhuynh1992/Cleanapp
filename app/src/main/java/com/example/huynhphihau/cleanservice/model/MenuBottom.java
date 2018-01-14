package com.example.huynhphihau.cleanservice.model;

/**
 * Created by phihau on 4/24/2017.
 */

public class MenuBottom {
    private String title;
    private String sub_title;

    public MenuBottom(String title, String sub_title) {
        this.title = title;
        this.sub_title = sub_title;
    }

    public String getTitle() {
        return title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }
}