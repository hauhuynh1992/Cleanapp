package com.example.huynhphihau.cleanservice.dashboard.periodic;

import com.example.huynhphihau.cleanservice.base.BaseView;
import com.example.huynhphihau.cleanservice.data.response.ReportData;

import java.util.ArrayList;

/**
 * Created by huynhphihau on 1/2/18.
 */

public interface PeriodicContact  {

    interface PeriodicView extends BaseView {
        void displayData(ArrayList<ReportData> reportData);

        void refreshData();
    }

    interface PeriodicPresenter {
        void getPeriodicHistory(int skip, int job_type);
    }

}
