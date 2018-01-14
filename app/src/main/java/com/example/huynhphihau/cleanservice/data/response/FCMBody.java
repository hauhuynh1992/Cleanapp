package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by phihau on 8/3/2017.
 */

public class FCMBody {
    @SerializedName("report_id")
    @Expose
    String reportId;
    @SerializedName("type")
    @Expose
    String type;

    public String getReportID() {
        return reportId;
    }

    public String getType() {
        return type;
    }
}
