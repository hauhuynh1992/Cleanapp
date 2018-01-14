package com.example.huynhphihau.cleanservice.dashboard.history;

import android.util.Log;

import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.data.response.ListReport;
import com.example.huynhphihau.cleanservice.data.response.ReportData;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huynhphihau on 1/2/18.
 */

public class HistoryPresenter
        implements HistoryContact.HistoryPresenter {
    HistoryActivity mView;
    private API mAPIService;
    private ReportData reportData;


    public HistoryPresenter(HistoryActivity activity) {
        this.mView = (HistoryActivity) activity;
        this.mAPIService = GetAPIService.getAPIService();
    }

    @Override
    public void getCompleteReport(int skip, int status) {
        mView.showProgressDialog();
        if(BaseApplication.getInstance().getUser().isUserStandard()){
            mAPIService.getHistoryReportOfStandard(skip, BaseConfig.NUM_ITEMS_ON_PAGE, status).enqueue(new Callback<ListReport>() {
                @Override
                public void onResponse(Call<ListReport> call, Response<ListReport> response) {
                    mView.hideProgressDialog();
                    if (response.isSuccessful()) {
                        Log.d("AAA", response.body().getNumberUnread()  + "");
                        mView.displayData(response.body().getData(), 0);
                    } else {
                        mView.handleError(response.code(), response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ListReport> call, Throwable t) {
                    mView.hideProgressDialog();
                    mView.handleError(t);
                }
            });
        } else {
            mAPIService.getHistoryReportOfSupervisor(skip, BaseConfig.NUM_ITEMS_ON_PAGE, status).enqueue(new Callback<ListReport>() {
                @Override
                public void onResponse(Call<ListReport> call, Response<ListReport> response) {
                    mView.hideProgressDialog();
                    if (response.isSuccessful()) {
                        Log.d("AAA", response.body().getNumberUnread()  + "");
                        mView.displayData(response.body().getData(), response.body().getNumberUnread());
                    } else {
                        mView.handleError(response.code(), response.errorBody());
                    }
                }

                @Override
                public void onFailure(Call<ListReport> call, Throwable t) {
                    mView.hideProgressDialog();
                    mView.handleError(t);
                }
            });
        }
    }
}