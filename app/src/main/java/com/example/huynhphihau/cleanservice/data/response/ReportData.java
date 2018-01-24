package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by huynhphihau on 12/16/17.
 */

public class ReportData {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("building_name")
    @Expose
    private String buildingName;
    @SerializedName("building_level_name")
    @Expose
    private String buildingLevelName;
    @SerializedName("company_name")
    @Expose
    private String companyName;
    @SerializedName("report_option_name")
    @Expose
    private String reportOptionName;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("job_type")
    @Expose
    private int jobType;
    @SerializedName("completed_time")
    @Expose
    private String completedTime;
    @SerializedName("created_at")
    @Expose
    private String createTime;
    @SerializedName("images")
    @Expose
    private ArrayList<Image> images = null;
    @SerializedName("isRead")
    @Expose
    private int isRead;
    @SerializedName("isRated")
    @Expose
    private int isRated;

    public int getIsRated() {
        return isRated;
    }

    public int getIsRead() {
        return isRead;
    }

    public String getCreateTime() {
        return createTime;
    }

    public int getJobType() {
        return jobType;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getRemark() {
        return remark;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public String getBuildingLevelName() {
        return buildingLevelName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getReportOptionName() {
        return reportOptionName;
    }

    public int getStatus() {
        return status;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public ArrayList<Image> getImages() {
        return images;
    }
}
