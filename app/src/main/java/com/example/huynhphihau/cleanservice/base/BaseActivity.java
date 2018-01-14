package com.example.huynhphihau.cleanservice.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.auth.FrontActivity;
import com.example.huynhphihau.cleanservice.dashboard.DashboardActivity;
import com.example.huynhphihau.cleanservice.dashboard.history.HistoryActivity;
import com.example.huynhphihau.cleanservice.dashboard.inspection.InspectionActivity;
import com.example.huynhphihau.cleanservice.dashboard.periodic.PeriodicActivity;
import com.example.huynhphihau.cleanservice.dialog.ProgressDialogFragment;
import com.example.huynhphihau.cleanservice.fcm.FirebaseMessagingService;
import com.example.huynhphihau.cleanservice.util.AppLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;

/**
 * Created by LucLX on 5/22/17.
 */

public class BaseActivity extends AppCompatActivity {
    private final IntentFilter mPushFilter = new IntentFilter(FirebaseMessagingService.PUSH_ANNOUNCE);
    /**
     * common progress dialog
     */
    protected ProgressDialogFragment progressDialog;

    /**
     * common alert dialog
     */
    protected AlertDialog alertDialog;

    public void showProgressDialog() {
        try {
            if (progressDialog == null) {
                progressDialog = ProgressDialogFragment.newInstance();
            }
            progressDialog.show(getSupportFragmentManager(), ProgressDialogFragment.class.getSimpleName());
        } catch (IllegalStateException e) {
            AppLog.e("ERROR", e.getMessage());
        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (IllegalStateException e) {
            AppLog.e("ERROR", e.getMessage());
        }
    }

    public void handleError(Throwable e) {
        hideProgressDialog();
        if (e instanceof IOException) {
            showDialogMessage(getResources().getString(R.string.msg_no_internet));
        } else {
            if (e.getMessage() != null) {
                showDialogMessage(e.getMessage());
            }
        }
    }

    public void handleError(Integer code, ResponseBody errorBody) {

        // handle error code
        String message = "";

        try {
            JSONObject mError = new JSONObject(errorBody.string());
            message = mError.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (code) {
            case 401:
            case 403:
                // Clear all local data before go to Login page
                BaseApplication.getInstance().clearSession();
                // Go to login page
                navigateLoginPage();
                break;
            default:
                break;
        }

        if (message != "") {
            showDialogMessage(message);
        }

    }

    public void showDialogMessage(String msg) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton(getResources().getText(R.string.btn_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
            AppLog.e("EXCEPTION", e.getMessage());
        }
    }

    public void navigateLoginPage() {
        Intent intent = new Intent(BaseApplication.getInstance(), FrontActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mPushReceiver, mPushFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mPushReceiver);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //No call for super(). Bug on API Level > 11.
    }

    /**
     * handle inform from FCM
     */
    private BroadcastReceiver mPushReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String report_id = intent.getStringExtra("REPORT_ID");
            String type = intent.getStringExtra("TYPE");

            if (BaseActivity.this != null && BaseActivity.this instanceof DashboardActivity) {
                if (!TextUtils.isEmpty(report_id)) {
                    ((DashboardActivity) BaseActivity.this).updateNumberUnRead();
                }
            }

            if (BaseActivity.this != null && BaseActivity.this instanceof HistoryActivity) {
                if (!TextUtils.isEmpty(report_id)) {
                    ((HistoryActivity) BaseActivity.this).refreshData();
                }
            }

            if (BaseActivity.this != null && BaseActivity.this instanceof InspectionActivity) {
                if (!TextUtils.isEmpty(report_id)) {
                    ((InspectionActivity) BaseActivity.this).refreshData();
                }
            }

            if (BaseActivity.this != null && BaseActivity.this instanceof PeriodicActivity) {
                if (!TextUtils.isEmpty(report_id)) {
                    ((PeriodicActivity) BaseActivity.this).refreshData();
                }
            }
        }
    };
}
