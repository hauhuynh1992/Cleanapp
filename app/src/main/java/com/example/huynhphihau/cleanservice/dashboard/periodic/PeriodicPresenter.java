package com.example.huynhphihau.cleanservice.dashboard.periodic;

import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.data.response.ListReport;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by huynhphihau on 1/2/18.
 */

public class PeriodicPresenter
        implements PeriodicContact.PeriodicPresenter {
    PeriodicContact.PeriodicView mView;
    private API mAPIService;


    public PeriodicPresenter(PeriodicActivity activity) {
        this.mView = activity;
        this.mAPIService = GetAPIService.getAPIService();
    }

    @Override
    public void getPeriodicHistory(int skip, int job_type) {
        mView.showProgressDialog();
        mAPIService.getPeriodicHistory(skip, BaseConfig.NUM_ITEMS_ON_PAGE, job_type).enqueue(new Callback<ListReport>() {
            @Override
            public void onResponse(Call<ListReport> call, Response<ListReport> response) {
                mView.hideProgressDialog();
                if (response.isSuccessful()) {
                    mView.displayData(response.body().getData());
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