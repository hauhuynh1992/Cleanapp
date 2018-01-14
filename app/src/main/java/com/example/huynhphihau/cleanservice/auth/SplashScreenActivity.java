package com.example.huynhphihau.cleanservice.auth;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseApplication;
import com.example.huynhphihau.cleanservice.dashboard.DashboardActivity;
import com.example.huynhphihau.cleanservice.data.rest.API;
import com.example.huynhphihau.cleanservice.data.rest.GetAPIService;

import io.reactivex.disposables.CompositeDisposable;

public class SplashScreenActivity extends AppCompatActivity {

    /*Duration of wait (ms)*/
    private int SPLASH_SCREEN_WAIT = 1000;
    private API mAPIServrice;
    private CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        this.mAPIServrice = GetAPIService.getAPIService();
        mCompositeDisposable = new CompositeDisposable();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String token = BaseApplication.getInstance().getToken();
                // If not logged in before
                if (TextUtils.isEmpty(token)) {
                    // Go to Login page
                    routeToLogin();
                } else {
                    // Go to Dashboard page
                    routeToDashBoard();
                }
            }
        }, SPLASH_SCREEN_WAIT);
    }

    private void routeToDashBoard() {
        Intent mainIntent = new Intent(SplashScreenActivity.this, DashboardActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void routeToLogin() {
        Intent loginIntent = new Intent(SplashScreenActivity.this, FrontActivity.class);
        startActivity(loginIntent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }
}
