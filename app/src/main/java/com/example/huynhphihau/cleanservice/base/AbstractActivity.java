package com.example.huynhphihau.cleanservice.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.dashboard.DashboardActivity;
import com.example.huynhphihau.cleanservice.dashboard.history.HistoryActivity;
import com.example.huynhphihau.cleanservice.dashboard.inspection.InspectionActivity;
import com.example.huynhphihau.cleanservice.dashboard.periodic.PeriodicActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huynhphihau on 1/2/18.
 */

public abstract class AbstractActivity
        extends BaseActivity
        implements BaseView {

    @BindView(R.id.txt_tab_home)
    TextView txtTabHome;
    @BindView(R.id.txt_tab_history)
    TextView txtTabHistory;
    @BindView(R.id.txt_tab_periodic)
    TextView txtTabPeriodic;
    @BindView(R.id.txt_tab_inspection)
    TextView txtTabInspection;
    @BindView(R.id.rel_loading)
    RelativeLayout relLoading;
    @BindView(R.id.txt_number_history)
    TextView numUnRead;

    /**
     * view for replacement child's content
     */
    private ViewGroup replaceLayout;

    @CallSuper
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        // replacement child's content
        replaceLayout = findViewById(R.id.content);
        getLayoutInflater().inflate(getContentView(), replaceLayout, true);

        // binding view
        ButterKnife.bind(this);

        // init another views
        onViewReady();

        /* Set color background for Progress bar */
        relLoading.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent_black_80));

        // handle tab
        handelTab();
    }

    /**
     * abstract for child
     *
     * @return
     */
    protected abstract int getContentView();

    /**
     * abstract for child
     *
     * @return
     */
    protected abstract void onViewReady();

    /**
     * hander api error response
     *
     * @param e
     */
    public void handleAPIError(Throwable e) {
        super.handleError(e);
    }

    private void handelTab() {

        if(BaseApplication.getInstance().getUser().isUserStandard()){
           txtTabPeriodic.setVisibility(View.GONE);
           txtTabInspection.setVisibility(View.GONE);
            txtTabHome.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 2));
            txtTabHistory.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT, 2));
        }

        txtTabHome.setOnClickListener((view -> {
            Intent intentHome = new Intent(getBaseContext(), DashboardActivity.class);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);

        }));

        txtTabHistory.setOnClickListener((view -> {
            Intent intentHome = new Intent(getBaseContext(), HistoryActivity.class);
            startActivity(intentHome);
            finish();
        }));

        txtTabPeriodic.setOnClickListener((view -> {
            Intent intentHome = new Intent(getBaseContext(), PeriodicActivity.class);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
            finish();
        }));

        txtTabInspection.setOnClickListener((view -> {
            Intent intentHome = new Intent(getBaseContext(), InspectionActivity.class);
            intentHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intentHome);
            finish();
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload number un read
        if(BaseApplication.getInstance().getNumberUnRead().equals("-")){
            numUnRead.setVisibility(View.GONE);
        } else {
            numUnRead.setVisibility(View.VISIBLE);
            numUnRead.setText(BaseApplication.getInstance().getNumberUnRead());
        }

    }
}

