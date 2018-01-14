package com.example.huynhphihau.cleanservice.dashboard.inspection;

import com.example.huynhphihau.cleanservice.base.BaseView;
import com.example.huynhphihau.cleanservice.data.response.ReportData;

import java.util.ArrayList;

/**
 * Created by huynhphihau on 1/2/18.
 */

public interface InspectionContact  {

    interface InspectionView extends BaseView {
        void displayData(ArrayList<ReportData> reportData);

        void refreshData();
    }

    interface InspectionPresenter {
        void getPeriodicHistory(int skip, int job_type);
    }

}
