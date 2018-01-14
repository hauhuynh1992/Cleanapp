package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhphihau on 12/20/17.
 */

public class ReportOption {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("option_content")
    @Expose
    private String optionContent;

    public long getId() {
        return id;
    }

    public String getOptionContent() {
        return optionContent;
    }
}
