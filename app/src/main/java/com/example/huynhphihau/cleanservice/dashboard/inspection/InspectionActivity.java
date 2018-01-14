package com.example.huynhphihau.cleanservice.dashboard.inspection;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.AbstractActivity;
import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.dashboard.ReportHistoryAdapter;
import com.example.huynhphihau.cleanservice.dashboard.report.ReportPageActivity;
import com.example.huynhphihau.cleanservice.data.response.ReportData;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by huynhphihau on 1/2/18.
 */

public class InspectionActivity
        extends AbstractActivity
        implements
        InspectionContact.InspectionView,
        ReportHistoryAdapter.OnTaskItemClickListener {
    @BindView(R.id.swf_inspection)
    SwipeRefreshLayout swfInspection;
    @BindView(R.id.rv_inspection)
    RecyclerView rcvInspection;

    ReportHistoryAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int mPage;
    private boolean isLoading = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private InspectionPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_inspection;
    }

    @Override
    protected void onViewReady() {

        mPresenter = new InspectionPresenter(this);
        /*Init apdater*/
        adapter = new ReportHistoryAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        rcvInspection.setLayoutManager(mLayoutManager);
        /*Set adapter for listview*/
        rcvInspection.setAdapter(adapter);

        // pull to refresh
        swfInspection.setOnRefreshListener(() -> {
            swfInspection.setRefreshing(false);
            refreshData();
        });

        // on load more
        rcvInspection.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    visibleItemCount = mLayoutManager.getChildCount();
                    totalItemCount = mLayoutManager.getItemCount();
                    pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
                    boolean canLoadMore = visibleItemCount + pastVisibleItems >= totalItemCount;
                    if (!isLoading && canLoadMore) {
                        isLoading = true;
                        mPage++;
                        mPresenter.getPeriodicHistory(mPage * BaseConfig.NUM_LOAD_MORE, BaseConfig.JOB_TYPE_INSPECTION);
                    }
                }
            }
        });
        refreshData();
    }

    @Override
    public void refreshData() {
        adapter.clearData();
        mPage = 0;
        mPresenter.getPeriodicHistory(mPage, BaseConfig.JOB_TYPE_INSPECTION);
    }

    @Override
    public void displayData(ArrayList<ReportData> reportData) {
        isLoading = false;
        adapter.setData(reportData);
    }

    @Override
    public void onItemClick(int postion) {
        if (postion != RecyclerView.NO_POSITION) {
            Intent jobIntent = new Intent(this, ReportPageActivity.class);
            jobIntent.putExtra("REPORT_ID", adapter.getItem(postion).getId());
            startActivity(jobIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }
}
