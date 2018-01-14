package com.example.huynhphihau.cleanservice.dashboard.history;

import com.example.huynhphihau.cleanservice.base.BaseView;
import com.example.huynhphihau.cleanservice.data.response.ReportData;

import java.util.ArrayList;

/**
 * Created by huynhphihau on 1/2/18.
 */

public interface HistoryContact {

    interface HistoryView extends BaseView {
        void displayData(ArrayList<ReportData> reportData, int numUnRead);

        void refreshData();
    }

    interface HistoryPresenter {
        void getCompleteReport(int skip, int status);
    }

}