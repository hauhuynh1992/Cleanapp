package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by huynhphihau on 1/3/18.
 */

public class ListReport {

    @SerializedName("data")
    @Expose
    private ArrayList<ReportData> data = null;
    @SerializedName("number_unread")
    @Expose
    private Integer numberUnread;

    public ArrayList<ReportData> getData() {
        return data;
    }

    public Integer getNumberUnread() {
        return numberUnread;
    }
}