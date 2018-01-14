package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by huynhphihau on 12/20/17.
 */

public class Company {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("building_id")
    @Expose
    private long buildingId;
    @SerializedName("building_level_id")
    @Expose
    private long buildingLevelId;
    @SerializedName("address")
    @Expose
    private String address;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public long getBuildingId() {
        return buildingId;
    }

    public long getBuildingLevelId() {
        return buildingLevelId;
    }

    public String getAddress() {
        return address;
    }
}
