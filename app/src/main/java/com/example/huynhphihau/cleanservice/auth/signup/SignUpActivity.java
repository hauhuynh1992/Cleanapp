package com.example.huynhphihau.cleanservice.auth.signup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.auth.login.LoginActivity;
import com.example.huynhphihau.cleanservice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by huynhphihau on 12/16/17.
 */

public class SignUpActivity
        extends BaseActivity
        implements SignUpContract.SignUpView {

    @BindView(R.id.toolbar_sign_up)
    Toolbar toolbar_sign_up;
    @BindView(R.id.edt_sign_up_first_name)
    EditText edt_sign_up_first_name;
    @BindView(R.id.edt_sign_up_last_name)
    EditText edt_sign_up_last_name;
    @BindView(R.id.edt_sign_up_username)
    EditText edt_sign_up_username;
    @BindView(R.id.edt_sign_up_email)
    EditText edt_sign_up_email;
    @BindView(R.id.edt_sign_up_pass)
    EditText edt_sign_up_pass;
    @BindView(R.id.edt_sign_up_confirm_pass)
    EditText edt_sign_up_confirm_pass;
    @BindView(R.id.btn_signup)
    Button btn_signup;

    SignUpPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mPresenter = new SignUpPresenter(this);

        ButterKnife.bind(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar_sign_up);
        /*Show home button*/
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fistName = edt_sign_up_first_name.getText().toString();
                String lastName = edt_sign_up_last_name.getText().toString();
                String userName = edt_sign_up_username.getText().toString();
                String email = edt_sign_up_email.getText().toString();
                String pass = edt_sign_up_pass.getText().toString();
                String confirmPass = edt_sign_up_confirm_pass.getText().toString();
                mPresenter.doSignUp(fistName, lastName, userName, email, pass, confirmPass);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showSignUpSuccess() {
        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
