package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhphihau on 12/20/17.
 */

public class RequestData {
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("report_option_id")
    @Expose
    private Long reportOptionId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("building_id")
    @Expose
    private long buildingId;
    @SerializedName("building_level_id")
    @Expose
    private long buildingLevelId;
    @SerializedName("company_id")
    @Expose
    private long companyId;
    @SerializedName("job_type")
    @Expose
    private long job_type;
    @SerializedName("status")
    @Expose
    private long status;
    @SerializedName("id")
    @Expose
    private long id;

    public String getTitle() {
        return title;
    }

    public Long getReportOptionId() {
        return reportOptionId;
    }

    public String getRemark() {
        return remark;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public long getBuildingLevelId() {
        return buildingLevelId;
    }

    public long getCompanyId() {
        return companyId;
    }

    public long getJob_type() {
        return job_type;
    }

    public long getStatus() {
        return status;
    }

    public long getId() {
        return id;
    }
}
