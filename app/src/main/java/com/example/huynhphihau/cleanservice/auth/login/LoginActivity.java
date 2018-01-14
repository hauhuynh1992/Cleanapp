package com.example.huynhphihau.cleanservice.auth.login;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.base.BaseActivity;
import com.example.huynhphihau.cleanservice.dashboard.DashboardActivity;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by phihau on 4/18/2017.
 */

public class LoginActivity
        extends BaseActivity
        implements LoginContract.LoginView {

    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.edt_login_mobile)
    EditText edt_phone;
    @BindView(R.id.edt_login_pass)
    EditText edt_pass;
    @BindView(R.id.toolbar_login)
    Toolbar toolbar_login;

    LoginContract.LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);


        /*Set tool bar*/
        setSupportActionBar(toolbar_login);
        /*Show home button*/
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mPresenter = new LoginPresenter(LoginActivity.this);
    }

    @OnClick(R.id.btn_login)
    protected void doLogin() {
        String phone = edt_phone.getText().toString();
        String pass = edt_pass.getText().toString();
        String device_id = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        String device_token = FirebaseInstanceId.getInstance().getToken();

        mPresenter.login(phone, pass, device_id, device_token);
    }

    @Override
    public void loginSuccess() {
        Intent loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(loginIntent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

