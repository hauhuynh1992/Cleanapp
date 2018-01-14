package com.example.huynhphihau.cleanservice.dashboard.history;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.AbstractActivity;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.base.BaseConfig;
import com.example.huynhphihau.cleanservice.dashboard.ReportHistoryAdapter;
import com.example.huynhphihau.cleanservice.dashboard.report.ReportPageActivity;
import com.example.huynhphihau.cleanservice.data.response.ReportData;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by huynhphihau on 1/2/18.
 */

public class HistoryActivity
        extends AbstractActivity
        implements
        HistoryContact.HistoryView,
        ReportHistoryAdapter.OnTaskItemClickListener {
    @BindView(R.id.swf_history)
    SwipeRefreshLayout swfHistory;
    @BindView(R.id.rv_history)
    RecyclerView rcvHistory;
    @BindView(R.id.cb_history_complete)
    CheckBox cbIsComplete;
    @BindView(R.id.txt_report_type)
    TextView txtReportType;
    @BindView(R.id.txt_tab_history)
    TextView txtTabHistory;
//    @BindView(R.id.txt_number_history)
//    TextView numberUnRead;
    boolean isCompleted;

    ReportHistoryAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    private int mPage;
    private boolean isLoading = false;
    private int pastVisibleItems, visibleItemCount, totalItemCount;
    private HistoryPresenter mPresenter;

    @Override
    protected int getContentView() {
        return R.layout.fragment_history;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("AAA", "History - onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("AAA", "History - onResume");
        refreshData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("AAA", "History - onPause");
    }

    @Override
    protected void onDestroy() {
        Log.d("AAA", "History - onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onViewReady() {
        mPage = 0;

        mPresenter = new HistoryPresenter(this);
        /*Init apdater*/
        adapter = new ReportHistoryAdapter(this);
        mLayoutManager = new LinearLayoutManager(this);
        rcvHistory.setLayoutManager(mLayoutManager);
        /*Set adapter for listview*/
        rcvHistory.setAdapter(adapter);

        // pull to refresh
        swfHistory.setOnRefreshListener(() -> {
            swfHistory.setRefreshing(false);
            refreshData();
        });

        cbIsComplete.setOnCheckedChangeListener((compoundButton, b) -> {
            isCompleted = b;
            refreshData();
        });

        // on load more
        rcvHistory.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
                        if (isCompleted) {
                            txtReportType.setText(getResources().getString(R.string.txt_history_completed));
                            mPresenter.getCompleteReport(mPage * BaseConfig.NUM_LOAD_MORE, BaseConfig.REP_STATUS_COMPLETED);
                        } else {
                            txtReportType.setText(getResources().getString(R.string.txt_history_outstanding));
                            mPresenter.getCompleteReport(mPage * BaseConfig.NUM_LOAD_MORE, BaseConfig.REP_STATUS_NEW);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void refreshData() {
        adapter.clearData();
        mPage = 0;
        if (isCompleted) {
            txtReportType.setText(getResources().getString(R.string.txt_history_completed));
            mPresenter.getCompleteReport(mPage, BaseConfig.REP_STATUS_COMPLETED);
        } else {
            txtReportType.setText(getResources().getString(R.string.txt_history_outstanding));
            mPresenter.getCompleteReport(mPage, BaseConfig.REP_STATUS_NEW);
        }
    }

    @Override
    public void displayData(ArrayList<ReportData> reportData, int numUnRead) {
        isLoading = false;
        adapter.setData(reportData);

        if (numUnRead != 0) {
            BaseApplication.getInstance().setNumberUnRead(numUnRead + "");
        } else {
            BaseApplication.getInstance().clearNumberUnRead();
        }
        super.onResume();
    }


    @Override
    public void onItemClick(int postion) {
        if (postion != RecyclerView.NO_POSITION) {
            Intent jobIntent = new Intent(this, ReportPageActivity.class);
            jobIntent.putExtra("REPORT_ID", adapter.getItem(postion).getId());
            startActivity(jobIntent);
        }
    }
}
