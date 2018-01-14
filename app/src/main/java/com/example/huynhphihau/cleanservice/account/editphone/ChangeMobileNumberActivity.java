package com.example.huynhphihau.cleanservice.account.editphone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.huynhphihau.cleanservice.R;
import com.example.huynhphihau.cleanservice.account.smsverification.SmsVerificationActivity;
import com.example.huynhphihau.cleanservice.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by phihau on 5/11/2017.
 */

public class ChangeMobileNumberActivity
        extends BaseActivity
        implements ChangeMobileNumberContract.ChangeMobileNumberView {

    @BindView(R.id.toolbar_change_phone)
    Toolbar toolbar;
    @BindView(R.id.edt_change_mobile_number)
    EditText edt_change_mobile_number;
    @BindView(R.id.btn_change_phone)
    Button btn_change_phone;

    ChangeMobileNumberPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mobile_number);

        ButterKnife.bind(this);

        /*Set tool bar*/
        setSupportActionBar(toolbar);
        /*Show home button*/
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new ChangeMobileNumberPresenter(this);

        btn_change_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = edt_change_mobile_number.getText().toString().trim();
                mPresenter.changeMobileNumber(phone);
            }
        });

    }


    @Override
    public void verifySMS(String newMobileNumber) {
        Intent intentSmsVerification = new Intent(ChangeMobileNumberActivity.this, SmsVerificationActivity.class);
        intentSmsVerification.putExtra("NewMobileNumber", newMobileNumber);
        startActivity(intentSmsVerification);
        finish();
    }
}