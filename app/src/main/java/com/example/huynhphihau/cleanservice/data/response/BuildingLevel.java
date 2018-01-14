package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhphihau on 12/20/17.
 */

public class BuildingLevel {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("level_name")
    @Expose
    private String level_name;
    @SerializedName("building_id")
    @Expose
    private long building_id;

    public long getId() {
        return id;
    }

    public String getLevelName() {
        return level_name;
    }

    public long getBuildingId() {
        return building_id;
    }
}
