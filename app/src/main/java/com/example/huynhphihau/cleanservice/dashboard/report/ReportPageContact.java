package com.example.huynhphihau.cleanservice.dashboard.report;

import android.content.Context;

import com.example.huynhphihau.cleanservice.base.BaseView;
import com.example.huynhphihau.cleanservice.data.response.ReportData;

/**
 * Created by phihau on 8/5/2017.
 */

public interface ReportPageContact {

    interface ReportPageView extends BaseView {
        void getReport(long ferryID);

        void showCompleteButton();

        void hideCompleteButton();

        void showRattingStar();

        void showProgress();

        void hideProgress();

        void back();
    }

    interface ReportPagePresenter {
        void updateReportStatus(long id, int status);

        void viewReport(long id);

        void markRead(long id);

        ReportData getReport();

        void submitRating(long id, String title, String remark, double rating);

        void createTempFileWithSampleSize(Context context, String originPath, long id);

        void onStop();
    }

}