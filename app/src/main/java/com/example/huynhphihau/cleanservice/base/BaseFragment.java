package com.example.huynhphihau.cleanservice.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.CallSuper;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.auth.FrontActivity;
import com.example.huynhphihau.cleanservice.dialog.ProgressDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;


/**
 * Created by phihau on 7/4/2017.
 */

public class BaseFragment extends Fragment {
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
            progressDialog.show(getActivity().getSupportFragmentManager(), ProgressDialogFragment.class.getSimpleName());
        } catch (IllegalStateException e) {

        }
    }

    public void hideProgressDialog() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (IllegalStateException e) {

        }
    }

    public void showDialogMessage(String msg) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton(getResources().getText(R.string.btn_confirm_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
//            AppLog.e("EXCEPTION", e.getMessage());
        }
    }

    /**
     * handle invalid token
     * back to login screen
     *
     * @param msg
     */
    public void showDialogMessageToken(String msg) {
        try {
            if (alertDialog != null && alertDialog.isShowing()) return;
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(msg);
            builder.setCancelable(false);
            builder.setNegativeButton(getResources().getText(R.string.btn_confirm_ok), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                    Intent intent = new Intent(BaseApplication.getInstance(), FrontActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
            alertDialog = builder.create();
            alertDialog.show();
        } catch (Exception e) {
//            AppLog.e("EXCEPTION", e.getMessage());
        }
    }

    @CallSuper
    public void handleError(Throwable e) {
        Activity activity = getActivity();
        if (activity != null) {
            if (e instanceof IOException) {
                showDialogMessage(getResources().getString(R.string.msg_no_internet));
            } else {
                if (e.getMessage() != null) {
                    showDialogMessage(e.getMessage());
                }
            }
        }
    }

    @CallSuper
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
                showDialogMessageToken(message);
                break;
            default:
                break;
        }
        if (message != "") {
            showDialogMessage(message);
        }
    }
}