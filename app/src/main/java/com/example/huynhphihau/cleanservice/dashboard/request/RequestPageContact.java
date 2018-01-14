package com.example.huynhphihau.cleanservice.dashboard.request;

import android.content.Context;

import com.example.huynhphihau.cleanservice.base.BaseView;
import com.example.huynhphihau.cleanservice.data.response.Building;
import com.example.huynhphihau.cleanservice.data.response.BuildingDetail;
import com.example.huynhphihau.cleanservice.data.response.Company;
import com.example.huynhphihau.cleanservice.data.response.ReportOption;

import java.util.List;

/**
 * Created by phihau on 8/5/2017.
 */

public interface RequestPageContact {

    interface RequestPageView extends BaseView {

        void showListBuilding();

        void showListBuildingLevels();

        void showListReportOptions();

        void showListCompanies();

        void showProgress();

        void hideProgress();

        void back();

    }

    interface RequestPagePresenter {

        void getListBuilding();

        void getBuildingDetails(long id);

        void getReportOption();

        void getListCompany();

        List<Building> getBuilings();

        BuildingDetail getBuilingDetail();

        List<ReportOption> getReportOptions();

        List<Company> getCompanies();

        void createReport(String title, long reportId, String remark, long buildingId, long buildingLevelId, long companyId, long jobType, String pathFile);

        void createTempFileWithSampleSize(Context context, String originPath);

        String getImagePath();
    }
}