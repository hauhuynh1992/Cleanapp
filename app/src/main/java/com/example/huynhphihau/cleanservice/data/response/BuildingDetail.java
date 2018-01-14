package com.example.huynhphihau.cleanservice.data.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by huynhphihau on 12/20/17.
 */

public class BuildingDetail extends Building {
    @SerializedName("building_levels")
    @Expose
    private List<BuildingLevel> buildingLevels = null;

    public List<BuildingLevel> getBuildingLevels() {
        return buildingLevels;
    }
}
